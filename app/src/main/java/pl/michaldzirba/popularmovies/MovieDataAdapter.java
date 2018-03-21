package pl.michaldzirba.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pl.michaldzirba.popularmovies.data.Movie;
import pl.michaldzirba.popularmovies.data.MovieDataProvider;

import static pl.michaldzirba.popularmovies.data.Movie.Size.w185;

/**
 * Adapter for the movie / poster view
 * Created by Michal on 3/20/2018.
 */
public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MovieAdapterViewHolder> {
    protected final MovieDataProvider dataProvider_;
    protected IMovieClickListener listener_;
    protected int lastPosition = -1;

    protected interface IMovieClickListener {
        public void onClick(final Movie argMovie);
    }

    protected MovieDataAdapter(final MovieDataProvider argDataProvider, final IMovieClickListener argListener) {
        this.dataProvider_ = argDataProvider;
        this.listener_ = argListener;
    }

    @Override
    public MovieDataAdapter.MovieAdapterViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieAdapterViewHolder(view, listener_);
    }

    @Override
    public void onBindViewHolder(final MovieDataAdapter.MovieAdapterViewHolder argHolder, final int argPosition) {
        argHolder.update(dataProvider_.getMovie(argPosition));
    }

    @Override
    public int getItemCount() {
        return dataProvider_.getItemCount();
    }

    protected class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected final ImageView mPoster;
        protected final View mParentView;
        protected final IMovieClickListener mOnClickListener_;
        protected Movie movie_;

        public MovieAdapterViewHolder(final View argView, final IMovieClickListener argListener) {
            super(argView);
            mPoster = argView.findViewById(R.id.iv_movie_poster);
            mParentView = argView;
            mOnClickListener_ = argListener;
            mParentView.setOnClickListener(this);
        }

        public void update(final Movie argMovie) {
            this.movie_ = argMovie;
            Picasso.with(mPoster.getContext()).load(argMovie.poster(w185)).into(mPoster);
        }

        @Override
        public void onClick(final View argView) {
            mOnClickListener_.onClick(movie_);
        }
    }
}



