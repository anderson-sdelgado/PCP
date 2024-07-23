package br.com.usinasantafe.pcp.presenter.proprio.destino

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.databinding.FragmentDestinoProprioBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.FLOW_APP_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.POS_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment.Companion.REQUEST_KEY_NOTA_FISCAL_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.FLOW_APP_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.POS_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.REQUEST_KEY_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.POS_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.REQUEST_KEY_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinoProprioFragment : BaseFragment<FragmentDestinoProprioBinding>(
    R.layout.fragment_destino_proprio,
    FragmentDestinoProprioBinding::bind
) {

    private val viewModel: DestinoProprioViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_DESTINO_PROPRIO = "requestKeyDestinoProprio"
        const val FLOW_APP_DESTINO_PROPRIO = "flowAppDestinoProprio"
        const val POS_DESTINO_PROPRIO = "posDestinoProprio"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DESTINO_PROPRIO) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_DESTINO_PROPRIO)]
            this.pos = bundle.getInt(POS_DESTINO_PROPRIO)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) { state ->
            handleStateChange(state)
        }
    }

    private fun setListener() {
        with(binding) {
            buttonOkDestino.setOnClickListener {
                if (editTextDestino.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "DESTINO"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.setDestino(editTextDestino.text.toString(), flowApp, pos)
            }
            buttonCancDestino.setOnClickListener {
                when(flowApp){
                    FlowApp.ADD -> {
                        setBundlePassagList(TypeAddOcupante.ADDPASSAGEIRO, 0)
                        fragmentAttachListenerProprio?.goPassagList()
                    }
                    FlowApp.CHANGE -> {
                        setBundleDetalhe(pos)
                        fragmentAttachListenerProprio?.goDetalhe()
                    }
                }
            }
        }
    }

    private fun handleStateChange(state: DestinoProprioFragmentState) {
        when (state) {
            is DestinoProprioFragmentState.CheckSetDestino -> handleCheckSetDestino(state.check)
            is DestinoProprioFragmentState.GoFragmentNotaFiscal -> handleGoFragmentNotaFiscal()
            is DestinoProprioFragmentState.GoFragmentObserv -> handleGoFragmentObserv()
        }
    }

    private fun handleCheckSetDestino(checkSetMatricColab: Boolean) {
        if (checkSetMatricColab) {
            when(flowApp){
                FlowApp.ADD -> viewModel.checkNextFragment()
                FlowApp.CHANGE -> {
                    setBundleDetalhe(pos)
                    fragmentAttachListenerProprio?.goDetalhe()
                }
            }
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "DESTINO"
            ), requireContext()
        )
    }

    private fun handleGoFragmentNotaFiscal(){
        setBundleNotaFiscal(FlowApp.ADD, 0)
        fragmentAttachListenerProprio?.goNotaFiscal()
    }

    private fun handleGoFragmentObserv(){
        setBundleObserv(FlowApp.ADD, 0)
        fragmentAttachListenerProprio?.goObserv()
    }

    private fun setBundleDetalhe(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_PROPRIO, bundle)
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

    private fun setBundlePassagList(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_COLAB_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_COLAB_LIST, bundle)
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