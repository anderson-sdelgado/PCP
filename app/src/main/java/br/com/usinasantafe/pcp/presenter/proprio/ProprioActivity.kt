package br.com.usinasantafe.pcp.presenter.proprio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.replaceFragment
import br.com.usinasantafe.pcp.databinding.ActivityProprioBinding
import br.com.usinasantafe.pcp.presenter.initial.InitialActivity
import br.com.usinasantafe.pcp.presenter.initial.InitialActivity.Companion.FlowInitial
import br.com.usinasantafe.pcp.presenter.initial.InitialActivity.Companion.KEY_FLOW_INITIAL
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioFragment
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioFragment
import br.com.usinasantafe.pcp.presenter.proprio.matric.MatricColabFragment
import br.com.usinasantafe.pcp.presenter.proprio.movequip.MovEquipProprioListFragment
import br.com.usinasantafe.pcp.presenter.proprio.nome.NomeColabFragment
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioFragment
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioFragment
import br.com.usinasantafe.pcp.presenter.proprio.passag.PassagColabListFragment
import br.com.usinasantafe.pcp.presenter.proprio.veiculo.VeiculoProprioFragment
import br.com.usinasantafe.pcp.presenter.proprio.veiculoseg.VeiculoSegProprioListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProprioActivity : AppCompatActivity(), FragmentAttachListenerProprio {

    private lateinit var binding: ActivityProprioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProprioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goMovProprioList()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun goMovProprioList() {
        replaceFragment(MovEquipProprioListFragment())
    }

    override fun goInicial() {
        val intent = Intent(this, InitialActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.apply {
            putExtra(KEY_FLOW_INITIAL, FlowInitial.RETURN.ordinal)
        }
        startActivity(intent)
    }

    override fun goVeiculoProprio() {
        replaceFragment(VeiculoProprioFragment())
    }

    override fun goMatricColab() {
        replaceFragment(MatricColabFragment())
    }

    override fun goPassagList() {
        replaceFragment(PassagColabListFragment())
    }

    override fun goVeicSegList() {
        replaceFragment(VeiculoSegProprioListFragment())
    }

    override fun goDestino() {
        replaceFragment(DestinoProprioFragment())
    }

    override fun goNotaFiscal() {
        replaceFragment(NotaFiscalProprioFragment())
    }

    override fun goObserv() {
        replaceFragment(ObservProprioFragment())
    }

    override fun goDetalhe() {
        replaceFragment(DetalheMovEquipProprioFragment())
    }

    override fun goNomeColab() {
        replaceFragment(NomeColabFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        replaceFragment(R.id.proprio_manager_fragment, fragment)
    }

}