package br.com.usinasantafe.pcp.util.retrofit;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MovEquipVisitTercDao {

    @POST("inserirmovequipvisitterc.php")
    Call<List<MovEquipVisitTercBean>> envioDadosMovEquipVisitTerc(@Body List<MovEquipVisitTercBean> movEquipVisitTercList);

}
