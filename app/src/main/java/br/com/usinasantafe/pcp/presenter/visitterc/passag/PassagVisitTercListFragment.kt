package br.com.usinasantafe.pcp.presenter.visitterc.passag

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
import br.com.usinasantafe.pcp.databinding.FragmentPassagVisitTercListBinding
import br.com.usinasantafe.pcp.presenter.visitterc.FragmentAttachListenerVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.POS_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.REQUEST_KEY_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CPFVisitTercFragment.Companion.TYPE_ADD_OCUPANTE_CPF_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.FLOW_APP_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.POS_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercFragment.Companion.REQUEST_KEY_DESTINO_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.POS_DETALHE_VISIT_TERC
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercFragment.Companion.REQUEST_KEY_DETALHE_VISIT_TERC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassagVisitTercListFragment : BaseFragment<FragmentPassagVisitTercListBinding>(
    R.layout.fragment_passag_visit_terc_list,
    FragmentPassagVisitTercListBinding::bind,
) {

    private val viewModel: PassagVisitTercListViewModel by viewModels()
    private var fragmentAttachListenerVisitTerc: FragmentAttachListenerVisitTerc? = null
    private lateinit var typeAddOcupante: TypeAddOcupante
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_PASSAG_VISIT_TERC_LIST = "requestKeyPassagVisitTercList"
        const val TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST = "typeAddOcupantePassagVisitTercList"
        const val POS_PASSAG_VISIT_TERC_LIST = "posPassagVisitTercList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_PASSAG_VISIT_TERC_LIST) { _, bundle ->
            this.typeAddOcupante = TypeAddOcupante.values()[bundle.getInt(
                TYPE_ADD_OCUPANTE_PASSAG_VISIT_TERC_LIST
            )]
            this.pos = bundle.getInt(POS_PASSAG_VISIT_TERC_LIST)
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

    private fun observeState(){
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun startEvents() {
        viewModel.recoverListPassag(typeAddOcupante, pos)
    }

    private fun setListener() {
        with(binding) {
            buttonInserirPassageiro.setOnClickListener {
                setBundleCPFVisitTerc(typeAddOcupante, pos)
                fragmentAttachListenerVisitTerc?.goCPFVisitTerc()
            }
            buttonOkPassageiro.setOnClickListener {
                when (typeAddOcupante) {
                    TypeAddOcupante.ADDMOTORISTA,
                    TypeAddOcupante.ADDPASSAGEIRO -> {
                        setBundleDestinoVisitTerc(FlowApp.ADD, 0)
                        fragmentAttachListenerVisitTerc?.goDestino()
                    }
                    TypeAddOcupante.CHANGEMOTORISTA,
                    TypeAddOcupante.CHANGEPASSAGEIRO -> {
                        setBundleDetalheVisitTerc(pos)
                        fragmentAttachListenerVisitTerc?.goDetalhe()
                    }
                }
            }
            buttonCancPassageiro.setOnClickListener {
                when (typeAddOcupante) {
                    TypeAddOcupante.ADDMOTORISTA,
                    TypeAddOcupante.ADDPASSAGEIRO -> {
                        setBundleCPFVisitTerc(TypeAddOcupante.ADDMOTORISTA, 0)
                        fragmentAttachListenerVisitTerc?.goCPFVisitTerc()
                    }
                    TypeAddOcupante.CHANGEMOTORISTA,
                    TypeAddOcupante.CHANGEPASSAGEIRO -> {
                        setBundleDetalheVisitTerc(pos)
                        fragmentAttachListenerVisitTerc?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: PassagVisitTercListFragmentState){
        when(state){
            is PassagVisitTercListFragmentState.ListVisitTercPassag -> handleListPassag(state.passagList)
            is PassagVisitTercListFragmentState.CheckDeleteVisitTercPassag -> handleCheckDeleteColabPassag(state.check)
        }
    }

    private fun handleListPassag(passagList: List<String>) {
        viewList(passagList)
    }

    private fun handleCheckDeleteColabPassag(check: Boolean) {
        if(check) {
            viewModel.recoverListPassag(typeAddOcupante, pos)
            return
        }
        showGenericAlertDialog(getString(R.string.texto_failure_delete, "PASSAGEIRO"), requireContext())
    }

    private fun viewList(passagList: List<String>) {

        val localListView = passagList.map { it }

        val listAdapter = CustomAdapter(localListView).apply {
            onItemClick = { _, pos ->
                showMessage(pos)
            }
        }
        binding.listViewPassag.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun showMessage(posList: Int){
        showGenericAlertDialogCheck("DESEJA EXCLUIR O PASSAGEIRO?", requireContext()) {
            viewModel.deletePassag(posList, typeAddOcupante, pos)
        }
    }

    private fun setBundleDetalheVisitTerc(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_VISIT_TERC, bundle)
    }

    private fun setBundleDestinoVisitTerc(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_DESTINO_VISIT_TERC, flowApp.ordinal)
        bundle.putInt(POS_DESTINO_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_DESTINO_VISIT_TERC, bundle)
    }

    private fun setBundleCPFVisitTerc(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_CPF_VISIT_TERC, typeAddOcupante.ordinal)
        bundle.putInt(POS_CPF_VISIT_TERC, pos)
        setFragmentResult(REQUEST_KEY_CPF_VISIT_TERC, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerVisitTerc){
            fragmentAttachListenerVisitTerc = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerVisitTerc = null
    }

}