package genesisproject.com.genesisproject.utils;


import java.io.IOException;

import genesisproject.com.genesisproject.http.exceptions.NoNetworkException;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class ServerErrorHandlerUtil {

    public interface OnErrorListener{
        void onHttpError(int responseCode);
        void onNoNetworkException();
    }

    private OnErrorListener listener;

    public ServerErrorHandlerUtil() {
    }

    public void setListener(OnErrorListener listener) {
        this.listener = listener;
    }

    public String checkCause(Throwable throwable) {
        String errorMessage = "";
        // We had non-200 http error
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            listener.onHttpError(response.code());
            errorMessage = response.message();
        } else if (throwable instanceof IOException) {
            // A network error happened
            errorMessage = throwable.getMessage();
        } else if (throwable instanceof NoNetworkException) {
            listener.onNoNetworkException();
        } else {
            //unknown error
            errorMessage = throwable.getMessage();
        }
        return errorMessage;
    }

}
