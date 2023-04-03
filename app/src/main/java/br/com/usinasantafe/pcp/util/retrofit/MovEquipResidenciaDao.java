package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MovEquipResidenciaDao {

    @POST("inserirmovequipresidencia.php")
    Call<List<MovEquipResidenciaBean>> envioDadosMovEquipResidencia(@Body List<MovEquipResidenciaBean> movEquipEquipResidenciaList);

}
