package my.util.app.adapter;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.models.BillDetails;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

public class AddressExpandableListAdapter extends BaseExpandableListAdapter{

    static Context ctx;
    List<String> titles;
    HashMap<String, List<BillDetails>> allBills;

    public AddressExpandableListAdapter(Context ctx, List<String> titles, HashMap<String, List<BillDetails>> allBills) {
        this.ctx = ctx;
        this.titles = titles;
        this.allBills = allBills;
        Log.d("DEBUG_LOG", "add titles " + titles.size());
        Log.d("DEBUG_LOG", "add allBills " + allBills.size());
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
        holder.currentBill = currentItem.get(currentItem.size() - 1); // RE-CHECK

        int type = holder.currentBill.getBillType();
        holder.billTypeIcon.setImageResource(type == 1 ? R.drawable.bulb_icon : R.drawable.gas_icon);
        holder.billType.setText(type == 1 ? "Elctricity Services" : "Gas Services");
        holder.complaintDate.setText(holder.currentBill.getBillingDate());
        holder.consumption.setText(holder.currentBill.getConsumption() + (type == 1 ? " Kwh" : " Thm"));
        holder.amount.setText("$" + holder.currentBill.getAmount());

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
        BillDetails currentBill;

        @BindView(R.id.bill_type_icon)
        ImageView billTypeIcon;
        @BindView(R.id.bill_type)
        TextView billType;
        @BindView(R.id.complaint_date)
        TextView complaintDate;
        @BindView(R.id.consumption)
        TextView consumption;
        @BindView(R.id.amount)
        TextView amount;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.bill_details)
        protected void showBillDetails(View v){
            DataManager.getInstance(ctx).setmCurrentBill(currentBill);
            ((BaseActivity)ctx).updateFragment(Constants.FRAGMENTS.BILL_DETAILS);
        }
    }
}
