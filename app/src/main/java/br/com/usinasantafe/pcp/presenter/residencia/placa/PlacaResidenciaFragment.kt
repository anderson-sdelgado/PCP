package br.com.usinasantafe.pcp.presenter.residencia.placa

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.databinding.FragmentPlacaResidenciaBinding
import br.com.usinasantafe.pcp.presenter.residencia.FragmentAttachListenerResidencia
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.POS_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.REQUEST_KEY_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.FLOW_APP_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.POS_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.REQUEST_KEY_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.FLOW_APP_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.POS_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.REQUEST_KEY_VEIC_RESIDENCIA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacaResidenciaFragment : BaseFragment<FragmentPlacaResidenciaBinding>(
    R.layout.fragment_placa_residencia,
    FragmentPlacaResidenciaBinding::bind
) {

    private val viewModel: PlacaResidenciaViewModel by viewModels()
    private var fragmentAttachListenerResidencia: FragmentAttachListenerResidencia? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_PLACA_RESIDENCIA = "requestKeyPlacaResidencia"
        const val FLOW_APP_PLACA_RESIDENCIA = "flowAppPlacaResidencia"
        const val POS_PLACA_RESIDENCIA = "posPlacaResidencia"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_PLACA_RESIDENCIA) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_PLACA_RESIDENCIA)]
            this.pos = bundle.getInt(POS_PLACA_RESIDENCIA)
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
            editTextPlacaResidencia.filters = editTextPlacaResidencia.filters + InputFilter.AllCaps()
            buttonOkPlacaResidencia.setOnClickListener {
                if (editTextPlacaResidencia.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "PLACA"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setPlaca(editTextPlacaResidencia.text.toString(), flowApp, pos)
            }
            buttonCancPlacaResidencia.setOnClickListener {
                when(flowApp) {
                    FlowApp.ADD -> {
                        setBundleVeicResidencia(flowApp, 0)
                        fragmentAttachListenerResidencia?.goVeiculo()
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalheResidencia(pos)
                        fragmentAttachListenerResidencia?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: PlacaResidenciaFragmentState) {
        when (state) {
            is PlacaResidenciaFragmentState.CheckSetPlaca -> handleCheckSetPlaca(state.check)
        }
    }

    private fun handleCheckSetPlaca(check: Boolean) {
        if (check) {
            when (flowApp) {
                FlowApp.ADD -> {
                    setBundleMotoristaResidencia(flowApp, 0)
                    fragmentAttachListenerResidencia?.goMotorista()
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
                "PLACA"
            ), requireContext()
        )
    }

    private fun setBundleDetalheResidencia(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_RESIDENCIA, bundle)
    }

    private fun setBundleMotoristaResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_MOTORISTA_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_MOTORISTA_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_MOTORISTA_RESIDENCIA, bundle)
    }

    private fun setBundleVeicResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_VEIC_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_VEIC_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_VEIC_RESIDENCIA, bundle)
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