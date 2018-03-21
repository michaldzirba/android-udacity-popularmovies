package pl.michaldzirba.popularmovies.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Michal on 3/21/2018.
 * measure to fill one full column, adjusting height
 */
public class OneColumnImageView extends ImageView {

    public OneColumnImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int argWidthMeasureSpec, int argHeightMeasureSpec) {
        final Drawable d = getDrawable();

        if (d != null) {
            int width = MeasureSpec.getSize(argWidthMeasureSpec);
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(argWidthMeasureSpec, argHeightMeasureSpec);
        }
    }
}
