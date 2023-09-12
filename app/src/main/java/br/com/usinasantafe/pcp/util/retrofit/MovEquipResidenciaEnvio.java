package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.control.MovEquipResidenciaCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.dao.AtualAplicDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovEquipResidenciaEnvio {

    public MovEquipResidenciaEnvio() {
    }

    public void envioDadosMovEquipResidencia(List<MovEquipResidenciaBean> movEquipResidenciaList, String activity){

        try {

            AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
            MovEquipResidenciaDao movEquipResidenciaDao = ConnRetrofit.getInstance().conn().create(MovEquipResidenciaDao.class);
            Call<List<MovEquipResidenciaBean>> call = movEquipResidenciaDao.envioDadosMovEquipResidencia(movEquipResidenciaList, "Bearer " + atualAplicDAO.token());
            call.enqueue(new Callback<List<MovEquipResidenciaBean>>() {
                @Override
                public void onResponse(Call<List<MovEquipResidenciaBean>> call, Response<List<MovEquipResidenciaBean>> response) {
                    MovEquipResidenciaCTR movEquipResidenciaCTR = new MovEquipResidenciaCTR();
                    movEquipResidenciaCTR.updateMovEquipResidencia(response.body(), activity);
                }

                @Override
                public void onFailure(Call<List<MovEquipResidenciaBean>> call, Throwable t) {
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
