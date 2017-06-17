package genesisproject.com.genesisproject.di.modules;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import genesisproject.com.genesisproject.di.application.GenApplication;

/**
 * Created by nick on 19.02.17.
 */
@Module
public class AndroidModule {

    private final GenApplication mBaseApplication;

    public AndroidModule(GenApplication baseApplication) {
        this.mBaseApplication = baseApplication;
    }

    @Provides
    @Singleton
    GenApplication provideBaseApplication() {
        return mBaseApplication;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mBaseApplication;
    }
}
