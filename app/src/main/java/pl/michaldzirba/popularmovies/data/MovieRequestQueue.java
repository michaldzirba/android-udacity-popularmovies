package pl.michaldzirba.popularmovies.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * volley request queue (singleton, as advices by https://developer.android.com/training/volley/requestqueue.html)
 */
public class MovieRequestQueue {
    public static final String VOLLEY_QUEUE_TAG = "MovieRequest";
    protected static MovieRequestQueue instance_;

    protected RequestQueue requestQueue_;
    protected Context ctx_;

    private MovieRequestQueue(final Context context) {
        ctx_ = context;
        requestQueue_ = getRequestQueue();
    }

    public static synchronized MovieRequestQueue getInstance(Context context) {
        if (instance_ == null) {
            instance_ = new MovieRequestQueue(context);
        }
        return instance_;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue_ == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue_ = Volley.newRequestQueue(ctx_.getApplicationContext());
        }
        return requestQueue_;
    }

    public void clear() {
        if (requestQueue_ != null) {
            requestQueue_.cancelAll(VOLLEY_QUEUE_TAG);
            requestQueue_.getCache().clear();
        }
    }
}