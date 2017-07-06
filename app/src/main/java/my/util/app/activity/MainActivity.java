package my.util.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.fragments.ComplaintsFragment;
import my.util.app.utils.Constants;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupBottomBar();
        updateFragment(Constants.FRAGMENTS.BILLS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            List<Fragment> allFragments = getSupportFragmentManager().getFragments();
            if (allFragments != null && !allFragments.isEmpty()) {
                for (Fragment fragment : allFragments) {
                    if (fragment instanceof ComplaintsFragment) {
                        Drawable image = new BitmapDrawable(getResources(), photo);
                        ((ComplaintsFragment) fragment).updateImages(image);
                    }
                }
            }
        }
    }
}
