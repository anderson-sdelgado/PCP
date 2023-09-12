package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.control.MovEquipVisitTercCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.dao.AtualAplicDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovEquipVisitTercEnvio {

    public MovEquipVisitTercEnvio() {
    }

    public void envioDadosMovEquipVisitTerc(List<MovEquipVisitTercBean> movEquipVisitTercList, String activity){

        try {

            AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
            MovEquipVisitTercDao movEquipVisitTercDao = ConnRetrofit.getInstance().conn().create(MovEquipVisitTercDao.class);
            Call<List<MovEquipVisitTercBean>> call = movEquipVisitTercDao.envioDadosMovEquipVisitTerc(movEquipVisitTercList, "Bearer " + atualAplicDAO.token());
            call.enqueue(new Callback<List<MovEquipVisitTercBean>>() {
                @Override
                public void onResponse(Call<List<MovEquipVisitTercBean>> call, Response<List<MovEquipVisitTercBean>> response) {
                    MovEquipVisitTercCTR movEquipVisitTercCTR = new MovEquipVisitTercCTR();
                    movEquipVisitTercCTR.updateMovEquipVisitTerc(response.body(), activity);
                }

                @Override
                public void onFailure(Call<List<MovEquipVisitTercBean>> call, Throwable t) {
                    EnvioDadosServ.status = 1;
                    LogErroDAO.getInstance().insertLogErro(t);
                }
            });

        } catch (Exception e) {
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

}
