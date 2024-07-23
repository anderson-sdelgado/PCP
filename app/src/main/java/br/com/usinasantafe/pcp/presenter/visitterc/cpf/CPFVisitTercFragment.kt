package br.com.usinasantafe.pcp.presenter.visitterc.cpf

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.GenericDialogProgressBar
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.setListenerButtonsCPF
import br.com.usinasantafe.pcp.utils.setListenerButtonsGeneric
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showToast
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.databinding.FragmentCpfVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.POS_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.REQUEST_KEY_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercFragment.Companion.CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercFragment.Companion.POS_NOME_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercFragment.Companion.REQUEST_KEY_NOME_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercFragment.Companion.TYPE_ADD_OCUPANTE_NOME_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.POS_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.REQUEST_KEY_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.FLOW_APP_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.POS_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.REQUEST_KEY_PLACA_VISIT_TERC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CPFVisitTercFragment : BaseFragment<FragmentCpfVisitTercBinding>(
    R.layout.fragment_cpf_visit_terc,
    FragmentCpfVisitTercBinding::bind
) {

    private val viewModel: CPFVisitTercViewModel by viewModels()
    private var genericDialogProgressBar: GenericDialogProgressBar? = null
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var describeUpdate: String
    private lateinit var typeAddOcupante: TypeAddOcupante
    private lateinit var cpfVisitTerc: String
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_CPF_VISIT_TERC = "requestKeyCPFVisitTerc"
        const val TYPE_ADD_OCUPANTE_CPF_VISIT_TERC = "typeAddOcupanteCPFVisitTerc"
        const val POS_CPF_VISIT_TERC = "posCPFVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_CPF_VISIT_TERC) { _, bundle ->
            this.typeAddOcupante = TypeAddOcupante.values()[bundle.getInt(
                TYPE_ADD_OCUPANTE_CPF_VISIT_TERC
            )]
            this.pos = bundle.getInt(POS_CPF_VISIT_TERC)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun setListener() {
        with(binding) {
            setListenerButtonsGeneric(layoutBotoes, editTextPadrao) {
                editText -> setListenerButtonsCPF(editText)
            }
            layoutBotoes.buttonOkPadrao.setOnClickListener {
                if (editTextPadrao.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "CPF"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                cpfVisitTerc = editTextPadrao.text.toString()
                viewModel.checkCPFVisitanteTerceiro(cpfVisitTerc, typeAddOcupante, pos)
            }
            layoutBotoes.buttonAtualPadrao.setOnClickListener {
                viewModel.updateDataVisitTerc()
            }
        }
    }

    private fun handleStateChange(state: CPFVisitTercFragmentState) {
        when (state) {
            is CPFVisitTercFragmentState.CheckCPF -> handleCheckCPF(state.checkCPF)
            is CPFVisitTercFragmentState.FeedbackUpdate -> handleFeedbackUpdate(state.statusUpdate)
            is CPFVisitTercFragmentState.SetResultUpdate -> handleSetResultUpdate(state.resultUpdateDatabase)
        }
    }

    private fun handleCheckCPF(checkMatric: Boolean) {
        if (checkMatric) {
            setBundleNomeVisitTerc(cpfVisitTerc, typeAddOcupante, pos)
            fragmentAttachListenerVisitTerc?.goNomeVisitTerc()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_dado_invalido_com_atual,
                "MATRICULA DO COLABORADOR"
            ), requireContext()
        )
    }

    private fun handleSetResultUpdate(resultUpdateDatabase: ResultUpdateDatabase?) {
        resultUpdateDatabase?.let {
            if (genericDialogProgressBar == null) {
                showProgressBar()
            }
            describeUpdate = resultUpdateDatabase.describe
            genericDialogProgressBar!!.setValue(resultUpdateDatabase)
        }
    }

    private fun handleFeedbackUpdate(statusUpdate: StatusUpdate) {
        when (statusUpdate) {
            StatusUpdate.UPDATED -> {
                hideProgressBar()
                showToast(
                    getString(R.string.texto_msg_atualizacao, "VISITANTE/TERCEIROS"),
                    requireContext()
                )
            }
            StatusUpdate.FAILURE -> {
                hideProgressBar()
                showToast(
                    getString(R.string.texto_update_failure, describeUpdate),
                    requireContext()
                )
            }
        }
    }

    private fun showProgressBar() {
        genericDialogProgressBar = GenericDialogProgressBar(requireContext())
        genericDialogProgressBar!!.show()
        genericDialogProgressBar!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

    private fun hideProgressBar() {
        if (genericDialogProgressBar != null) {
            genericDialogProgressBar!!.cancel()
        }
        genericDialogProgressBar = null
    }

    private fun setBundlePassagVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_VISIT_TERC_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_VISIT_TERC_LIST, bundle)
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundleNomeVisitTerc(cpf: String, typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_NOME_VISIT_TERC, typeAddOcupante.ordinal)
        bundle.putInt(POS_NOME_VISIT_TERC, pos)
        bundle.putString(CPF_VISIT_TERC, cpf)
        setFragmentResult(REQUEST_KEY_NOME_VISIT_TERC, bundle)
    }

    private fun setBundlePlacaVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_PLACA_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_PLACA_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_PLACA_VISIT_TERC, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerVisitTerc) {
            fragmentAttachListenerVisitTerc = context
        }
        onBackPressed {
            when(typeAddOcupante){
                TypeAddOcupante.ADDMOTORISTA -> {
                    setBundlePlacaVisitTerc(FlowApp.ADD, 0)
                    fragmentAttachListenerVisitTerc?.goPlaca()
                }
                TypeAddOcupante.CHANGEMOTORISTA -> {
                    setBundleDetalheVisitTerc(pos)
                    fragmentAttachListenerVisitTerc?.goDetalhe()
                }
                TypeAddOcupante.ADDPASSAGEIRO,
                TypeAddOcupante.CHANGEPASSAGEIRO -> {
                    setBundlePassagVisitTerc(typeAddOcupante, pos)
                    fragmentAttachListenerVisitTerc?.goPassagList()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerVisitTerc = null
    }

}