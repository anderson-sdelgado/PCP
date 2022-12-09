package br.com.usinasantafe.pcp;

import android.app.Application;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.control.MovVeicProprioCTR;
import br.com.usinasantafe.pcp.control.MovVeicResidenciaCTR;
import br.com.usinasantafe.pcp.control.MovVeicVisitTercCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;

public class PCPContext extends Application {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private ConfigCTR configCTR;
    private MovVeicProprioCTR movVeicProprioCTR;
    private MovVeicVisitTercCTR movVeicVisitTercCTR;
    private MovVeicResidenciaCTR movVeicResidenciaCTR;

    public static String versaoWS = "2.00";

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public MovVeicProprioCTR getMovVeicProprioCTR(){
        if (movVeicProprioCTR == null)
            movVeicProprioCTR = new MovVeicProprioCTR();
        return movVeicProprioCTR;
    }

    public MovVeicVisitTercCTR getMovVeicVisitTercCTR(){
        if (movVeicVisitTercCTR == null)
            movVeicVisitTercCTR = new MovVeicVisitTercCTR();
        return movVeicVisitTercCTR;
    }

    public MovVeicResidenciaCTR getMovVeicResidenciaCTR(){
        if (movVeicResidenciaCTR == null)
            movVeicResidenciaCTR = new MovVeicResidenciaCTR();
        return movVeicResidenciaCTR;
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
