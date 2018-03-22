package pl.michaldzirba.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.michaldzirba.popularmovies.data.Movie;

/**
 * show movie details, movie is passed via intent
 */
public class DetailActivity extends AppCompatActivity {

    protected Movie movie_;

    @BindView(R.id.tv_title)
    protected TextView title_;

    @BindView(R.id.iv_movie_poster_detail)
    protected ImageView posterDetail_;

    @BindView(R.id.tv_releaseDate)
    protected TextView releaseDate_;

    @BindView(R.id.tv_rating)
    protected RatingBar rating_;
    
    @BindView(R.id.tv_plot)
    protected TextView plot_;


    @Override
    protected void onCreate(final Bundle argSavedInstanceState) {
        super.onCreate(argSavedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        final Intent intent = getIntent();
        if (intent.hasExtra(INTENT_PARAMETER)) {
            this.movie_ = intent.getParcelableExtra(INTENT_PARAMETER);
        }

        rating_.setIsIndicator(true); // does not allow changes
        refresh(this.movie_);
    }

    protected void refresh(final Movie argMovie) {
        this.title_.setText(argMovie.title);
        this.releaseDate_.setText(argMovie.releaseDate);
        this.rating_.setRating((float) argMovie.rating / 2); // web rating is < 10, we have 5 stars
        this.plot_.setText(argMovie.plot);

        Picasso.with(this).load(argMovie.poster(Movie.w185))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_missing_poster)
                .into(this.posterDetail_);
    }

    public static final String INTENT_PARAMETER = "movie";
}
