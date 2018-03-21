package pl.michaldzirba.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import pl.michaldzirba.popularmovies.data.Movie;
import pl.michaldzirba.popularmovies.data.MovieDataProvider;

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

    protected String appurl = url_movies_popular; // replace with preferences?

    protected RecyclerView recyclerView_;
    protected MovieDataAdapter adapter_;
    protected RecyclerView.LayoutManager layoutManager_;
    protected MovieDataProvider movieDataProvider_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * initialize fields
     */
    private void init() {
        movieDataProvider_ = new MovieDataProvider(appurl);
        recyclerView_ = (RecyclerView) findViewById(R.id.rv_movies);
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
                appurl = url_movies_toprated;
                return refresh();
            case R.id.menu_popular:
                appurl = url_movies_popular;
                return refresh();

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * reload data, refresh view
     */
    protected boolean refresh() {
        movieDataProvider_.setAppUrl(appurl);
        animation(recyclerView_);
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
}
