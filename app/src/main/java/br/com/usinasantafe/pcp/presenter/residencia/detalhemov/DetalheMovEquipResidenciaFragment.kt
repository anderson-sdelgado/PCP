package br.com.usinasantafe.pcp.presenter.residencia.detalhemov

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.CustomAdapter
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showGenericAlertDialogCheck
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentDetalheMovEquipResidenciaBinding
import br.com.usinasantafe.pcp.presenter.residencia.FragmentAttachListenerResidencia
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.FLOW_APP_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.POS_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaFragment.Companion.REQUEST_KEY_MOTORISTA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.FLOW_APP_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.POS_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.REQUEST_KEY_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.TYPE_MOV_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.FLOW_APP_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.POS_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaFragment.Companion.REQUEST_KEY_PLACA_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.FLOW_APP_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.POS_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.REQUEST_KEY_VEIC_RESIDENCIA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalheMovEquipResidenciaFragment : BaseFragment<FragmentDetalheMovEquipResidenciaBinding>(
    R.layout.fragment_detalhe_mov_equip_residencia,
    FragmentDetalheMovEquipResidenciaBinding::bind
) {

    private val viewModel: DetalheMovEquipResidenciaViewModel by viewModels()
    private var fragmentAttachListenerResidencia: FragmentAttachListenerResidencia? = null
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_DETALHE_RESIDENCIA = "requestKeyDetalheResidencia"
        const val POS_DETALHE_RESIDENCIA = "posDetalheResidencia"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DETALHE_RESIDENCIA) { _, bundle ->
            this.pos = bundle.getInt(POS_DETALHE_RESIDENCIA)
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
        viewModel.recoverDataDetalhe(pos)
    }

    private fun setListener() {
        with(binding) {
            buttonFecharMov.setOnClickListener {
                showMessage()
            }
            buttonRetDetalheMov.setOnClickListener {
                fragmentAttachListenerResidencia?.goMovResidenciaStartedList()
            }
        }

    }

    private fun showMessage(){
        showGenericAlertDialogCheck("DESEJA REALMENTE FECHAR O MOVIMENTO?", requireContext()) {
            viewModel.closeMov(pos)
        }
    }

    private fun handleStateChange(state: DetalheMovEquipResidenciaFragmentState){
        when(state){
            is DetalheMovEquipResidenciaFragmentState.CheckMov -> handleCloseMov(state.check)
            is DetalheMovEquipResidenciaFragmentState.RecoverDetalhe -> handleDetalhe(state.detalhe)
        }
    }

    private fun handleCloseMov(check: Boolean) {
        if (check) {
            fragmentAttachListenerResidencia?.goMovResidenciaStartedList()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleDetalhe(detalhe: DetalheMovEquipResidenciaModel) {

        val detalhes = listOf(
            detalhe.dthr,
            detalhe.tipoMov,
            detalhe.veiculo,
            detalhe.placa,
            detalhe.motorista,
            detalhe.observ
        )

        val listAdapter = CustomAdapter(detalhes).apply {
            onItemClick = { _, position ->
                when(position){
                    2 -> {
                        setBundleVeicResidencia(FlowApp.CHANGE, pos)
                        fragmentAttachListenerResidencia?.goVeiculo()
                    }
                    3 -> {
                        setBundlePlacaResidencia(FlowApp.CHANGE, pos)
                        fragmentAttachListenerResidencia?.goPlaca()
                    }
                    4 -> {
                        setBundleMotoristaResidencia(FlowApp.CHANGE, pos)
                        fragmentAttachListenerResidencia?.goMotorista()
                    }
                    5 -> {
                        setBundleObservResidencia(TypeMov.EMPTY, FlowApp.CHANGE, pos)
                        fragmentAttachListenerResidencia?.goObserv()
                    }
                }
            }
        }
        binding.listViewDetalheMov.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setBundleVeicResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_VEIC_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_VEIC_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_VEIC_RESIDENCIA, bundle)
    }

    private fun setBundlePlacaResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_PLACA_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_PLACA_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_PLACA_RESIDENCIA, bundle)
    }

    private fun setBundleMotoristaResidencia(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_MOTORISTA_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_MOTORISTA_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_MOTORISTA_RESIDENCIA, bundle)
    }

    private fun setBundleObservResidencia(typeMov: TypeMov, flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_OBSERV_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(TYPE_MOV_OBSERV_RESIDENCIA, typeMov.ordinal)
        bundle.putInt(POS_OBSERV_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_RESIDENCIA, bundle)
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