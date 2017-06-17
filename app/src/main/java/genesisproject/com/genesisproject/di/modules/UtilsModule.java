package genesisproject.com.genesisproject.di.modules;


import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import genesisproject.com.genesisproject.utils.ToastUtils;

@Module
public class UtilsModule {

    @Provides
    ToastUtils provideToastUtils(Context context, Resources resources) {
        return new ToastUtils(context, resources);
    }

   /* @Provides
    @Singleton
    PreferenceUtils providePreferenceUtils(Context context, Gson gson) {
        return new PreferenceUtils(context, gson);
    }*/

}
