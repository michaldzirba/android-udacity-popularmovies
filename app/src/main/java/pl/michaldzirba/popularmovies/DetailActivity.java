package pl.michaldzirba.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.michaldzirba.popularmovies.data.Movie;

import static pl.michaldzirba.popularmovies.data.Movie.Size.w185;

/**
 * show movie details
 */
public class DetailActivity extends Activity {

    protected Movie movie_;
    protected TextView title_;
    protected ImageView posterDetail_;
    protected TextView releaseDate_;
    protected TextView rating_;
    protected TextView plot_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent.hasExtra(INTENT_PARAMETER)) {
            this.movie_ = intent.getParcelableExtra(INTENT_PARAMETER);
        }

        init();
        refresh(this.movie_);
    }

    protected void refresh(final Movie argMovie) {
        this.title_.setText(argMovie.title);
        this.releaseDate_.setText(argMovie.releaseDate);
        this.rating_.setText(""+argMovie.rating);
        this.plot_.setText(argMovie.plot);

        Picasso.with(this).load(argMovie.poster(w185)).into(this.posterDetail_);
    }

    protected void init() {
        title_ = findViewById(R.id.tv_title);
        posterDetail_ = findViewById(R.id.iv_movie_poster_detail);
        releaseDate_ = findViewById(R.id.tv_releaseDate);
        rating_ = findViewById(R.id.tv_rating);
        plot_ = findViewById(R.id.tv_plot);
    }

    public static final String INTENT_PARAMETER = "movie";
}
