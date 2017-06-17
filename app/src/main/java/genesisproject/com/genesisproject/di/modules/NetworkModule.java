package genesisproject.com.genesisproject.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import genesisproject.com.genesisproject.BuildConfig;
import genesisproject.com.genesisproject.http.GenApi;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

	private static final int TIMEOUT = 20000;

	@Provides
	@Singleton
	@GenApiUrl
	String provideTestEndpoint() {
		return BuildConfig.BASE_URL;
	}

	@Provides
	@Singleton
	GenApi provideOdsApi(Retrofit restAdapter) {
		return restAdapter.create(GenApi.class);
	}

	@Provides
	@Singleton
    Retrofit provideRestAdapter(@GenApiUrl String baseUrl, OkHttpClient httpClient,
								GsonConverterFactory gsonConverterFactory) {

		Retrofit.Builder retrofitBuilder =
				new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory)
						.addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient);
		return retrofitBuilder.build();
	}

	@Provides
	@Singleton
    OkHttpClient provideHttpClient() {

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		if (BuildConfig.DEBUG) {
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		} else {
			logging.setLevel(HttpLoggingInterceptor.Level.NONE);
		}

		return new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.addInterceptor(logging)
				.build();
	}


	@Provides
	@Singleton
    GsonConverterFactory provideGsonConverterFactory() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson myGson = gsonBuilder.create();
		return GsonConverterFactory.create(myGson);
	}
}
