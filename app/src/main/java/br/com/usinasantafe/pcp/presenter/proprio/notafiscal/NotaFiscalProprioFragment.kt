package br.com.usinasantafe.pcp.presenter.proprio.notafiscal

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.setListenerButtonsGenericSUpdate
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.databinding.FragmentNotaFiscalProprioBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.FLOW_APP_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.POS_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment.Companion.REQUEST_KEY_DESTINO_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.FLOW_APP_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.POS_OBSERV_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment.Companion.REQUEST_KEY_OBSERV_PROPRIO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotaFiscalProprioFragment : BaseFragment<FragmentNotaFiscalProprioBinding>(
    R.layout.fragment_nota_fiscal_proprio,
    FragmentNotaFiscalProprioBinding::bind
) {

    private val viewModel: NotaFiscalProprioViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var flowApp: FlowApp
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_NOTA_FISCAL_PROPRIO = "requestKeyNotaFiscalProprio"
        const val FLOW_APP_NOTA_FISCAL_PROPRIO = "flowAppNotaFiscalProprio"
        const val POS_NOTA_FISCAL_PROPRIO = "posNotaFiscalProprio"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_NOTA_FISCAL_PROPRIO) { _, bundle ->
            this.flowApp = FlowApp.values()[bundle.getInt(FLOW_APP_NOTA_FISCAL_PROPRIO)]
            this.pos = bundle.getInt(POS_NOTA_FISCAL_PROPRIO)
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
            setListenerButtonsGenericSUpdate(layoutBotoes, editTextPadrao)
            layoutBotoes.buttonOkPadrao.setOnClickListener {
                if (editTextPadrao.text.isEmpty()) {
                    setBundleObserv(flowApp, 0)
                    fragmentAttachListenerProprio?.goObserv()
                    return@setOnClickListener
                }
                viewModel.setNotaFiscal(editTextPadrao.text.toString(), flowApp, pos)
            }
        }
    }

    private fun handleStateChange(state: NotaFiscalProprioFragmentState) {
        when (state) {
            is NotaFiscalProprioFragmentState.CheckSetNotaFiscal -> handleCheckSetNotaFiscal(state.check)
        }
    }

    private fun handleCheckSetNotaFiscal(check: Boolean) {
        if (check) {
            when(flowApp) {
                FlowApp.ADD -> {
                    setBundleObserv(flowApp, 0)
                    fragmentAttachListenerProprio?.goObserv()
                }
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

    private fun setBundleDestino(flowApp: FlowApp, pos: Int){
        val bundle = Bundle()
        bundle.putInt(FLOW_APP_DESTINO_PROPRIO, flowApp.ordinal)
        bundle.putInt(POS_DESTINO_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DESTINO_PROPRIO, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerProprio){
            fragmentAttachListenerProprio = context
        }
        onBackPressed {
            when(flowApp) {
                FlowApp.ADD -> {
                    setBundleDestino(flowApp, 0)
                    fragmentAttachListenerProprio?.goDestino()
                }
                FlowApp.CHANGE -> {
                    setBundleDetalhe(pos)
                    fragmentAttachListenerProprio?.goDetalhe()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerProprio = null
    }

}