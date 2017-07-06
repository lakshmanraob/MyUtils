package my.util.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OutageView extends LinearLayout {


    ImageView mOutageIcon;
    TextView mOutgaeTextView;
    boolean select;

    public OutageView(Context context) {
        super(context);
    }

    public OutageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ar = context.obtainStyledAttributes(attrs, R.styleable.OutageView, 0, 0);

        int drawableResourceId = ar.getResourceId(R.styleable.OutageView_selectedStateDrawable, R.color.outage_select);
        int colorResourceId = ar.getResourceId(R.styleable.OutageView_selectedStateTextColor, R.color.outage_select);
        select = ar.getBoolean(R.styleable.OutageView_select, false);
        String outageString = ar.getString(R.styleable.OutageView_outageString);

        ar.recycle();

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflatedView = inflater.inflate(R.layout.view_outage_item, this);

        mOutageIcon = (ImageView) inflatedView.findViewById(R.id.outage_icon);
        mOutgaeTextView = (TextView) inflatedView.findViewById(R.id.outage_type);

        mOutageIcon.setImageResource(drawableResourceId);
        mOutgaeTextView.setTextColor(colorResourceId);
        mOutgaeTextView.setText(outageString);

        setSelected(select);

    }

    public void setSelected(boolean selected) {
        select = selected;
        mOutageIcon.setSelected(selected);
        mOutgaeTextView.setSelected(selected);
    }

    public boolean isSelected() {
        return select;
    }

}
