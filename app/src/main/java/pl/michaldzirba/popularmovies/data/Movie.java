package pl.michaldzirba.popularmovies.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * data class for movie information
 * <p>
 * Created by Michal on 3/20/2018.
 */
public class Movie implements Parcelable {
    public enum Size {w92, w154, w185, w342, w500, w780, original}

    public int id;
    public String title;
    public String releaseDate; // ????
    public String rating; // ????
    public String plot;
    public String length;

    protected String url;

    public Movie() {
    }

    public Uri poster(final Size argSize) {
        return Uri.parse(baseurl + argSize + url);
    }

    // "De-parcel object
    public Movie(final Parcel argIn) {
        id = argIn.readInt();
        title = argIn.readString();
        url = argIn.readString();
        releaseDate = argIn.readString();
        rating = argIn.readString();
        plot = argIn.readString();
        length = argIn.readString();
    }

    @Override
    public void writeToParcel(final Parcel argDest, final int argFlags) {
        argDest.writeInt(id);
        argDest.writeString(title);
        argDest.writeString(url);
        argDest.writeString(releaseDate);
        argDest.writeString(rating);
        argDest.writeString(plot);

        argDest.writeString(length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(final Parcel argIn) {
            return new Movie(argIn);
        }

        public Movie[] newArray(final int argSize) {
            return new Movie[argSize];
        }
    };
    protected static final String baseurl = "http://image.tmdb.org/t/p/";
}
