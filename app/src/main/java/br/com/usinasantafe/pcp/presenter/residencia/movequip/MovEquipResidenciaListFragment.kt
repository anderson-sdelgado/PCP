package br.com.usinasantafe.pcp.presenter.residencia.movequip

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.databinding.FragmentMovEquipResidenciaListBinding
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.residencia.FragmentAttachListenerResidencia
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.FLOW_APP_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.POS_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.REQUEST_KEY_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaFragment.Companion.TYPE_MOV_OBSERV_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.FLOW_APP_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.POS_VEIC_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaFragment.Companion.REQUEST_KEY_VEIC_RESIDENCIA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovEquipResidenciaListFragment : BaseFragment<FragmentMovEquipResidenciaListBinding>(
    R.layout.fragment_mov_equip_residencia_list,
    FragmentMovEquipResidenciaListBinding::bind,
) {

    private val viewModel: MovEquipResidenciaListViewModel by viewModels()
    private var fragmentAttachListenerResidencia: FragmentAttachListenerResidencia? = null

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
        viewModel.recoverDataHeader()
        viewModel.recoverListMov()
    }

    private fun setListener() {
        with(binding) {
            buttonEntradaMovEquipResidencia.setOnClickListener {
                viewModel.checkSetInitialMov()
            }
            buttonEditarMovEquipResidencia.setOnClickListener {
                fragmentAttachListenerResidencia?.goMovResidenciaStartedList()
            }
            buttonRetornarMovEquipResidencia.setOnClickListener {
                fragmentAttachListenerResidencia?.goInicial()
            }
        }
    }

    private fun handleStateChange(state: MovEquipResidenciaListFragmentState) {
        when(state){
            is MovEquipResidenciaListFragmentState.RecoverHeader -> handleHeader(state.header)
            is MovEquipResidenciaListFragmentState.ListMovEquip -> handleListMov(state.movEquipResidenciaList)
            is MovEquipResidenciaListFragmentState.CheckInitialMovEquip -> handleStartMov(state.check)
        }
    }

    private fun handleHeader(header: HeaderModel){
        with(binding) {
            textViewVigia.text = header.nomeVigia
            textViewLocal.text = header.local
        }
    }

    private fun handleListMov(movEquipResidenciaList: List<MovEquipResidenciaModel>){
        val listAdapter = MovEquipResidenciaAdapter(movEquipResidenciaList).apply {
            onItemClick = { pos ->
                setBundleObservResidencia(TypeMov.OUTPUT, FlowApp.ADD,  pos)
                fragmentAttachListenerResidencia?.goObserv()
            }
        }
        binding.listViewMovResidencia.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun handleStartMov(check: Boolean) {
        if (check) {
            setBundleVeicResidencia(FlowApp.ADD, 0)
            fragmentAttachListenerResidencia?.goVeiculo()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_failure_app,
            ), requireContext()
        )
    }

    private fun setBundleObservResidencia(typeMov: TypeMov, flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_MOV_OBSERV_RESIDENCIA, typeMov.ordinal)
        bundle.putInt(FLOW_APP_OBSERV_RESIDENCIA, flowApp.ordinal)
        bundle.putInt(POS_OBSERV_RESIDENCIA, pos)
        setFragmentResult(REQUEST_KEY_OBSERV_RESIDENCIA, bundle)
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