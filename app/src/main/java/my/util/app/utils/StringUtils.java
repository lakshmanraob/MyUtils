package my.util.app.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import my.util.app.R;

public class StringUtils {

    public static SpannableStringBuilder getSpannableHeading(Context context) {
        final SpannableStringBuilder sb = new SpannableStringBuilder(context.getString(R.string.util_txt));
        sb.setSpan(new TextAppearanceSpan(context, R.style.SpanTextAppearance), 1, 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}
