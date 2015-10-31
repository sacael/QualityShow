package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/30/15.
 */
public class SerieCallbacks {

    public static class GetSeriesCallback extends Adapter.JsonArrayCallback{

        private SerieListener listener;

        public GetSeriesCallback(SerieListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON array or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON array.
         */
        @Override
        public void onSuccess(JSONArray response) {
            ArrayList<Serie> series = new ArrayList<>();
            SerieRepository repository = new SerieRepository();
            for (int i = 0; i < response.length(); i++){
                JSONObject object = null;
                try {
                    object  = response.getJSONObject(i);

                    Serie serie = object != null
                            ? repository.createObject(JsonUtil.fromJson(object))
                            : null;
                    if (serie != null)
                        series.add(serie);
                }catch (JSONException e){
                    Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + GetSeriesCallback.class.getSimpleName(), e);
                }
                Log.d(Constants.Log.TAG, "Series received");
                this.listener.getSeries(series);
            }
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + GetSeriesCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class AddSerieCallback extends Adapter.JsonObjectCallback{

        private SerieListener listener;

        public AddSerieCallback(SerieListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON object.
         */
        @Override
        public void onSuccess(JSONObject response) {
            Log.d(Constants.Log.TAG, "Serie added");
            SerieRepository repository = new SerieRepository();
            Serie serie = response !=  null
                    ? repository.createObject(JsonUtil.fromJson(response))
                    : null;
            this.listener.serieIsAdded(serie);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class DeleteSerieCallback implements Adapter.Callback{

        private SerieListener listener;

        public DeleteSerieCallback(SerieListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully.
         *
         * @param response The HTTP response body.
         */
        @Override
        public void onSuccess(String response) {
            Log.d(Constants.Log.TAG, "Serie deleted");
            this.listener.serieIsDeleted();
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

}
