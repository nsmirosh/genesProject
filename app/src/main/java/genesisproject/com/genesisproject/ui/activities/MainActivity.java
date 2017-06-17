package genesisproject.com.genesisproject.ui.activities;


import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.FragmentManager;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import genesisproject.com.genesisproject.di.application.GenApplication;
import genesisproject.com.genesisproject.R;
import genesisproject.com.genesisproject.http.GenApi;
import genesisproject.com.genesisproject.models.GithubResponse;
import genesisproject.com.genesisproject.models.Repo;
import genesisproject.com.genesisproject.ui.dialogs.RepoViewDialogFragment;
import genesisproject.com.genesisproject.ui.mvc.MainActivityMvc;
import genesisproject.com.genesisproject.ui.mvc.MainActivityMvcImpl;
import genesisproject.com.genesisproject.utils.PreferenceUtils;
import genesisproject.com.genesisproject.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static genesisproject.com.genesisproject.ui.dialogs.RepoViewDialogFragment.ARGS_REPO_URL;

public class MainActivity extends BaseActivity implements MainActivityMvc.InteractionListener {


    public static final String SAVED_INSTANCE_REPO_LIST = "saved_instance_repo_list";

    @Inject
    ToastUtils mToastUtils;

    @Inject
    GenApi mGenApi;

    @Inject
    PreferenceUtils mPreferenceUtils;

    MainActivityMvcImpl mMainActivityMvcImpl;


    Call<GithubResponse> mCall;

    List<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GenApplication) getApplication()).component().inject(this);

        //по хорошему нужно было это инжектнуть - но какой-то баг валится из-за support library, решил так создать
        mMainActivityMvcImpl  = new MainActivityMvcImpl(getLayoutInflater(), this);
        mMainActivityMvcImpl.setListener(this);
        setContentView(mMainActivityMvcImpl.getRootView());

        if (savedInstanceState != null) {
            mRepos.clear();
            List<Repo> data = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_INSTANCE_REPO_LIST));
            mRepos.addAll(data);
            mMainActivityMvcImpl.bindData(mRepos);
        }
    }


    @Override
    public void onQueryTyped(String query) {
        search(query);

    }

    private void search(String query) {
        mCall = mGenApi.searchRepositories(query, "stars");
        mCall.enqueue(new Callback<GithubResponse>() {
            @Override
            public void onResponse(Call<GithubResponse> call, Response<GithubResponse> response) {
                onSearchResult(response);
            }

            @Override
            public void onFailure(Call<GithubResponse> call, Throwable t) {
                if (call.isCanceled()) {
                   mToastUtils.showToast("request was cancelled");
                }
                else {
                    mServerErrorHandlerUtil.checkCause(t);
                }

            }
        });
    }


    private void onSearchResult(Response<GithubResponse> response) {
        mMainActivityMvcImpl.hideProgressBar();
        if (response.isSuccessful()) {
            mRepos = response.body().getItems();
            mMainActivityMvcImpl.bindData(mRepos);
            mPreferenceUtils.saveResults(mRepos);
        }
    }

    @Override
    public void onRepoClicked(Repo repo) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RepoViewDialogFragment repoViewDialogFragment;
        if (fragmentManager.findFragmentByTag(RepoViewDialogFragment.FRAGMENT_TAG) == null) {
            repoViewDialogFragment = new RepoViewDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ARGS_REPO_URL, repo.getUrl());
            repoViewDialogFragment.setArguments(bundle);
        }
        else {
            repoViewDialogFragment = (RepoViewDialogFragment) fragmentManager.findFragmentByTag(RepoViewDialogFragment.FRAGMENT_TAG);
        }

        repoViewDialogFragment.show(getSupportFragmentManager(),RepoViewDialogFragment.FRAGMENT_TAG);
    }

    @Override
    public void showPreviousResults() {
        if (mPreferenceUtils.getPreviousResults() != null) {
            mRepos = mPreferenceUtils.getPreviousResults();
            mMainActivityMvcImpl.bindData(mRepos);
        }
        else {
            mToastUtils.showToast(R.string.nothing_saved_in_cache_yet);
        }
    }

    @Override
    public void onSearchClosed() {
        //сбрасываем запрос
        if (mCall != null) {
            mCall.cancel();
            mCall = null;
        }
        else {
            mToastUtils.showToast(R.string.no_request_to_cancel_yet);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_REPO_LIST, Parcels.wrap(mRepos));
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mRepos.clear();
            List<Repo> data = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_INSTANCE_REPO_LIST));
            mRepos.addAll(data);
            mMainActivityMvcImpl.bindData(mRepos);
        }
    }
}
