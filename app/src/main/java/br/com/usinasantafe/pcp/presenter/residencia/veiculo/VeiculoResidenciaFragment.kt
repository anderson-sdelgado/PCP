package br.com.usinasantafe.pcp.presenter.residencia.veiculo

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
import br.com.usinasantafe.pcp.databinding.FragmentVeiculoResidenciaBinding
import br.com.usinasantafe.pcp.presenter.residencia.FragmentAttachListenerResidencia
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.POS_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaFragment.Companion.REQUEST_KEY_DETALHE_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.FLOW_APP_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.POS_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.REQUEST_KEY_PLACA_RESIDENCIA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VeiculoResidenciaFragment : BaseFragment<FragmentVeiculoResidenciaBinding>(
    R.layout.fragment_veiculo_residencia,
    FragmentVeiculoResidenciaBinding::bind
) {

    private val viewModel: VeiculoResidenciaViewModel by viewModels()
    private var fragmentAttachListenerResidencia: FragmentAttachListenerResidencia? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_VEIC_RESIDENCIA = "requestKeyVeicResidencia"
        const val FLOW_APP_VEIC_RESIDENCIA = "flowAppVeicResidencia"
        const val POS_VEIC_RESIDENCIA = "posVeicResidencia"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_VEIC_RESIDENCIA) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_VEIC_RESIDENCIA)]
            this.pos = bundle.getInt(POS_VEIC_RESIDENCIA)
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
            buttonOkVeicResidencia.setOnClickListener {
                if (editTextVeicResidencia.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "VEÍCULO"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setVeiculo(editTextVeicResidencia.text.toString(), flowApp, pos)
            }
            buttonCancVeicResidencia.setOnClickListener {
                when(flowApp){
                    FlowApp.ADD -> fragmentAttachListenerResidencia?.goMovResidenciaList()
                    FlowApp.CHANGE -> {
                        setBundleDetalheResidencia(pos)
                        fragmentAttachListenerResidencia?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: VeiculoResidenciaFragmentState) {
        when (state) {
            is VeiculoResidenciaFragmentState.CheckSetVeiculo -> handleCheckSetVeiculo(state.check)
        }
    }

    private fun handleCheckSetVeiculo(check: Boolean) {
        if (check) {
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