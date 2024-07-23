package br.com.usinasantafe.pcp.presenter.visitterc.movequipedit

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
import br.com.usinasantafe.pcp.databinding.FragmentMovEquipVisitTercStartedListBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.POS_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.REQUEST_KEY_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.movequip.MovEquipVisitTercModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovEquipVisitTercOpenListFragment : BaseFragment<FragmentMovEquipVisitTercStartedListBinding>(
    R.layout.fragment_mov_equip_visit_terc_started_list,
    FragmentMovEquipVisitTercStartedListBinding::bind,
) {

    private val viewModel: MovEquipVisitTercEmptyListViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null

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

    private fun setListener() {
        with(binding) {
            buttonFecharMov.setOnClickListener {
                showMessage()
            }
            buttonRetMov.setOnClickListener {
                fragmentAttachListenerVisitTerc?.goMovVisitTercList()
            }
        }
    }


    private fun startEvents() {
        viewModel.recoverListMov()
    }

    private fun showMessage(){
        showGenericAlertDialogCheck("DESEJA REALMENTE FECHAR TODOS OS MOVIMENTOS?", requireContext()) {
            viewModel.closeAllMov()
        }
    }

    private fun handleStateChange(state: MovEquipVisitTercStartedListFragmentState) {
        when(state){
            is MovEquipVisitTercStartedListFragmentState.ListMovEquip -> handleListMov(state.movEquipVisitTercList)
            is MovEquipVisitTercStartedListFragmentState.CheckCloseAllMov -> handleCloseAllMov(state.check)
        }
    }

    private fun handleCloseAllMov(check: Boolean){
        if (check) {
            fragmentAttachListenerVisitTerc?.goMovVisitTercList()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun handleListMov(movEquipVisitTercList: List<MovEquipVisitTercModel>) {
        val listAdapter = MovEquipVisitTercOpenAdapter(movEquipVisitTercList).apply {
            onItemClick = { pos ->
                setBundleDetalheVisitTerc(pos)
                fragmentAttachListenerVisitTerc?.goDetalhe()
            }
        }
        binding.listViewMov.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
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