package genesisproject.com.genesisproject.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import genesisproject.com.genesisproject.R;
import genesisproject.com.genesisproject.di.application.GenApplication;
import genesisproject.com.genesisproject.utils.ServerErrorHandlerUtil;
import genesisproject.com.genesisproject.utils.ToastUtils;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nsmirosh on 6/17/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements ServerErrorHandlerUtil.OnErrorListener{

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    ServerErrorHandlerUtil mServerErrorHandlerUtil = new ServerErrorHandlerUtil();

    @Inject
    ToastUtils mToastUtils;

    protected ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GenApplication) getApplication()).component().inject(this);
        mServerErrorHandlerUtil.setListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
    }





    public void onRequestError(Throwable throwable) {
        Timber.e(throwable.getMessage(), throwable);
//        hideProgressDialog();

        String errorText = mServerErrorHandlerUtil.checkCause(throwable);
        if (errorText != null && errorText.length() > 0) {
            mToastUtils.showToast(errorText);
        }
    }


    @Override
    public void onHttpError(int responseCode) {
        //никакого специфического error-handling не добавляю
        mToastUtils.showToast("" + responseCode);

    }

    @Override
    public void onNoNetworkException() {
        mToastUtils.showToast(R.string.no_network_connection_available);
    }


}
