package br.com.usinasantafe.pcp.presenter.visitterc.destino

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentDestinoVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.FLOW_APP_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.POS_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.REQUEST_KEY_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.TYPE_MOV_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.POS_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.REQUEST_KEY_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinoVisitTercFragment : BaseFragment<FragmentDestinoVisitTercBinding>(
    R.layout.fragment_destino_visit_terc,
    FragmentDestinoVisitTercBinding::bind
) {

    private val viewModel: DestinoVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_DESTINO_VISIT_TERC = "requestKeyDestinoVisitTerc"
        const val FLOW_APP_DESTINO_VISIT_TERC = "flowAppDestinoVisitTerc"
        const val POS_DESTINO_VISIT_TERC = "posDestinoVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DESTINO_VISIT_TERC) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_DESTINO_VISIT_TERC)]
            this.pos = bundle.getInt(POS_DESTINO_VISIT_TERC)
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
            buttonOkDestino.setOnClickListener {
                if (editTextDestino.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "DESTINO"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setDestino(editTextDestino.text.toString(), flowApp, pos)
            }
            buttonCancDestino.setOnClickListener {
                when(flowApp) {
                    FlowApp.ADD -> {
                        setBundlePassagVisitTerc(TypeAddOcupante.ADDPASSAGEIRO, 0)
                        fragmentAttachListenerVisitTerc?.goPassagList()
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalheVisitTerc(pos)
                        fragmentAttachListenerVisitTerc?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: DestinoVisitTercFragmentState) {
        when (state) {
            is DestinoVisitTercFragmentState.CheckSetDestino -> handleCheckSetDestino(state.check)
        }
    }

    private fun handleCheckSetDestino(checkSetMatricColab: Boolean) {
        if (checkSetMatricColab) {
            when(flowApp) {
                FlowApp.ADD -> {
                    setBundleObservVisitTerc(TypeMov.INPUT, flowApp, 0)
                    fragmentAttachListenerVisitTerc?.goObserv()
                }
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
                "DESTINO"
            ), requireContext()
        )
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(DetalheMovEquipVisitTercFragment.POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(DetalheMovEquipVisitTercFragment.REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundleObservVisitTerc(typeMov: TypeMov, flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_OBSERV_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(TYPE_MOV_OBSERV_VISIT_TERC, typeMov.ordinal)
        bundle.putInt(POS_OBSERV_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_VISIT_TERC, bundle)
    }

    private fun setBundlePassagVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_VISIT_TERC_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_VISIT_TERC_LIST, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerVisitTerc){
            fragmentAttachListenerVisitTerc = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerVisitTerc = null
    }

}