package my.util.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import my.util.app.R;
import sheet.bottom.com.networklib.models.stackexchange.StackItem;

public class BillItemsAdapter extends RecyclerView.Adapter<BillItemsAdapter.StackViewHolder> {

    private List<StackItem> stackItemList = null;
    private Context mContext;

    public BillItemsAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public StackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stack_item_layout, parent, false);

        return new StackViewHolder(itemView);
    }

    public void setStackItemList(List<StackItem> stackItems){
        this.stackItemList = stackItems;
    }

    @Override
    public void onBindViewHolder(StackViewHolder holder, int position) {
        StackItem stackItem = stackItemList.get(position);
        holder.title.setText(stackItem.getTitle());
        holder.tags.setText(tagsCombiner(stackItem.getTags()));
        holder.profileName.setText(stackItem.getOwner().getDisplay_name());
        //imageView for displaying the profile image
        Picasso.with(mContext)
                .load(stackItem.getOwner().getProfile_image())
                .error(R.drawable.ic_fav_normal_24dp)
                .placeholder(R.drawable.ic_place_normal_24dp)
                .resize(150,150)
                .into(holder.profileImage);

    }

    private String tagsCombiner(String[] tags){
        StringBuilder sb = new StringBuilder();
        for (String st:tags){
            sb.append(st).append(",");
        }
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return stackItemList.size();
    }

    public class StackViewHolder extends RecyclerView.ViewHolder {

        TextView profileName, title, tags;
        ImageView profileImage;

        public StackViewHolder(View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            tags = (TextView) itemView.findViewById(R.id.tags);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
