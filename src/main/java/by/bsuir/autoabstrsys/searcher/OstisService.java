package by.bsuir.autoabstrsys.searcher;

import by.bsuir.autoabstrsys.model.OstisResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OstisService {
    @GET("idtf/find/")
    Call<OstisResponse> getOstisResponse(@Query("substr") String substr);
}
