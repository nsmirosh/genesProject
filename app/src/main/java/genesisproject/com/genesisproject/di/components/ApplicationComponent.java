package genesisproject.com.genesisproject.di.components;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import javax.inject.Singleton;

import dagger.Component;
import genesisproject.com.genesisproject.di.application.GenApplication;
import genesisproject.com.genesisproject.di.modules.AndroidModule;
import genesisproject.com.genesisproject.di.modules.AppModule;
import genesisproject.com.genesisproject.di.modules.NetworkModule;
import genesisproject.com.genesisproject.di.modules.UtilsModule;

/**
 * Created by nick on 19.02.17.
 */

@Singleton
@Component(modules = {AndroidModule.class, AppModule.class, UtilsModule.class, NetworkModule.class})
public interface ApplicationComponent extends AbstractComponent {

    GenApplication getApp();

    Resources provideResources();

    Context getContext();

    Gson getGson();

    LayoutInflater getLayoutInflater();
}
