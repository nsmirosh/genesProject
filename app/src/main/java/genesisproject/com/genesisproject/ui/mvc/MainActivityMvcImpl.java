package genesisproject.com.genesisproject.ui.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import genesisproject.com.genesisproject.R;
import genesisproject.com.genesisproject.adapters.RepoAdapter;
import genesisproject.com.genesisproject.models.Repo;

/**
 * Created by nsmirosh on 6/17/17.
 */

public class MainActivityMvcImpl implements MainActivityMvc, RepoAdapter.onInteractionListener {

    InteractionListener mListener;
    View mRootView;
    RepoAdapter mRepoAdapter;

    @BindView(R.id.searchView)
    SearchView mSearchView;

    @BindView(R.id.result_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public MainActivityMvcImpl(LayoutInflater inflater, Context context) {
        mRootView = inflater.inflate(R.layout.activity_main, null, false);
        ButterKnife.bind(this, mRootView);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressBar();
                mListener.onQueryTyped(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(() -> {
//            mListener.onSearchClosed();
            hideProgressBar();
            return false;
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //не инжектю, т.к. не получилось заинжектить MainActivityImpl
        mRepoAdapter = new RepoAdapter();
        mRepoAdapter.setListener(this);
        mRecyclerView.setAdapter(mRepoAdapter);
    }


    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


    @OnClick(R.id.show_prev_results_btn)
    public void onShowPreviousResultsClicked() {
        mListener.showPreviousResults();
    }


    @OnClick(R.id.cancel_request_btn)
    public void onCancelRequest() {
        hideProgressBar();
        mListener.onSearchClosed();
    }


    @Override
    public void bindData(List<Repo> repoList) {
        mRepoAdapter.setData(repoList);
    }


    @Override
    public void onRepoClicked(Repo repo) {
        mListener.onRepoClicked(repo);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setListener(InteractionListener listener) {
        mListener = listener;
    }


}
