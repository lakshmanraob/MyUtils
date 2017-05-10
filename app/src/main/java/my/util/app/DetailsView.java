package my.util.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsView extends LinearLayout {


    TextView mHeadingView;
    TextView mContentView;

    public DetailsView(Context context) {
        super(context);
    }

    public DetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ar = context.obtainStyledAttributes(attrs, R.styleable.DetailsView, 0, 0);

        String heading = ar.getString(R.styleable.DetailsView_heading);
        String content = ar.getString(R.styleable.DetailsView_content);

        ar.recycle();

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflatedView = inflater.inflate(R.layout.view_details_item, this);

        mHeadingView = (TextView) inflatedView.findViewById(R.id.details_heading);
        mContentView = (TextView) inflatedView.findViewById(R.id.deatils_content);

        mHeadingView.setText(heading);
        mContentView.setText(content);

    }

    public void setHeding(String heading) {
        mHeadingView.setText(heading);
    }

    public void setContent(String content) {
        mContentView.setText(content);
    }

}
