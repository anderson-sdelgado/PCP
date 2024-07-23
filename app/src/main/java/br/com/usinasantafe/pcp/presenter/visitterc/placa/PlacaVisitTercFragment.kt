package br.com.usinasantafe.pcp.presenter.visitterc.placa

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.databinding.FragmentPlacaVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.POS_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.REQUEST_KEY_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.FLOW_APP_VEIC_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.POS_VEIC_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.REQUEST_KEY_VEIC_VISIT_TERC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacaVisitTercFragment : BaseFragment<FragmentPlacaVisitTercBinding>(
    R.layout.fragment_placa_visit_terc,
    FragmentPlacaVisitTercBinding::bind
) {

    private val viewModel: PlacaVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_PLACA_VISIT_TERC = "requestKeyPlacaVisitTerc"
        const val FLOW_APP_PLACA_VISIT_TERC = "flowAppPlacaVisitTerc"
        const val POS_PLACA_VISIT_TERC = "posPlacaVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_PLACA_VISIT_TERC) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_PLACA_VISIT_TERC)]
            this.pos = bundle.getInt(POS_PLACA_VISIT_TERC)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) { state ->
            handleStateChange(state)
        }
    }

    private fun setListener() {
        with(binding) {
            editTextPlacaVisitTerc.filters = editTextPlacaVisitTerc.filters + InputFilter.AllCaps()
            buttonOkPlacaVisitTerc.setOnClickListener {
                if (editTextPlacaVisitTerc.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "PLACA"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setPlaca(editTextPlacaVisitTerc.text.toString(), flowApp, pos)
            }
            buttonCancPlacaVisitTerc.setOnClickListener {
                when(flowApp) {
                    FlowApp.ADD -> {
                        setBundleVeicVisitTerc(flowApp, 0)
                        fragmentAttachListenerVisitTerc?.goVeiculo()
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalheVisitTerc(pos)
                        fragmentAttachListenerVisitTerc?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: PlacaVisitTercFragmentState) {
        when (state) {
            is PlacaVisitTercFragmentState.CheckSetPlaca -> handleCheckSetPlaca(state.check)
        }
    }

    private fun handleCheckSetPlaca(check: Boolean) {
        if (check) {
            when(flowApp) {
                FlowApp.ADD -> fragmentAttachListenerVisitTerc?.goTipoVisitTerc()
                FlowApp.CHANGE -> {
                    setBundleDetalheVisitTerc(pos)
                    fragmentAttachListenerVisitTerc?.goDetalhe()
                }
            }
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "PLACA"
            ), requireContext()
        )
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundleVeicVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_VEIC_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_VEIC_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_VEIC_VISIT_TERC, bundle)
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