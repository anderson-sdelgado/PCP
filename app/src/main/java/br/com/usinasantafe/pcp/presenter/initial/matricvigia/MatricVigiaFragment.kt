package br.com.usinasantafe.pcp.presenter.initial.matricvigia

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.BaseFragment
import br.com.usinasantafe.pcp.utils.GenericDialogProgressBar
import br.com.usinasantafe.pcp.utils.onBackPressed
import br.com.usinasantafe.pcp.utils.setListenerButtonsGeneric
import br.com.usinasantafe.pcp.utils.showGenericAlertDialog
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.databinding.FragmentMatricVigiaBinding
import br.com.usinasantafe.pcp.presenter.initial.FragmentAttachListenerInitial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatricVigiaFragment : BaseFragment<FragmentMatricVigiaBinding>(
    R.layout.fragment_matric_vigia,
    FragmentMatricVigiaBinding::bind
) {

    private val viewModel: MatricVigiaViewModel by viewModels()
    private var genericDialogProgressBar: GenericDialogProgressBar? = null
    private var fragmentAttachListenerInitial: FragmentAttachListenerInitial? = null
    private lateinit var describeUpdate: String

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
            setListenerButtonsGeneric(layoutBotoes, editTextPadrao)
            layoutBotoes.buttonOkPadrao.setOnClickListener {
                if (editTextPadrao.text.isEmpty()) {
                    showGenericAlertDialog(
                        getString(
                            R.string.texto_campo_vazio,
                            "MATRICULA DO VIGIA"
                        ), requireContext()
                    )
                    return@setOnClickListener
                }
                viewModel.checkMatricVigia(editTextPadrao.text.toString())
            }
            layoutBotoes.buttonAtualPadrao.setOnClickListener {
                viewModel.updateDataColab()
            }
        }
    }

    private fun handleStateChange(state: MatricVigiaFragmentState) {
        when (state) {
            is MatricVigiaFragmentState.CheckMatric -> handleCheckMatric(state.checkMatric)
            is MatricVigiaFragmentState.CheckSetMatric -> handleCheckSetMatricVigia(state.checkSetMatric)
            is MatricVigiaFragmentState.FeedbackUpdate -> handleFeedbackUpdate(state.statusUpdate)
            is MatricVigiaFragmentState.SetResultUpdate -> handleSetResultUpdate(state.resultUpdateDatabase)
        }
    }

    private fun handleCheckMatric(checkMatric: Boolean) {
        if (checkMatric) {
            viewModel.checkSetMatricVigia(binding.editTextPadrao.text.toString())
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_dado_invalido_com_atual,
                "MATRICULA DO VIGIA"
            ), requireContext()
        )
    }

    private fun handleCheckSetMatricVigia(checkSetMatricVigia: Boolean) {
        if (checkSetMatricVigia) {
            fragmentAttachListenerInitial?.goNomeVigia()
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "MATRICULA DO VIGIA"
            ), requireContext()
        )
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
                showGenericAlertDialog(getString(R.string.texto_msg_atualizacao, "COLABORADORES"),
                    requireContext())
            }
            StatusUpdate.FAILURE -> {
                hideProgressBar()
                showGenericAlertDialog(
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerInitial) {
            fragmentAttachListenerInitial = context
        }
        onBackPressed {
            fragmentAttachListenerInitial?.goSplash()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerInitial = null
    }

}