package br.com.usinasantafe.pcp.presenter.visitterc.nome

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.databinding.FragmentNomeVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.POS_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.REQUEST_KEY_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.TYPE_ADD_OCUPANTE_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.POS_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.REQUEST_KEY_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NomeVisitTercFragment : BaseFragment<FragmentNomeVisitTercBinding>(
    R.layout.fragment_nome_visit_terc,
    FragmentNomeVisitTercBinding::bind
) {

    private val viewModel: NomeVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var cpfVisitTerc: String
    private lateinit var typeAddOcupante: TypeAddOcupante
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_NOME_VISIT_TERC = "requestKeyNomeVisitTerc"
        const val TYPE_ADD_OCUPANTE_NOME_VISIT_TERC = "typeAddOcupanteNomeVisitTerc"
        const val CPF_VISIT_TERC = "cpfVisitTerc"
        const val POS_NOME_VISIT_TERC = "posNomeVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_NOME_VISIT_TERC) { _, bundle ->
            this.typeAddOcupante = TypeAddOcupante.values()[bundle.getInt(
                TYPE_ADD_OCUPANTE_NOME_VISIT_TERC
            )]
            this.pos = bundle.getInt(POS_NOME_VISIT_TERC)
            this.cpfVisitTerc = bundle.getString(CPF_VISIT_TERC, "")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

    }

    override fun onResume() {
        startEvents()
        super.onResume()
    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun startEvents() {
        viewModel.recoverData(cpfVisitTerc, typeAddOcupante, pos)
    }

    private fun setListener() {
        with(binding) {
            buttonOkNome.setOnClickListener {
                viewModel.setCPFVisitTerc(cpfVisitTerc, typeAddOcupante, pos)
            }
            buttonCancNome.setOnClickListener {
                setBundleCPFVisitTerc(typeAddOcupante, pos)
                fragmentAttachListenerVisitTerc?.goCPFVisitTerc()
            }
        }
    }

    private fun handleStateChange(state: NomeVisitTercFragmentState){
        when(state){
            is NomeVisitTercFragmentState.GetDataVisitTerc -> handleDataVisitTerc(state.display)
            is NomeVisitTercFragmentState.CheckSetCPF -> handleCheckSetCPF(state.checkSetCPF)
        }
    }

    private fun handleDataVisitTerc(display: DisplayDataVisitTercModel){
        with(binding) {
            textViewTituloNome.text = display.tipo
            textViewNome.text = display.nome
            textViewEmpresa.text = display.empresas
        }
    }

    private fun handleCheckSetCPF(checkSetMatricColab: Boolean) {
        if (checkSetMatricColab) {
            when(typeAddOcupante) {
                TypeAddOcupante.ADDMOTORISTA -> {
                    setBundlePassagVisitTerc(TypeAddOcupante.ADDPASSAGEIRO, 0)
                    fragmentAttachListenerVisitTerc?.goPassagList()
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
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "MATRICULA DO COLABORADOR"
            ), requireContext()
        )
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(DetalheMovEquipVisitTercFragment.POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(DetalheMovEquipVisitTercFragment.REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundlePassagVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_VISIT_TERC_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_VISIT_TERC_LIST, bundle)
    }

    private fun setBundleCPFVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_CPF_VISIT_TERC, typeAddOcupante.ordinal)
        bundle.putInt(POS_CPF_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_CPF_VISIT_TERC, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerVisitTerc){
            fragmentAttachListenerVisitTerc = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerVisitTerc = null
    }

}