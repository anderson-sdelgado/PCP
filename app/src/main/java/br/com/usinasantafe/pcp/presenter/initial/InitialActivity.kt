package br.com.usinasantafe.pcp.presenter.initial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.utils.replaceFragment
import br.com.usinasantafe.pcp.databinding.ActivityInitialBinding
import br.com.usinasantafe.pcp.presenter.initial.config.ConfigFragment
import br.com.usinasantafe.pcp.presenter.initial.local.LocalListFragment
import br.com.usinasantafe.pcp.presenter.initial.matricvigia.MatricVigiaFragment
import br.com.usinasantafe.pcp.presenter.initial.menuapont.MenuApontListFragment
import br.com.usinasantafe.pcp.presenter.initial.menuinicial.MenuInicialFragment
import br.com.usinasantafe.pcp.presenter.initial.nomevigia.NomeVigiaFragment
import br.com.usinasantafe.pcp.presenter.initial.senha.SenhaFragment
import br.com.usinasantafe.pcp.presenter.proprio.ProprioActivity
import br.com.usinasantafe.pcp.presenter.residencia.ResidenciaActivity
import br.com.usinasantafe.pcp.presenter.splash.SplashActivity
import br.com.usinasantafe.pcp.presenter.visitterc.VisitTercActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialActivity : AppCompatActivity(), FragmentAttachListenerInitial {

    private lateinit var binding: ActivityInitialBinding

    companion object {
        const val KEY_FLOW_INITIAL = "key_flow_initial";
        enum class FlowInitial { START, RETURN }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val flowInitial = FlowInitial.values()[bundle?.getInt(KEY_FLOW_INITIAL)!!]

        if(flowInitial == FlowInitial.START){
            goMenuInicial()
        } else {
            goMenuApont()
        }
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun goSenha() {
        replaceFragment(SenhaFragment())
    }

    override fun goMovVisitTerc() {
        val intent = Intent(this, VisitTercActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun goMovProprio() {
        val intent = Intent(this, ProprioActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun goMovResidencia() {
        val intent = Intent(this, ResidenciaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun goConfig() {
        replaceFragment(ConfigFragment())
    }

    override fun goLocalInicial() {
        replaceFragment(LocalListFragment())
    }

    override fun goMenuInicial() {
        replaceFragment(MenuInicialFragment())
    }

    override fun goNomeVigia() {
        replaceFragment(NomeVigiaFragment())
    }

    override fun goMatricVigia() {
        replaceFragment(MatricVigiaFragment())
    }

    override fun goMenuApont() {
        replaceFragment(MenuApontListFragment())
    }

    override fun goSplash() {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment){
        replaceFragment(R.id.initial_manager_fragment, fragment)
    }
}