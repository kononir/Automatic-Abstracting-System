package by.bsuir.autoabstrsys.searcher.impl;

import by.bsuir.autoabstrsys.model.OstisResponse;
import by.bsuir.autoabstrsys.searcher.OstisService;
import by.bsuir.autoabstrsys.searcher.Searcher;
import by.bsuir.autoabstrsys.searcher.SearcherException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class SearcherImpl implements Searcher {
    private static final String OSTIS_API = "http://ims.ostis.net/api/";

    private OstisService ostisService;

    public SearcherImpl() {
        setUp();
    }

    private void setUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OSTIS_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ostisService = retrofit.create(OstisService.class);
    }

    @Override
    public OstisResponse search(String term) {
        try {
            Call<OstisResponse> call = ostisService.getOstisResponse(term);
            Response<OstisResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new SearcherException("Problems with response receiving: " + response.errorBody());
            }
        } catch (IOException e) {
            throw new SearcherException("Problems with response receiving", e);
        }
    }
}
