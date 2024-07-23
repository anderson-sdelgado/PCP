package br.com.usinasantafe.pcp.presenter.proprio.veiculoseg

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.databinding.FragmentVeiculoSegProprioListBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.POS_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.REQUEST_KEY_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.TYPE_ADD_OCUPANTE_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.POS_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.REQUEST_KEY_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.TYPE_ADD_EQUIP_VEICULO_PROPRIO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VeiculoSegProprioListFragment : BaseFragment<FragmentVeiculoSegProprioListBinding>(
    R.layout.fragment_veiculo_seg_proprio_list,
    FragmentVeiculoSegProprioListBinding::bind,
) {

    private val viewModel: VeiculoSegProprioListViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var typeAddEquip : TypeAddEquip
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_VEICULO_SEG = "requestKeyVeiculoSeg"
        const val TYPE_ADD_EQUIP_VEICULO_SEG = "typeAddEquipVeiculoSeg"
        const val POS_VEICULO_SEG = "posVeiculoSeg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_VEICULO_SEG) { _, bundle ->
            this.typeAddEquip = TypeAddEquip.values()[bundle.getInt(
                TYPE_ADD_EQUIP_VEICULO_SEG
            )]
            this.pos = bundle.getInt(POS_VEICULO_SEG)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        observeState()

    }

    override fun onResume() {
        startEvents()
        super.onResume()
    }


    private fun observeState(){
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun startEvents() {
        viewModel.recoverListEquipSeg(typeAddEquip, pos)
    }

    private fun setListener() {
        with(binding) {
            buttonInserirVeiculoSeg.setOnClickListener {
                setBundleVeicProprio(typeAddEquip, pos)
                fragmentAttachListenerProprio?.goVeiculoProprio()
            }
            buttonOkVeiculoSeg.setOnClickListener {
                when(typeAddEquip) {
                    TypeAddEquip.ADDVEICULO,
                    TypeAddEquip.ADDVEICULOSEG -> {
                        setBundleMatricColab(TypeAddOcupante.ADDMOTORISTA, 0)
                        fragmentAttachListenerProprio?.goMatricColab()
                    }
                    TypeAddEquip.CHANGEVEICULO,
                    TypeAddEquip.CHANGEVEICULOSEG -> {
                        setBundleDetalhe(pos)
                        fragmentAttachListenerProprio?.goDetalhe()
                    }
                }
            }
            buttonCancVeiculoSeg.setOnClickListener {
                when(typeAddEquip) {
                    TypeAddEquip.ADDVEICULO,
                    TypeAddEquip.ADDVEICULOSEG -> {
                        setBundleVeicProprio(TypeAddEquip.ADDVEICULO, 0)
                        fragmentAttachListenerProprio?.goVeiculoProprio()
                    }
                    TypeAddEquip.CHANGEVEICULO,
                    TypeAddEquip.CHANGEVEICULOSEG -> {
                        setBundleDetalhe(pos)
                        fragmentAttachListenerProprio?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: VeiculoSegProprioListFragmentState){
        when(state){
            is VeiculoSegProprioListFragmentState.ListEquipSeg -> handleListEquipSeg(state.equipSegList)
            is VeiculoSegProprioListFragmentState.CheckDeleteEquipProprioSeg -> handleCheckDeleteEquipSeg(state.check)
        }
    }

    private fun handleListEquipSeg(equipSegList: List<String>) {
        viewList(equipSegList)
    }

    private fun handleCheckDeleteEquipSeg(check: Boolean) {
        if(check) {
            viewModel.recoverListEquipSeg(typeAddEquip, pos)
            return
        }
        showGenericAlertDialog(getString(R.string.texto_failure_delete, "VEÍCULO SECUNDÁRIO"), requireContext())
    }

    private fun viewList(equipSegList: List<String>) {

        val localListView = equipSegList.map { it }

        val listAdapter = CustomAdapter(localListView).apply {
            onItemClick = { _, pos ->
                showMessage(pos)
            }
        }
        binding.listViewVeiculoSeg.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun showMessage(posList: Int){
        showGenericAlertDialogCheck("DESEJA EXCLUIR O VEÍCULO?", requireContext()) {
            viewModel.deleteEquip(posList, typeAddEquip, pos)
        }
    }

    private fun setBundleDetalhe(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_PROPRIO, bundle)
    }

    private fun setBundleVeicProprio(typeAddEquip: TypeAddEquip, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_EQUIP_VEICULO_PROPRIO, typeAddEquip.ordinal)
        bundle.putInt(POS_VEICULO_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_VEICULO_PROPRIO, bundle)
    }

    private fun setBundleMatricColab(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_MATRIC_COLAB, typeAddOcupante.ordinal)
        bundle.putInt(POS_MATRIC_COLAB, pos)
        setFragmentResult(REQUEST_KEY_MATRIC_COLAB, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerProprio){
            fragmentAttachListenerProprio = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerProprio = null
    }

}