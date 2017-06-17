package genesisproject.com.genesisproject.di.modules;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  @Provides
  Resources provideResources(Context context) {
    return context.getResources();
  }

  @Provides Gson provideGson() {
    return new Gson();
  }

  @Provides
  LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }


}
