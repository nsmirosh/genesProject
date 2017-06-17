package genesisproject.com.genesisproject.di.application;

import android.support.multidex.MultiDexApplication;

import genesisproject.com.genesisproject.di.components.AbstractComponent;
import genesisproject.com.genesisproject.di.components.DaggerApplicationComponent;
import genesisproject.com.genesisproject.di.modules.AndroidModule;
import genesisproject.com.genesisproject.di.modules.AppModule;
import genesisproject.com.genesisproject.di.modules.NetworkModule;
import genesisproject.com.genesisproject.di.modules.UtilsModule;
import timber.log.Timber;

/**
 * Created by nsmirosh on 6/17/17.
 */

public class GenApplication  extends MultiDexApplication {

    private final AbstractComponent component = createComponent();

    protected AbstractComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .utilsModule(new UtilsModule())
                .appModule(new AppModule())
                .networkModule(new NetworkModule())
                .androidModule(new AndroidModule(this))
                .build();
    }

    public AbstractComponent component() {
        return component;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        createComponent();
        component().inject(this);
        Timber.plant(new Timber.DebugTree());
    }


}
