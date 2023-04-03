package br.com.usinasantafe.pcp;

import android.app.Application;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.control.MovEquipProprioCTR;
import br.com.usinasantafe.pcp.control.MovEquipResidenciaCTR;
import br.com.usinasantafe.pcp.control.MovEquipVisitTercCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;

public class PCPContext extends Application {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private ConfigCTR configCTR;
    private MovEquipProprioCTR movEquipProprioCTR;
    private MovEquipVisitTercCTR movEquipVisitTercCTR;
    private MovEquipResidenciaCTR movEquipResidenciaCTR;

    public static String versaoWS = "3.00";

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public MovEquipProprioCTR getMovVeicProprioCTR(){
        if (movEquipProprioCTR == null)
            movEquipProprioCTR = new MovEquipProprioCTR();
        return movEquipProprioCTR;
    }

    public MovEquipVisitTercCTR getMovVeicVisitTercCTR(){
        if (movEquipVisitTercCTR == null)
            movEquipVisitTercCTR = new MovEquipVisitTercCTR();
        return movEquipVisitTercCTR;
    }

    public MovEquipResidenciaCTR getMovVeicResidenciaCTR(){
        if (movEquipResidenciaCTR == null)
            movEquipResidenciaCTR = new MovEquipResidenciaCTR();
        return movEquipResidenciaCTR;
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
