package br.com.usinasantafe.pcp.presenter.visitterc.detalhemov

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.CustomAdapter
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showGenericAlertDialogCheck
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentDetalheMovEquipVisitTercBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.POS_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.REQUEST_KEY_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.TYPE_ADD_OCUPANTE_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.FLOW_APP_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.POS_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.REQUEST_KEY_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.FLOW_APP_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.POS_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.REQUEST_KEY_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercFragment.Companion.TYPE_MOV_OBSERV_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.POS_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.REQUEST_KEY_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.passag.PassagVisitTercListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.FLOW_APP_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.POS_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercFragment.Companion.REQUEST_KEY_PLACA_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.FLOW_APP_VEIC_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.POS_VEIC_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercFragment.Companion.REQUEST_KEY_VEIC_VISIT_TERC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalheMovEquipVisitTercFragment : BaseFragment<FragmentDetalheMovEquipVisitTercBinding>(
    R.layout.fragment_detalhe_mov_equip_visit_terc,
    FragmentDetalheMovEquipVisitTercBinding::bind
) {

    private val viewModel: DetalheMovEquipVisitTercViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_DETALHE_VISIT_TERC = "requestKeyDetalheVisitTerc"
        const val POS_DETALHE_VISIT_TERC = "posDetalheVisitTerc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DETALHE_VISIT_TERC) { _, bundle ->
            this.pos = bundle.getInt(POS_DETALHE_VISIT_TERC)
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
                fragmentAttachListenerVisitTerc?.goMovVisitTercListStarted()
            }
        }

    }

    private fun showMessage(){
        showGenericAlertDialogCheck("DESEJA REALMENTE FECHAR O MOVIMENTO?", requireContext()) {
            viewModel.closeMov(pos)
        }
    }

    private fun handleStateChange(state: DetalheMovEquipVisitTercFragmentState){
        when(state){
            is DetalheMovEquipVisitTercFragmentState.CheckMov -> handleCloseMov(state.check)
            is DetalheMovEquipVisitTercFragmentState.RecoverDetalhe -> handleDetalhe(state.detalhe)
        }
    }

    private fun handleCloseMov(check: Boolean) {
        if (check) {
            fragmentAttachListenerVisitTerc?.goMovVisitTercListStarted()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleDetalhe(detalhe: DetalheMovEquipVisitTercModel) {

        val detalhes = listOf(
            detalhe.dthr,
            detalhe.tipoMov,
            detalhe.veiculo,
            detalhe.placa,
            detalhe.tipoVisitTerc,
            detalhe.motorista,
            detalhe.passageiro,
            detalhe.destino,
            detalhe.observ
        )

        val listAdapter = CustomAdapter(detalhes).apply {
            onItemClick = { _, position ->
                when(position){
                    2 -> {
                        setBundleVeicVisitTerc(FlowApp.CHANGE, pos)
                        fragmentAttachListenerVisitTerc?.goVeiculo()
                    }
                    3 -> {
                        setBundlePlacaVisitTerc(FlowApp.CHANGE, pos)
                        fragmentAttachListenerVisitTerc?.goPlaca()
                    }
                    5 -> {
                        setBundleCPFVisitTerc(TypeAddOcupante.CHANGEMOTORISTA, pos)
                        fragmentAttachListenerVisitTerc?.goCPFVisitTerc()
                    }
                    6 -> {
                        setBundlePassagVisitTerc(TypeAddOcupante.CHANGEPASSAGEIRO, pos)
                        fragmentAttachListenerVisitTerc?.goPassagList()
                    }
                    7 -> {
                        setBundleDestinoVisitTerc(FlowApp.CHANGE, pos)
                        fragmentAttachListenerVisitTerc?.goDestino()
                    }
                    8 -> {
                        setBundleObservVisitTerc(TypeMov.EMPTY, FlowApp.CHANGE, pos)
                        fragmentAttachListenerVisitTerc?.goObserv()
                    }
                }
            }
        }
        binding.listViewDetalheMov.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setBundleVeicVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_VEIC_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_VEIC_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_VEIC_VISIT_TERC, bundle)
    }

    private fun setBundlePlacaVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_PLACA_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_PLACA_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_PLACA_VISIT_TERC, bundle)
    }

    private fun setBundleCPFVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_CPF_VISIT_TERC, typeAddOcupante.ordinal)
        bundle.putInt(POS_CPF_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_CPF_VISIT_TERC, bundle)
    }

    private fun setBundlePassagVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_VISIT_TERC_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_VISIT_TERC_LIST, bundle)
    }

    private fun setBundleDestinoVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_DESTINO_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_DESTINO_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DESTINO_VISIT_TERC, bundle)
    }

    private fun setBundleObservVisitTerc(typeMov: TypeMov, flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_OBSERV_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(TYPE_MOV_OBSERV_VISIT_TERC, typeMov.ordinal)
        bundle.putInt(POS_OBSERV_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_VISIT_TERC, bundle)
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