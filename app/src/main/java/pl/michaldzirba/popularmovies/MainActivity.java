package pl.michaldzirba.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import pl.michaldzirba.popularmovies.data.Movie;
import pl.michaldzirba.popularmovies.data.MovieDataProvider;
import pl.michaldzirba.popularmovies.data.MovieRequestQueue;

/**
 * In this stage you’ll build the core experience of your movies app.
 * <p>
 * Your app will:
 * <p>
 * Upon launch, present the user with an grid arrangement of movie posters.
 * Allow your user to change sort order via a setting:
 * The sort order can be by most popular, or by top rated
 * Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
 * original title
 * movie poster image thumbnail
 * A plot synopsis (called overview in the api)
 * user rating (called vote_average in the api)
 * release date
 */
public class MainActivity extends Activity implements MovieDataAdapter.IMovieClickListener {
    protected final static String url_movies_popular = "/movie/popular";
    protected final static String url_movies_toprated = "/movie/top_rated";

    protected String appurl_ = url_movies_popular;
    protected String baseurl_ = "http://api.themoviedb.org/3";
    protected String apikey_ = null;

    protected RecyclerView recyclerView_;
    protected MovieDataAdapter adapter_;
    protected RecyclerView.LayoutManager layoutManager_;
    protected MovieDataProvider movieDataProvider_;
    protected RequestQueue requestQueue_;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (apikey_ == null) initApi(); // has to be first, call below will use the api
        init();
    }

    protected void initApi() {
        final XmlPullParser xpp = getResources().getXml(R.xml.keys);
        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("key")) {
                        apikey_ = xpp.getAttributeValue(0);
                        break;
                    }
                }
                xpp.next();
            }
        } catch (final XmlPullParserException | IOException e) {
            Log.e("API", e.getMessage());
        }
    }

    /**
     * initialize fields
     */
    protected void init() {
        requestQueue_ = MovieRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        movieDataProvider_ = new MovieDataProvider();
        recyclerView_ = findViewById(R.id.rv_movies);
        recyclerView_.setHasFixedSize(false);
        layoutManager_ = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView_.setLayoutManager(layoutManager_);

        adapter_ = new MovieDataAdapter(movieDataProvider_, this);
        recyclerView_.setAdapter(adapter_);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_highly_rated:
                appurl_ = url_movies_toprated;
                return refresh();
            case R.id.menu_popular:
                appurl_ = url_movies_popular;
                return refresh();

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * reload data, refresh view
     */
    protected boolean refresh() {
        requestQueue_.add(new JsonObjectRequest
                (Request.Method.GET, getUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        movieDataProvider_.refresh(response);
                        MainActivity.this.animation(recyclerView_);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(final VolleyError error) {
//                        Snackbar.
//                        or activity error, with "retry" calling this back

                        Toast.makeText(MainActivity.this, "No internet connection. (" + error.getMessage() + ")", Toast.LENGTH_LONG).show();
                    }
                }
                )).setTag(MovieRequestQueue.VOLLEY_QUEUE_TAG);
        return true;
    }

    protected void animation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onClick(final Movie argMovie) {
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_PARAMETER, argMovie);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MovieRequestQueue.getInstance(this).close();
    }

    public String getUrl() {
        return baseurl_ + appurl_ + "?api_key=" + apikey_;
    }
}
