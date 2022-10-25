package br.com.usinasantafe.pcp;

import android.app.Application;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.control.MovimentacaoVeicProprioCTR;
import br.com.usinasantafe.pcp.control.MovimentacaoVeicVisTercCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;

public class PCPContext extends Application {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private ConfigCTR configCTR;
    private MovimentacaoVeicProprioCTR movimentacaoVeicProprioCTR;
    private MovimentacaoVeicVisTercCTR movimentacaoVeicVisTercCTR;

    public static String versaoWS = "1.00";

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public MovimentacaoVeicProprioCTR getMovimentacaoVeicProprioCTR(){
        if (movimentacaoVeicProprioCTR == null)
            movimentacaoVeicProprioCTR = new MovimentacaoVeicProprioCTR();
        return movimentacaoVeicProprioCTR;
    }

    public MovimentacaoVeicVisTercCTR getMovimentacaoVeicVisTercCTR(){
        if (movimentacaoVeicVisTercCTR == null)
            movimentacaoVeicVisTercCTR = new MovimentacaoVeicVisTercCTR();
        return movimentacaoVeicVisTercCTR;
    }

    public ConfigCTR getConfigCTR(){
        if (configCTR == null)
            configCTR = new ConfigCTR();
        return configCTR;
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            LogErroDAO.getInstance().insertLogErro(ex);
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        }
    };

}
