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
import my.util.app.DetailsView;
import my.util.app.R;
import my.util.app.models.BillDetails;

/**
 * Created by labattula on 05/05/17.
 */

public class UserBilldetailsAdapter extends BaseExpandableListAdapter {

    Context mContext;
    List<String> mGroupTitles;
    HashMap<String, List<BillDetails>> allBillDetails;


    public UserBilldetailsAdapter(Context context, List<String> groupTitle, HashMap<String, List<BillDetails>> allBills) {
        this.mContext = context;
        this.mGroupTitles = groupTitle;
        this.allBillDetails = allBills;
    }


    @Override
    public int getGroupCount() {
        return mGroupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return allBillDetails.get(mGroupTitles.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return mGroupTitles.get(groupPosition);
    }

    @Override
    public List<BillDetails> getChild(int groupPosition, int childPosition) {
        return allBillDetails.get(mGroupTitles.get(groupPosition));
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
        UserBilldetailsAdapter.GroupViewHolder holder;
        if (convertView != null) {
            holder = (UserBilldetailsAdapter.GroupViewHolder) convertView.getTag();
        } else {
            convertView = ((LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ud_bill_address, parent, false);
            holder = new UserBilldetailsAdapter.GroupViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.detailsHeading.setText("Service Address 1");
        holder.detailsContent.setText(getGroup(groupPosition));
        holder.expandedIndicator.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.collapsedIndicator.setVisibility(isExpanded ? View.GONE : View.VISIBLE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        UserBilldetailsAdapter.ChildViewHolder holder;
        if (convertView != null) {
            holder = (UserBilldetailsAdapter.ChildViewHolder) convertView.getTag();
        } else {
            convertView = ((LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ud_bill_child_item, parent, false);
            holder = new UserBilldetailsAdapter.ChildViewHolder(convertView);
            convertView.setTag(holder);
        }
        List<BillDetails> currentItem = getChild(groupPosition, childPosition);
        holder.bills = currentItem;
        holder.serviceHeading.setText(holder.bills.get(childPosition).getProvider());
        holder.avgDayConsumption.setContent(holder.bills.get(childPosition).getConsumption());
        holder.avgMonthConsumption.setContent(holder.bills.get(childPosition).getConsumptionAverage());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.details_heading)
        TextView detailsHeading;
        @BindView(R.id.deatils_content)
        TextView detailsContent;

        @BindView(R.id.expanded_indicator)
        IconTextView expandedIndicator;
        @BindView(R.id.collapsed_indicator)
        IconTextView collapsedIndicator;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        List<BillDetails> bills;

        @BindView(R.id.service_heading)
        TextView serviceHeading;

        @BindView(R.id.avg_day_consumption)
        DetailsView avgDayConsumption;

        @BindView(R.id.avg_month_consumption)
        DetailsView avgMonthConsumption;


        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
