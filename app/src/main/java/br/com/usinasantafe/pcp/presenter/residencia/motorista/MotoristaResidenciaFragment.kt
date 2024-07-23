package br.com.usinasantafe.pcp.presenter.residencia.motorista

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
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentMotoristaResidenciaBinding
import br.com.usinasantafe.pcp.presenter.residencia.FragmentAttachListenerResidencia
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.POS_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.REQUEST_KEY_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.FLOW_APP_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.POS_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.REQUEST_KEY_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.TYPE_MOV_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.FLOW_APP_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.POS_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.REQUEST_KEY_PLACA_RESIDENCIA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotoristaResidenciaFragment : BaseFragment<FragmentMotoristaResidenciaBinding>(
    R.layout.fragment_motorista_residencia,
    FragmentMotoristaResidenciaBinding::bind
) {

    private val viewModel: MotoristaResidenciaViewModel by viewModels()
    private var fragmentAttachListenerResidencia: FragmentAttachListenerResidencia? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_MOTORISTA_RESIDENCIA = "requestKeyMotoristaResidencia"
        const val FLOW_APP_MOTORISTA_RESIDENCIA = "flowAppMotoristaResidencia"
        const val POS_MOTORISTA_RESIDENCIA = "posMotoristaResidencia"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_MOTORISTA_RESIDENCIA) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_MOTORISTA_RESIDENCIA)]
            this.pos = bundle.getInt(POS_MOTORISTA_RESIDENCIA)
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
            buttonOkMotoristaResidencia.setOnClickListener {
                if (editTextMotoristaResidencia.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "VEÍCULO"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setMotorista(editTextMotoristaResidencia.text.toString(), flowApp, pos)
            }
            buttonCancMotoristaResidencia.setOnClickListener {
                when(flowApp) {
                    FlowApp.ADD -> {
                        setBundlePlacaResidencia(flowApp, 0)
                        fragmentAttachListenerResidencia?.goPlaca()
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalheResidencia(pos)
                        fragmentAttachListenerResidencia?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: MotoristaResidenciaFragmentState) {
        when (state) {
            is MotoristaResidenciaFragmentState.CheckSetMotorista -> handleCheckSetVeiculo(state.check)
        }
    }

    private fun handleCheckSetVeiculo(check: Boolean) {
        if (check) {
            when(flowApp) {
                FlowApp.ADD -> {
                    setBundleObservResidencia(TypeMov.INPUT, flowApp, 0)
                    fragmentAttachListenerResidencia?.goObserv()
                }
                FlowApp.CHANGE -> {
                    setBundleDetalheResidencia(pos)
                    fragmentAttachListenerResidencia?.goDetalhe()
                }
            }
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "VEÍCULO"
            ), requireContext()
        )
    }

    private fun setBundleDetalheResidencia(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_RESIDENCIA, bundle)
    }

    private fun setBundleObservResidencia(typeMov: TypeMov, flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_MOV_OBSERV_RESIDENCIA, typeMov.ordinal)
        bundle.putInt(FLOW_APP_OBSERV_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_OBSERV_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_RESIDENCIA, bundle)
    }

    private fun setBundlePlacaResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_PLACA_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_PLACA_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_PLACA_RESIDENCIA, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerResidencia) {
            fragmentAttachListenerResidencia = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerResidencia = null
    }

}