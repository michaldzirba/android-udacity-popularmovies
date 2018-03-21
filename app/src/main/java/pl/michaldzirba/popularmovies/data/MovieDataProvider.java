package pl.michaldzirba.popularmovies.data;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * data on movies
 * Created by Michal on 3/20/2018.
 */
public class MovieDataProvider {
    protected Movie[] movies_ = new Movie[0];
    public Movie getMovie(final int argPosition) {
        return movies_[argPosition];
    }
    public int getItemCount() {
        return movies_.length;
    }
    public void refresh(final JSONObject argResponse) {

        try {
            final JSONArray results = argResponse.getJSONArray("results");
            movies_ = new Movie[results.length()];
            for(int i = 0; i < results.length(); i++){
                movies_[i] = new Movie(results.getJSONObject(i));
            }

        } catch (JSONException e) {
            Log.e("json/parsing", Log.getStackTraceString(e));
        }
    }
}
