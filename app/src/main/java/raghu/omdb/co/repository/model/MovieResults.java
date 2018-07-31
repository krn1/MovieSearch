package raghu.omdb.co.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *  Search response from the backend
 */
public class MovieResults implements Parcelable {
    @SerializedName("Search")
    private List<MovieInfo> results;
    @SerializedName("Error")
    private String error;

    protected MovieResults(Parcel in) {
        results = in.createTypedArrayList(MovieInfo.CREATOR);
        error = in.readString();
    }

    public static final Creator<MovieResults> CREATOR = new Creator<MovieResults>() {
        @Override
        public MovieResults createFromParcel(Parcel in) {
            return new MovieResults(in);
        }

        @Override
        public MovieResults[] newArray(int size) {
            return new MovieResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
        dest.writeString(error);
    }

    public List<MovieInfo> getResults() {
        return results;
    }

    public void setResults(List<MovieInfo> results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}