package br.com.usinasantafe.pcp.presenter.proprio.passag

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
import br.com.usinasantafe.pcp.databinding.FragmentPassagColabListBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.FLOW_APP_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.POS_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.REQUEST_KEY_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.POS_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.REQUEST_KEY_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.TYPE_ADD_OCUPANTE_MATRIC_COLAB
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassagColabListFragment : BaseFragment<FragmentPassagColabListBinding>(
    R.layout.fragment_passag_colab_list,
    FragmentPassagColabListBinding::bind,
) {

    private val viewModel: PassagColabListViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var typeAddOcupante: TypeAddOcupante
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_PASSAG_COLAB_LIST = "requestKeyPassagColabList"
        const val TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST = "typeAddOcupantePassagColabList"
        const val POS_PASSAG_COLAB_LIST = "posPassagColabList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_PASSAG_COLAB_LIST) { _, bundle ->
            this.typeAddOcupante = TypeAddOcupante.values()[bundle.getInt(
                TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST
            )]
            this.pos = bundle.getInt(POS_PASSAG_COLAB_LIST)
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
        viewModel.uiLiveData.observe(viewLifecycleOwner) { state ->
            handleStateChange(state)
        }
    }

    private fun startEvents() {
        viewModel.recoverListPassag(typeAddOcupante, pos)
    }

    private fun setListener() {
        with(binding) {
            buttonInserirPassageiro.setOnClickListener {
                setBundleMatricColab(typeAddOcupante, pos)
                fragmentAttachListenerProprio?.goMatricColab()
            }
            buttonOkPassageiro.setOnClickListener {
                when (typeAddOcupante) {
                    TypeAddOcupante.ADDMOTORISTA,
                    TypeAddOcupante.ADDPASSAGEIRO -> {
                        setBundleDestino(FlowApp.ADD, 0)
                        fragmentAttachListenerProprio?.goDestino()
                    }
                    TypeAddOcupante.CHANGEMOTORISTA,
                    TypeAddOcupante.CHANGEPASSAGEIRO -> {
                        setBundleDetalhe(pos)
                        fragmentAttachListenerProprio?.goDetalhe()
                    }
                }
            }
            buttonCancPassageiro.setOnClickListener {
                when (typeAddOcupante) {
                    TypeAddOcupante.ADDMOTORISTA,
                    TypeAddOcupante.ADDPASSAGEIRO -> {
                        setBundleMatricColab(TypeAddOcupante.ADDMOTORISTA, 0)
                        fragmentAttachListenerProprio?.goMatricColab()
                    }
                    TypeAddOcupante.CHANGEMOTORISTA,
                    TypeAddOcupante.CHANGEPASSAGEIRO -> {
                        setBundleDetalhe(pos)
                        fragmentAttachListenerProprio?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: PassagColabListFragmentState) {
        when (state) {
            is PassagColabListFragmentState.ListColabPassag -> handleListPassag(state.passagList)
            is PassagColabListFragmentState.CheckDeleteColabPassag -> handleCheckDeleteColabPassag(
                state.check
            )
        }
    }

    private fun handleListPassag(passagList: List<String>) {
        viewList(passagList)
    }

    private fun handleCheckDeleteColabPassag(check: Boolean) {
        if (check) {
            viewModel.recoverListPassag(typeAddOcupante, pos)
            return
        }
        showGenericAlertDialog(
            getString(R.string.texto_failure_delete, "PASSAGEIRO"),
            requireContext()
        )
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

    private fun showMessage(posList: Int) {
        showGenericAlertDialogCheck("DESEJA EXCLUIR O PASSAGEIRO?", requireContext()) {
            viewModel.deletePassag(posList, typeAddOcupante, pos)
        }
    }

    private fun setBundleDetalhe(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_PROPRIO, bundle)
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