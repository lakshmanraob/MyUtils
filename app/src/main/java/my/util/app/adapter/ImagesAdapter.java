package my.util.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import my.util.app.R;
import my.util.app.utils.Constants;
import my.util.app.utils.ImageCaptureListener;
import my.util.app.utils.PhotoDetails;


public class ImagesAdapter extends BaseAdapter{

    private Context ctx;
    private ArrayList<PhotoDetails> images;
    private static ImageCaptureListener clickListener;

    public ImagesAdapter(Context context, ArrayList<PhotoDetails> imageList, ImageCaptureListener imageCaptureListener) {
        this.ctx = context;
        this.images = imageList;
        clickListener = imageCaptureListener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public PhotoDetails getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = ((LayoutInflater)ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.image_grid_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PhotoDetails currentItem = getItem(position);
        holder.item = currentItem;
        holder.image.setImageDrawable(currentItem.getImage());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView image;
        @BindView(R.id.imageViewOverlay)
        ImageView imageOverlay;
        @BindView(R.id.imageViewOverlayCross)
        IconTextView crossIcon;
        PhotoDetails item;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView)
        protected void onImageClick(){
            clickListener.onClick(item);
        }

        @OnLongClick(R.id.imageView)
        protected boolean onImageLongClick(){
            imageOverlay.setVisibility(View.VISIBLE);
            crossIcon.setVisibility(View.VISIBLE);
            return false;
        }

        @OnClick(R.id.imageViewOverlay)
        protected void onDeleteClick(){
            clickListener.onLongClick(item);
            imageOverlay.setVisibility(View.GONE);
            crossIcon.setVisibility(View.GONE);
        }
    }
}
