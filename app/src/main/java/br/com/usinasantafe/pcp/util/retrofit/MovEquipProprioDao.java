package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MovEquipProprioDao {

    @POST("inserirmovequipproprio.php")
    Call<List<MovEquipProprioBean>> envioDadosMovEquipProprio(@Body List<MovEquipProprioBean> movEquipProprioList);

}
