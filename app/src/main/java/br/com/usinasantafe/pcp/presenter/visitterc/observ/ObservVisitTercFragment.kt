package br.com.usinasantafe.pcp.presenter.visitterc.observ

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
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentObservVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.FLOW_APP_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.POS_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.REQUEST_KEY_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObservVisitTercFragment : BaseFragment<FragmentObservVisitTercBinding>(
    R.layout.fragment_observ_visit_terc,
    FragmentObservVisitTercBinding::bind
) {

    private val viewModel: ObservVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var flowApp: FlowApp
    private lateinit var typeMov: TypeMov
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_OBSERV_VISIT_TERC = "requestKeyObservVisitTerc"
        const val FLOW_APP_OBSERV_VISIT_TERC = "flowAppObservVisitTerc"
        const val TYPE_MOV_OBSERV_VISIT_TERC = "typeMovObservVisitTerc"
        const val POS_OBSERV_VISIT_TERC = "posObservVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_OBSERV_VISIT_TERC) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_OBSERV_VISIT_TERC)]
            this.pos = bundle.getInt(POS_OBSERV_VISIT_TERC)
            this.typeMov = TypeMov.values()[bundle.getInt(TYPE_MOV_OBSERV_VISIT_TERC)]
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
            buttonOkObserv.setOnClickListener {
                if (editTextObserv.text.isEmpty()) {
                    viewModel.setObserv(null, pos, typeMov, flowApp)
                    return@setOnClickListener
                }
                viewModel.setObserv(editTextObserv.text.toString(), pos, typeMov, flowApp)
            }
            buttonCancObserv.setOnClickListener {
                when (flowApp) {
                    FlowApp.ADD -> {
                        when (typeMov) {
                            TypeMov.INPUT -> {
                                setBundleDestinoVisitTerc(flowApp, 0)
                                fragmentAttachListenerVisitTerc?.goDestino()
                            }
                            TypeMov.OUTPUT -> fragmentAttachListenerVisitTerc?.goMovVisitTercList()
                            else -> {}
                        }
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalheVisitTerc(pos)
                        fragmentAttachListenerVisitTerc?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: ObservVisitTercFragmentState) {
        when (state) {
            is ObservVisitTercFragmentState.CheckSetObserv -> handleCheckSetObserv(state.check)
        }
    }

    private fun handleCheckSetObserv(check: Boolean) {
        if (check) {
            when(flowApp){
                FlowApp.ADD -> fragmentAttachListenerVisitTerc?.goMovVisitTercList()
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
                "OBSERVAÇÃO"
            ), requireContext()
        )
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(DetalheMovEquipVisitTercFragment.POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(DetalheMovEquipVisitTercFragment.REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundleDestinoVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_DESTINO_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_DESTINO_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DESTINO_VISIT_TERC, bundle)
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