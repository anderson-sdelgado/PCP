package br.com.usinasantafe.pcp.presenter.visitterc.tipo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.CustomAdapter
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.databinding.FragmentTipoVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.POS_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.REQUEST_KEY_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.TYPE_ADD_OCUPANTE_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.FLOW_APP_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.POS_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.REQUEST_KEY_PLACA_VISIT_TERC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TipoVisitTercFragment : BaseFragment<FragmentTipoVisitTercBinding>(
    R.layout.fragment_tipo_visit_terc,
    FragmentTipoVisitTercBinding::bind,
) {

    private val viewModel: TipoVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        viewList()
        setListener()

    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun viewList() {
        val optionType = listOf(
            "TERCEIRO",
            "VISITANTE",
        )
        val listAdapter = CustomAdapter(optionType).apply {
            onItemClick = { text, _ ->
                when(text){
                    "TERCEIRO" -> viewModel.setTipo(TypeVisitTerc.TERCEIRO)
                    "VISITANTE" -> viewModel.setTipo(TypeVisitTerc.VISITANTE)
                }
            }
        }
        binding.listViewTipo.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun handleStateChange(state: TipoVisitTercFragmentState){
        when(state){
            is TipoVisitTercFragmentState.CheckSetTipo -> handleCheckSetTipo(state.check)
        }
    }

    private fun setListener() {
        with(binding) {
            buttonRetornarTipo.setOnClickListener {
                setBundlePlacaVisitTerc(FlowApp.ADD, 0)
                fragmentAttachListenerVisitTerc?.goPlaca()
            }
        }
    }

    private fun handleCheckSetTipo(check: Boolean) {
        if (check) {
            setBundleCPFVisitTerc(TypeAddOcupante.ADDMOTORISTA, 0)
            fragmentAttachListenerVisitTerc?.goCPFVisitTerc()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "PLACA"
            ), requireContext()
        )
    }

    private fun setBundleCPFVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_CPF_VISIT_TERC, typeAddOcupante.ordinal)
        bundle.putInt(POS_CPF_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_CPF_VISIT_TERC, bundle)
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
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerVisitTerc = null
    }

}