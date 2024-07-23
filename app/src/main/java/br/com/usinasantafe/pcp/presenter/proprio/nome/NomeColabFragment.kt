package br.com.usinasantafe.pcp.presenter.proprio.nome

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
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.databinding.FragmentNomeColabBinding
import br.com.usinasantafe.pcp.presenter.proprio.FragmentAttachListenerProprio
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.POS_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment.Companion.REQUEST_KEY_DETALHE_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.POS_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.REQUEST_KEY_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment.Companion.TYPE_ADD_OCUPANTE_MATRIC_COLAB
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.POS_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.REQUEST_KEY_PASSAG_COLAB_LIST
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment.Companion.TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NomeColabFragment : BaseFragment<FragmentNomeColabBinding>(
    R.layout.fragment_nome_colab,
    FragmentNomeColabBinding::bind
) {

    private val viewModel: NomeColabViewModel by viewModels()
    private var fragmentAttachListenerProprio: FragmentAttachListenerProprio? = null
    private lateinit var matricColab: String
    private lateinit var typeAddOcupante: TypeAddOcupante
    private var pos: Int = 0

    companion object {
        const val REQUEST_KEY_NOME_COLAB = "requestKeyNomeColab"
        const val TYPE_ADD_OCUPANTE_NOME_COLAB = "typeAddOcupanteNomeColab"
        const val MATRIC_COLAB = "matricColab"
        const val POS_NOME_COLAB = "posNomeColab"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_NOME_COLAB) { _, bundle ->
            this.typeAddOcupante = TypeAddOcupante.values()[bundle.getInt(
                TYPE_ADD_OCUPANTE_NOME_COLAB
            )]
            this.pos = bundle.getInt(POS_NOME_COLAB)
            this.matricColab = bundle.getString(MATRIC_COLAB, "")
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
        viewModel.recoverDataNome(matricColab)
    }

    private fun setListener() {
        with(binding) {
            buttonOkNome.setOnClickListener {
                viewModel.setMatricMotorista(matricColab, typeAddOcupante, pos)
            }
            buttonCancNome.setOnClickListener {
                setBundleMatricColab(typeAddOcupante, pos)
                fragmentAttachListenerProprio?.goMatricColab()
            }
        }
    }

    private fun handleStateChange(state: NomeColabFragmentState){
        when(state){
            is NomeColabFragmentState.CheckSetMatric -> handleCheckSetMatricColab(state.checkSetMatric)
            is NomeColabFragmentState.GetNomeColab -> handleNomeVigia(state.nomeColab)
        }
    }

    private fun handleNomeVigia(nome: String){
        with(binding) {
            textViewNome.text = nome
        }
    }

    private fun handleCheckSetMatricColab(checkSetMatricColab: Boolean) {
        if (checkSetMatricColab) {
            when(typeAddOcupante) {
                TypeAddOcupante.ADDMOTORISTA -> {
                    setBundlePassagList(TypeAddOcupante.ADDPASSAGEIRO, 0)
                    fragmentAttachListenerProprio?.goPassagList()
                }
                TypeAddOcupante.CHANGEMOTORISTA -> {
                    setBundleDetalhe(pos)
                    fragmentAttachListenerProprio?.goDetalhe()
                }
                TypeAddOcupante.ADDPASSAGEIRO,
                TypeAddOcupante.CHANGEPASSAGEIRO -> {
                    setBundlePassagList(typeAddOcupante, pos)
                    fragmentAttachListenerProprio?.goPassagList()
                }
            }
            return
        }
        showGenericAlertDialog(
            getString(
                R.string.texto_falha_insercao_campo,
                "MATRICULA DO COLABORADOR"
            ), requireContext()
        )
    }

    private fun setBundleDetalhe(pos: Int){
        val bundle = Bundle()
        bundle.putInt(POS_DETALHE_PROPRIO, pos)
        setFragmentResult(REQUEST_KEY_DETALHE_PROPRIO, bundle)
    }

    private fun setBundleMatricColab(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_MATRIC_COLAB, typeAddOcupante.ordinal)
        bundle.putInt(POS_MATRIC_COLAB, pos)
        setFragmentResult(REQUEST_KEY_MATRIC_COLAB, bundle)
    }

    private fun setBundlePassagList(typeAddOcupante: TypeAddOcupante, pos: Int){
        val bundle = Bundle()
        bundle.putInt(TYPE_ADD_OCUPANTE_PASSAG_COLAB_LIST, typeAddOcupante.ordinal)
        bundle.putInt(POS_PASSAG_COLAB_LIST, pos)
        setFragmentResult(REQUEST_KEY_PASSAG_COLAB_LIST, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerProprio){
            fragmentAttachListenerProprio = context
        }
        onBackPressed {}
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerProprio = null
    }

}