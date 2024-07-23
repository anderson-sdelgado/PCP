package br.com.usinasantafe.pcp.presenter.proprio.movequip

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showGenericAlertDialogCheck
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentMovEquipProprioListBinding
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.POS_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.REQUEST_KEY_VEICULO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment.Companion.TYPE_ADD_EQUIP_VEICULO_PROPRIO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovEquipProprioListFragment : BaseFragment<FragmentMovEquipProprioListBinding>(
    R.layout.fragment_mov_equip_proprio_list,
    FragmentMovEquipProprioListBinding::bind,
) {

    private val viewModel: MovEquipProprioListViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

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
        viewModel.recoverDataHeader()
        viewModel.recoverListMov()
    }

    private fun setListener() {
        with(binding) {
            buttonEntradaMov.setOnClickListener {
                viewModel.checkSetInitialMov(TypeMov.INPUT)
            }
            buttonSaidaMov.setOnClickListener {
                viewModel.checkSetInitialMov(TypeMov.OUTPUT)
            }
            buttonFecharMov.setOnClickListener {
                showMessage()
            }
            buttonRetornarMov.setOnClickListener {
                fragmentAttachListenerProprio?.goInicial()
            }
        }
    }

    private fun showMessage(){
        showGenericAlertDialogCheck("DESEJA REALMENTE FECHAR TODOS OS MOVIMENTOS?", requireContext()) {
            viewModel.closeAllMov()
        }
    }

    private fun handleStateChange(state: MovEquipProprioListFragmentState){
        when(state){
            is MovEquipProprioListFragmentState.RecoverHeader -> handleHeader(state.header)
            is MovEquipProprioListFragmentState.ListMovEquip -> handleListMov(state.movEquipProprioList)
            is MovEquipProprioListFragmentState.CheckInitialMovEquip -> handleStartMov(state.check)
            is MovEquipProprioListFragmentState.CheckCloseAllMov -> handleCloseAllMov(state.check)
        }
    }

    private fun handleCloseAllMov(check: Boolean){
        if (check) {
            fragmentAttachListenerProprio?.goInicial()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleHeader(header: HeaderModel){
        with(binding) {
            textViewVigia.text = header.nomeVigia
            textViewLocal.text = header.local
        }
    }

    private fun handleStartMov(check: Boolean) {
        if (check) {
            setBundleVeicProprio(TypeAddEquip.ADDVEICULO, 0)
            fragmentAttachListenerProprio?.goVeiculoProprio()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleListMov(movEquipProprioList: List<MovEquipProprioModel>){
        val listAdapter = MovEquipProprioAdapter(movEquipProprioList).apply {
            onItemClick = { pos ->
                setBundleDetalhe(pos)
                fragmentAttachListenerProprio?.goDetalhe()
            }
        }
        binding.listViewMovProprio.run {
            setHasFixedSize(true)
            adapter = listAdapter
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