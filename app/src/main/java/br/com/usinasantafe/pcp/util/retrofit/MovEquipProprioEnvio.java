package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.control.MovEquipProprioCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.dao.AtualAplicDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovEquipProprioEnvio {

    public MovEquipProprioEnvio() {
    }

    public void envioDadosMovEquipProprio(List<MovEquipProprioBean> movEquipProprioList, String activity){

        try {

            AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
            MovEquipProprioDao movEquipProprioDao = ConnRetrofit.getInstance().conn().create(MovEquipProprioDao.class);
            Call<List<MovEquipProprioBean>> call = movEquipProprioDao.envioDadosMovEquipProprio(movEquipProprioList, "Bearer " + atualAplicDAO.token());
            call.enqueue(new Callback<List<MovEquipProprioBean>>() {

                @Override
                public void onResponse(Call<List<MovEquipProprioBean>> call, Response<List<MovEquipProprioBean>> response) {
                    MovEquipProprioCTR movEquipProprioCTR = new MovEquipProprioCTR();
                    movEquipProprioCTR.updateMovEquipProprio(response.body(), activity);
                }

                @Override
                public void onFailure(Call<List<MovEquipProprioBean>> call, Throwable t) {
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
