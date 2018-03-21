package pl.michaldzirba.popularmovies.data;

/**
 * data on movies
 * Created by Michal on 3/20/2018.
 */
public class MovieDataProvider {
    protected String appUrl_;

    public MovieDataProvider(final String argUrl) {
        this.appUrl_ = argUrl;
    }

    public Movie getMovie(final int argPosition) {
        return getTestMovie();
    }

    public int getItemCount() {
        return 10;
    }

    public void setAppUrl(final String argAppUrl) {
        appUrl_ = argAppUrl;
        refreshdata();
    }

    protected void refreshdata() {
    }

    protected static Movie getTestMovie() {
        Movie m = new Movie();

        m.plot = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.";
        m.title = "Fight Club";
        m.url = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        m.releaseDate = "1999-10-15";
        m.length = "139 minutes";
        m.id = 500;
        m.rating = "8.3 / 10";
        return m;
    }
}
