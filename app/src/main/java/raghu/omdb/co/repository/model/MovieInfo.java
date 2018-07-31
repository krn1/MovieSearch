package raghu.omdb.co.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MovieInfo implements Parcelable {
   @SerializedName("Title")
   private String title;
   @SerializedName("Year")
   private String year;
   @SerializedName("imdbID")
   private String imdbId;
   @SerializedName("Type")
   private String type;
   @SerializedName("Poster")
   private String poster;

    protected MovieInfo(Parcel in) {
        title = in.readString();
        year = in.readString();
        imdbId = in.readString();
        type = in.readString();
        poster = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(imdbId);
        dest.writeString(type);
        dest.writeString(poster);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieInfo that = (MovieInfo) o;
        return Objects.equals(((MovieInfo) o).imdbId, that.imdbId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbId);
    }
}
