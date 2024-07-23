package br.com.usinasantafe.pcp.presenter.proprio.detalhemov

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
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.databinding.FragmentDetalheMovEquipProprioBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.FLOW_APP_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.POS_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.REQUEST_KEY_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.POS_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.REQUEST_KEY_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.TYPE_ADD_OCUPANTE_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.FLOW_APP_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.POS_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.REQUEST_KEY_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.FLOW_APP_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.POS_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.REQUEST_KEY_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.POS_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.REQUEST_KEY_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.POS_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.REQUEST_KEY_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.TYPE_ADD_EQUIP_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.POS_VEICULO_SEG
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.REQUEST_KEY_VEICULO_SEG
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.TYPE_ADD_EQUIP_VEICULO_SEG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalheMovEquipProprioFragment : BaseFragment<FragmentDetalheMovEquipProprioBinding>(
    R.layout.fragment_detalhe_mov_equip_proprio,
    FragmentDetalheMovEquipProprioBinding::bind
) {

    private val viewModel: DetalheMovEquipProprioViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_DETALHE_PROPRIO = "requestKeyDetalheProprio"
        const val POS_DETALHE_PROPRIO = "posDetalheProprio"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DETALHE_PROPRIO) { _, bundle ->
            this.pos = bundle.getInt(POS_DETALHE_PROPRIO)
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
                fragmentAttachListenerProprio?.goMovProprioList()
            }
        }

    }

    private fun showMessage(){
        showGenericAlertDialogCheck("DESEJA REALMENTE FECHAR O MOVIMENTO?", requireContext()) {
            viewModel.closeMov(pos)
        }
    }

    private fun handleStateChange(state: DetalheMovEquipProprioFragmentState){
        when(state){
            is DetalheMovEquipProprioFragmentState.RecoverDetalhe ->  handleDetalhe(state.detalhe)
            is DetalheMovEquipProprioFragmentState.CheckMov -> handleCloseMov(state.check)
        }
    }

    private fun handleCloseMov(check: Boolean) {
        if (check) {
            fragmentAttachListenerProprio?.goMovProprioList()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleDetalhe(detalhe: DetalheMovEquipProprioModel) {

        val detalhes = listOf(
            detalhe.dthr,
            detalhe.tipoMov,
            detalhe.veiculo,
            detalhe.motorista,
            detalhe.passageiro,
            detalhe.destino,
            detalhe.veiculoSec,
            detalhe.notaFiscal,
            detalhe.observ
        )

        val listAdapter = CustomAdapter(detalhes).apply {
            onItemClick = { _, position ->
                when(position){
                    2 -> {
                        setBundleVeicProprio(TypeAddEquip.CHANGEVEICULO, pos)
                        fragmentAttachListenerProprio?.goVeiculoProprio()
                    }
                    3 -> {
                        setBundleMatricColab(TypeAddOcupante.CHANGEMOTORISTA, pos)
                        fragmentAttachListenerProprio?.goMatricColab()
                    }
                    4 -> {
                        setBundlePassagList(TypeAddOcupante.CHANGEPASSAGEIRO, pos)
                        fragmentAttachListenerProprio?.goPassagList()
                    }
                    5 -> {
                        setBundleDestino(FlowApp.CHANGE, pos)
                        fragmentAttachListenerProprio?.goDestino()
                    }
                    6 -> {
                        setBundleVeicSegProprio(TypeAddEquip.CHANGEVEICULOSEG, pos)
                        fragmentAttachListenerProprio?.goVeicSegList()
                    }
                    7 -> {
                        setBundleNotaFiscal(FlowApp.CHANGE, pos)
                        fragmentAttachListenerProprio?.goNotaFiscal()
                    }
                    8 -> {
                        setBundleObserv(FlowApp.CHANGE, pos)
                        fragmentAttachListenerProprio?.goObserv()
                    }
                }
            }
        }
        binding.listViewDetalheMov.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setBundleObserv(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_OBSERV_PROPRIO, flowApp.ordinal)
        bundle.putInt(POS_OBSERV_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_PROPRIO, bundle)
    }

    private fun setBundleNotaFiscal(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_NOTA_FISCAL_PROPRIO, flowApp.ordinal)
        bundle.putInt(POS_NOTA_FISCAL_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_NOTA_FISCAL_PROPRIO, bundle)
    }

    private fun setBundleVeicSegProprio(typeAddEquip: TypeAddEquip, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_EQUIP_VEICULO_SEG, typeAddEquip.ordinal)
        bundle.putInt(POS_VEICULO_SEG, pos)
        setFragmentResult(REQUEST_KEY_VEICULO_SEG, bundle)
    }

    private fun setBundlePassagList(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_COLAB_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_COLAB_LIST, bundle)
    }

    private fun setBundleDestino(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_DESTINO_PROPRIO, flowApp.ordinal)
        bundle.putInt(POS_DESTINO_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DESTINO_PROPRIO, bundle)
    }

    private fun setBundleMatricColab(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_MATRIC_COLAB, typeAddOcupante.ordinal)
        bundle.putInt(POS_MATRIC_COLAB, pos)
        setFragmentResult(REQUEST_KEY_MATRIC_COLAB, bundle)
    }

    private fun setBundleVeicProprio(typeAddEquip: TypeAddEquip, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_EQUIP_VEICULO_PROPRIO, typeAddEquip.ordinal)
        bundle.putInt(POS_VEICULO_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_VEICULO_PROPRIO, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerProprio) {
            fragmentAttachListenerProprio = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerProprio = null
    }

}