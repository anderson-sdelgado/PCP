package br.com.usinasantafe.pcp.presenter.initial.menuapont

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.CustomAdapter
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showGenericAlertDialogCheck
import br.com.usinasantafe.pcp.databinding.FragmentMenuApontListBinding
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.initial.FragmentAttachListenerInitial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuApontListFragment : BaseFragment<FragmentMenuApontListBinding>(
    R.layout.fragment_menu_apont_list,
    FragmentMenuApontListBinding::bind,
) {

    private val viewModel: MenuApontListViewModel by viewModels()
    private var fragmentAttachListenerInitial: FragmentAttachListenerInitial? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        viewList()
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

    private fun viewList() {

        val opcaoMenu = listOf(
            "MOV. VEÍCULO PRÓPRIO",
            "MOV. VEÍCULO VISITANTE/TERCEIRO",
            "MOV. VEÍCULO RESIDÊNCIA",
        )

        val listAdapter = CustomAdapter(opcaoMenu).apply {
            onItemClick = { text, _ ->
                when(text){
                    "MOV. VEÍCULO PRÓPRIO" -> eventMovEquipProprio()
                    "MOV. VEÍCULO VISITANTE/TERCEIRO" -> eventMovEquipTercVisit()
                    "MOV. VEÍCULO RESIDÊNCIA" -> eventMovEquipResidencia()
                }
            }
        }
        binding.listMenuInicial.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun startEvents() {
        viewModel.recoverDataConfig()
    }

    private fun setListener() {
        with(binding) {
            buttonSairMenuApont.setOnClickListener {
                showMessage()
            }
        }
    }

    private fun handleStateChange(state: MenuApontListFragmentState){
        when(state){
            is MenuApontListFragmentState.CheckCloseAllMov -> handleCloseMov(state.state)
            is MenuApontListFragmentState.RecoverHeader -> handleConfig(state.header)
        }
    }

    private fun showMessage(){
        showGenericAlertDialogCheck(
            "DESEJA REALMENTE RETORNAR? ISSO FECHARÁ TODOS OS MOVIMENTOS.",
            requireContext()
        ) {
            viewModel.closedAllMov()
        }
    }


    private fun handleCloseMov(check: Boolean) {
        if (check) {
            fragmentAttachListenerInitial?.goSplash()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }


    private fun handleConfig(header: HeaderModel){
        with(binding) {
            textViewVigia.text = header.nomeVigia
            textViewLocal.text = header.local
        }
    }

    private fun eventMovEquipProprio(){
        fragmentAttachListenerInitial?.goMovProprio()
    }

    private fun eventMovEquipTercVisit(){
        fragmentAttachListenerInitial?.goMovVisitTerc()
    }

    private fun eventMovEquipResidencia(){
        fragmentAttachListenerInitial?.goMovResidencia()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerInitial) {
            fragmentAttachListenerInitial = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerInitial = null
    }
}