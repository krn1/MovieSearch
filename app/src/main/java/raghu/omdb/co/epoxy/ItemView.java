package raghu.omdb.co.epoxy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;

import com.airbnb.epoxy.ModelProp;
import com.airbnb.epoxy.ModelView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import raghu.omdb.co.R;
import raghu.omdb.co.repository.model.MovieInfo;
import timber.log.Timber;

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ItemView extends FrameLayout {


    @BindView(R.id.root_view)
    View root;

    @BindView(R.id.thumbnail)
    SimpleDraweeView posterView;

    @BindView(R.id.movie_title)
    AppCompatTextView titleView;

    @BindView(R.id.year)
    AppCompatTextView yearView;

    private MovieInfo movieInfo;

    public ItemView(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.movie_list_item, this);
        ButterKnife.bind(this);

        root.setOnClickListener(listener -> startDetailsPage());
    }

    @ModelProp
    public void setContent(@NonNull MovieInfo movieInfo) {
        this.movieInfo = movieInfo;

        posterView.setImageURI(movieInfo.getPoster());
        titleView.setText(movieInfo.getTitle());
        yearView.setText(movieInfo.getYear());
    }

    // region private
    private void startDetailsPage() {
        Timber.e("Starting Details page ");
        //MovieDetailsActivity.start((Activity) getContext(),movieInfo);
    }
    //endregion private
}
