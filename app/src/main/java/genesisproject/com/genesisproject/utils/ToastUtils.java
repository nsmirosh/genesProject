package genesisproject.com.genesisproject.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import javax.inject.Inject;

public class ToastUtils {

  Context mContext;
  Resources mResources;


  @Inject
  public ToastUtils(Context context, Resources resources) {
    mContext = context;
    mResources = resources;

  }

  public void showToast(String text) {
    Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
  }

  public void showToast(String text, int toastLength) {
    Toast.makeText(mContext, text, toastLength).show();
  }

  public void showToast(int id) {
    showToast(id, false);
  }

  public void showToast(int id, boolean longToast) {
    Toast.makeText(mContext, id, longToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
  }

  public void showToast(int id, int length) {
    Toast.makeText(mContext, id, length).show();
  }

  public void showFormattedToast(int id, Object... args) {
    Toast.makeText(mContext, String.format(mContext.getText(id).toString(), args),
        Toast.LENGTH_LONG).show();
  }
}
