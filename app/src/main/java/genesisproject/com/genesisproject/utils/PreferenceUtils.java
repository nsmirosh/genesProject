package genesisproject.com.genesisproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import genesisproject.com.genesisproject.models.Repo;

/**
 * Created by nsmirosh on 6/17/17.
 */

public class PreferenceUtils {

    Gson mGson;
    Context mContext;

    private SharedPreferences mPrefs;

    public static final String PREFERENCES_KEY = "myPrefsKey";

    public static final String STRING_LIST_PREVIOUS_RESULT_KEY = "string_list_previous_result_key";

    @Inject
    public PreferenceUtils(Context context, Gson gson) {
        this.mPrefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        this.mGson = gson;
        this.mContext = context;
    }

    public void clearPreferences() {
        mPrefs.edit().clear().apply();
    }

    public void saveResults(List resultList) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(STRING_LIST_PREVIOUS_RESULT_KEY, mGson.toJson(resultList));
        editor.apply();
    }


    public List<Repo> getPreviousResults() {
        List<Repo> prevResults = null;
        if (mPrefs.contains(STRING_LIST_PREVIOUS_RESULT_KEY)) {
            Type type = new TypeToken<List<Repo>>() {
            }.getType();
            prevResults =  mGson.fromJson(mPrefs.getString(STRING_LIST_PREVIOUS_RESULT_KEY, null), type);
        }
        return prevResults;
    }
}
