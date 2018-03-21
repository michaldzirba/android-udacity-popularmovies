package pl.michaldzirba.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.michaldzirba.popularmovies.data.Movie;

import static pl.michaldzirba.popularmovies.data.Movie.Size.w185;

/**
 * show movie details, movie is passed via intent
 */
public class DetailActivity extends AppCompatActivity {

    protected Movie movie_;
    protected TextView title_;
    protected ImageView posterDetail_;
    protected TextView releaseDate_;
    protected RatingBar rating_;
    protected TextView plot_;

    @Override
    protected void onCreate(final Bundle argSavedInstanceState) {
        super.onCreate(argSavedInstanceState);
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
        this.rating_.setRating((float) argMovie.rating / 2); // web rating is < 10, we have 5 stars
        this.plot_.setText(argMovie.plot);

        Picasso.with(this).load(argMovie.poster(w185)).into(this.posterDetail_);
    }

    protected void init() {
        title_ = findViewById(R.id.tv_title);
        posterDetail_ = findViewById(R.id.iv_movie_poster_detail);
        releaseDate_ = findViewById(R.id.tv_releaseDate);
        rating_ = findViewById(R.id.tv_rating);
        rating_.setIsIndicator(true); // does not allow changes 

        plot_ = findViewById(R.id.tv_plot);
    }

    public static final String INTENT_PARAMETER = "movie";
}
