package br.com.usinasantafe.pcp.presenter.proprio.veiculo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.GenericDialogProgressBar
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.setListenerButtonsGeneric
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.showToast
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.databinding.FragmentVeiculoProprioBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.POS_VEICULO_SEG
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.REQUEST_KEY_VEICULO_SEG
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment.Companion.TYPE_ADD_EQUIP_VEICULO_SEG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VeiculoProprioFragment : BaseFragment<FragmentVeiculoProprioBinding>(
    R.layout.fragment_veiculo_proprio,
    FragmentVeiculoProprioBinding::bind
) {

    private val viewModel: VeiculoProprioViewModel by viewModels()
    private var genericDialogProgressBar: GenericDialogProgressBar? = null
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var describeUpdate: String
    private lateinit var typeAddEquip : TypeAddEquip
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_VEICULO_PROPRIO = "requestKeyVeiculoProprio"
        const val TYPE_ADD_EQUIP_VEICULO_PROPRIO = "typeAddEquipVeiculoProprio"
        const val POS_VEICULO_PROPRIO = "posVeiculoProprio"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_VEICULO_PROPRIO) { _, bundle ->
            this.typeAddEquip = TypeAddEquip.values()[bundle.getInt(TYPE_ADD_EQUIP_VEICULO_PROPRIO)]
            this.pos = bundle.getInt(POS_VEICULO_PROPRIO)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setListener()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun observeState() {
        viewModel.uiLiveData.observe(viewLifecycleOwner) { state ->
            handleStateChange(state)
        }
    }

    private fun setListener() {
        with(binding) {
            setListenerButtonsGeneric(layoutBotoes, editTextPadrao)
            layoutBotoes.buttonOkPadrao.setOnClickListener {
                if (editTextPadrao.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "NUMERO DO EQUIPAMENTO"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.checkNroVeiculo(editTextPadrao.text.toString())
            }
            layoutBotoes.buttonAtualPadrao.setOnClickListener {
                viewModel.updateDataEquip()
            }
        }
    }

    private fun handleStateChange(state: VeiculoProprioFragmentState) {
        when (state) {
            is VeiculoProprioFragmentState.CheckEquip -> handleCheckNroEquip(state.checkNroEquip)
            is VeiculoProprioFragmentState.CheckSetVeicEquipSeg -> handleCheckSetNroEquip(state.checkSetNroVeiculo)
            is VeiculoProprioFragmentState.FeedbackUpdate -> handleFeedbackUpdate(state.statusUpdate)
            is VeiculoProprioFragmentState.SetResultUpdate -> handleSetResultUpdate(state.resultUpdateDatabase)
        }
    }

    private fun handleCheckNroEquip(checkMatric: Boolean) {
        if (checkMatric) {
            viewModel.setNroVeiculo(binding.editTextPadrao.text.toString(), typeAddEquip, pos)
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_dado_invalido_com_atual,
                "NUMERO DO EQUIPAMENTO"
            ), requireContext()
        )
    }

    private fun handleCheckSetNroEquip(checkSetMatricColab: Boolean) {
        if (checkSetMatricColab) {
            when(typeAddEquip) {
                TypeAddEquip.ADDVEICULO -> {
                    setBundleVeicSegProprio(TypeAddEquip.ADDVEICULOSEG, 0)
                    fragmentAttachListenerProprio?.goVeicSegList()
                }
                TypeAddEquip.CHANGEVEICULO -> {
                    setBundleDetalhe(pos)
                    fragmentAttachListenerProprio?.goDetalhe()
                }
                TypeAddEquip.ADDVEICULOSEG,
                TypeAddEquip.CHANGEVEICULOSEG -> {
                    setBundleVeicSegProprio(typeAddEquip, pos)
                    fragmentAttachListenerProprio?.goVeicSegList()
                }
            }
        } else {
            showGenericAlertDialog(
                getString(
                    R.string.texto_falha_insercao_campo,
                    "NUMERO DO EQUIPAMENTO",
                ), requireContext()
            )
        }
    }

    private fun handleSetResultUpdate(resultUpdateDatabase: ResultUpdateDatabase?) {
        resultUpdateDatabase?.let {
            if (genericDialogProgressBar == null) {
                showProgressBar()
            }
            describeUpdate = resultUpdateDatabase.describe
            genericDialogProgressBar!!.setValue(resultUpdateDatabase)
        }
    }

    private fun handleFeedbackUpdate(statusUpdate: StatusUpdate) {
        when (statusUpdate) {
            StatusUpdate.UPDATED -> {
                hideProgressBar()
                showToast(
                    getString(R.string.texto_msg_atualizacao, "EQUIPAMENTO"),
                    requireContext()
                )
            }
            StatusUpdate.FAILURE -> {
                hideProgressBar()
                showToast(
                    getString(R.string.texto_update_failure, describeUpdate),
                    requireContext()
                )
            }
        }
    }

    private fun showProgressBar() {
        genericDialogProgressBar = GenericDialogProgressBar(requireContext())
        genericDialogProgressBar!!.show()
        genericDialogProgressBar!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

    private fun hideProgressBar() {
        if (genericDialogProgressBar != null) {
            genericDialogProgressBar!!.cancel()
        }
        genericDialogProgressBar = null
    }

    private fun setBundleDetalhe(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_PROPRIO, bundle)
    }

    private fun setBundleVeicSegProprio(typeAddEquip: TypeAddEquip, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_EQUIP_VEICULO_SEG, typeAddEquip.ordinal)
        bundle.putInt(POS_VEICULO_SEG, pos)
        setFragmentResult(REQUEST_KEY_VEICULO_SEG, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerProprio) {
            fragmentAttachListenerProprio = context
        }
        onBackPressed {
            when(typeAddEquip) {
                TypeAddEquip.ADDVEICULO -> fragmentAttachListenerProprio?.goMovProprioList()
                TypeAddEquip.CHANGEVEICULO -> {
                    setBundleDetalhe(pos)
                    fragmentAttachListenerProprio?.goDetalhe()
                }
                TypeAddEquip.ADDVEICULOSEG,
                TypeAddEquip.CHANGEVEICULOSEG -> {
                    setBundleVeicSegProprio(typeAddEquip, pos)
                    fragmentAttachListenerProprio?.goVeicSegList()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerProprio = null
    }

}