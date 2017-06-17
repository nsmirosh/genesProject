package genesisproject.com.genesisproject.di.components;


import genesisproject.com.genesisproject.di.application.GenApplication;
import genesisproject.com.genesisproject.ui.activities.BaseActivity;
import genesisproject.com.genesisproject.ui.activities.MainActivity;

public interface AbstractComponent {

    void inject(GenApplication app);

    void inject(MainActivity mainActivity);

    void inject(BaseActivity baseActivity);

}

