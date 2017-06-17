package genesisproject.com.genesisproject.http;

import java.util.List;

import genesisproject.com.genesisproject.models.GithubResponse;
import genesisproject.com.genesisproject.models.Repo;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nsmirosh on 6/17/17.
 */

public interface GenApi {

    @GET("search/repositories")
    Call<GithubResponse> searchRepositories(@Query("q") String query, @Query("sort") String sortBy);

}
