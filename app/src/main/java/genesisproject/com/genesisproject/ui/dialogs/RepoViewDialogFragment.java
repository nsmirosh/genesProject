package genesisproject.com.genesisproject.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by nick on 09.11.16.
 */

public class RepoViewDialogFragment extends DialogFragment {

  public static final String FRAGMENT_TAG = RepoViewDialogFragment.class.getSimpleName();

  public static final String ARGS_REPO_URL = "args_repo_url";

  String repoUrl;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    WebView webview = new WebView(getActivity());
    webview.getSettings().setJavaScriptEnabled(true);

    if (getArguments() != null) {
      repoUrl = getArguments().getString(ARGS_REPO_URL);
      webview.loadUrl(repoUrl);
    }
    return webview;
  }


  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(ARGS_REPO_URL, repoUrl);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState != null) {
      repoUrl = savedInstanceState.getString(ARGS_REPO_URL);
    }
  }

}
