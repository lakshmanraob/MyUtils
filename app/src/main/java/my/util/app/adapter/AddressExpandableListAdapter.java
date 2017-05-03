package my.util.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.utils.BillDetails;
import my.util.app.utils.Utils;

public class AddressExpandableListAdapter extends BaseExpandableListAdapter{

    Context ctx;
    List<String> titles;
    HashMap<String, List<BillDetails>> allBills;

    public AddressExpandableListAdapter(Context ctx, List<String> titles, HashMap<String, List<BillDetails>> allBills) {
        this.ctx = ctx;
        this.titles = titles;
        this.allBills = allBills;
    }

    @Override
    public int getGroupCount() {
        return titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return allBills.get(titles.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return titles.get(groupPosition);
    }

    @Override
    public List<BillDetails> getChild(int groupPosition, int childPosition) {
        return allBills.get(titles.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView != null) {
            holder = (GroupViewHolder) convertView.getTag();
        } else {
            convertView = ((LayoutInflater)ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.address_list_parent_item, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.addressLabel.setText(getGroup(groupPosition));
        holder.expandedIndicator.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.collapsedIndicator.setVisibility(isExpanded ? View.GONE : View.VISIBLE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView != null) {
            holder = (ChildViewHolder) convertView.getTag();
        } else {
            convertView = ((LayoutInflater)ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.address_list_child_item, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }
        List<BillDetails> currentItem = getChild(groupPosition, childPosition);
        holder.bills = currentItem;

        Utils.showShortToast(ctx, "> "+ currentItem.size());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.text_address_parent)
        TextView addressLabel;
        @BindView(R.id.expanded_indicator)
        IconTextView expandedIndicator;
        @BindView(R.id.collapsed_indicator)
        IconTextView collapsedIndicator;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder{
        List<BillDetails> bills;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
