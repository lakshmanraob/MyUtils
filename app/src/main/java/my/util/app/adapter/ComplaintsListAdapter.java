package my.util.app.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.utils.Constants;
import my.util.app.models.IssueDetails;
import my.util.app.utils.Utils;

public class ComplaintsListAdapter extends RecyclerView.Adapter<ComplaintsListAdapter.ViewHolder> {

    private ArrayList<IssueDetails> complaints;
    private boolean weekLabelDisplayed;
    private boolean monthLabelDisplayed;
    private boolean previousLabelDisplayed;

    public ComplaintsListAdapter(ArrayList<IssueDetails> complaints) {
        this.complaints = complaints;
    }

    public void updateList(ArrayList<IssueDetails> complaintsList){
        this.complaints = Utils.sortComplaintsList(complaintsList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IssueDetails issue = complaints.get(position);

        Calendar complaintDate = issue.getComplaintDate();
        holder.outageType.setText(issue.getOutageType());
        holder.complaintDate.setText(complaintDate.get(Calendar.DAY_OF_MONTH) + "/" +
                complaintDate.get(Calendar.MONTH) + "/" + complaintDate.get(Calendar.YEAR));
        holder.referenceNumber.setText(String.valueOf(issue.getReferenceNo()));
        holder.outageSubType.setText(issue.getOutageType());
        holder.complaintAddress.setText(issue.getAddress());

        int status = issue.getStatus();
        switch (status) {
            case Constants.COMPLAINT_STATUS.UNDER_REVIEW:
                showUnderReviewStatus(holder);
                break;
            case Constants.COMPLAINT_STATUS.RESOLVED:
                showResolvedStatus(holder);
                break;
            case Constants.COMPLAINT_STATUS.SUBMITTED:
            default:
                showSubmittedStatus(holder);
                break;
        }

        if ((Constants.COMPLAINTS_TIMINGS.THIS_WEEK == issue.getComplaintTiming()) &&
                (View.GONE == holder.weekLabel.getVisibility()) && !weekLabelDisplayed) {
            holder.weekLabel.setVisibility(View.VISIBLE);
            holder.monthLabel.setVisibility(View.GONE);
            holder.previousLabel.setVisibility(View.GONE);
            weekLabelDisplayed = true;
        } else if ((Constants.COMPLAINTS_TIMINGS.THIS_MONTH == issue.getComplaintTiming()) &&
                (View.GONE == holder.monthLabel.getVisibility()) && !monthLabelDisplayed) {
            holder.monthLabel.setVisibility(View.VISIBLE);
            holder.weekLabel.setVisibility(View.GONE);
            holder.previousLabel.setVisibility(View.GONE);
            monthLabelDisplayed = true;
        } else if ((Constants.COMPLAINTS_TIMINGS.PREVIOUS == issue.getComplaintTiming()) &&
                (View.GONE == holder.previousLabel.getVisibility()) && !previousLabelDisplayed) {
            holder.previousLabel.setVisibility(View.VISIBLE);
            holder.weekLabel.setVisibility(View.GONE);
            holder.monthLabel.setVisibility(View.GONE);
            previousLabelDisplayed = true;
        }

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.week_label)
        TextView weekLabel;
        @BindView(R.id.month_label)
        TextView monthLabel;
        @BindView(R.id.previous_label)
        TextView previousLabel;

        @BindView(R.id.outage_type)
        TextView outageType;
        @BindView(R.id.complaint_date)
        TextView complaintDate;
        @BindView(R.id.ref_no)
        TextView referenceNumber;
        @BindView(R.id.outage_sub_type)
        TextView outageSubType;
        @BindView(R.id.complaint_address)
        TextView complaintAddress;

        @BindView(R.id.reported_light)
        ImageView reportedLight;
        @BindView(R.id.reported_filled)
        ImageView reportedFilled;
        @BindView(R.id.reported_border)
        ImageView reportedBorder;
        @BindView(R.id.reviewed_light)
        ImageView revieweLight;
        @BindView(R.id.reviewed_filled)
        ImageView revieweFilled;
        @BindView(R.id.reviewed_border)
        ImageView revieweBorder;
        @BindView(R.id.resolved_light)
        ImageView resolvedLight;
        @BindView(R.id.resolved_filled)
        ImageView resolvedFilled;
        @BindView(R.id.resolved_border)
        ImageView resolvedBorder;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showSubmittedStatus(ViewHolder holder) {
        holder.reportedLight.setVisibility(View.VISIBLE);
        holder.reportedFilled.setVisibility(View.VISIBLE);
        holder.reportedBorder.setVisibility(View.GONE);
    }

    private void showUnderReviewStatus(ViewHolder holder) {
        holder.reportedLight.setVisibility(View.GONE);
        holder.reportedFilled.setVisibility(View.VISIBLE);
        holder.reportedBorder.setVisibility(View.GONE);
        holder.revieweLight.setVisibility(View.VISIBLE);
        holder.revieweFilled.setVisibility(View.VISIBLE);
        holder.revieweBorder.setVisibility(View.GONE);
    }

    private void showResolvedStatus(ViewHolder holder) {
        holder.reportedLight.setVisibility(View.GONE);
        holder.reportedFilled.setVisibility(View.VISIBLE);
        holder.reportedBorder.setVisibility(View.GONE);
        holder.revieweLight.setVisibility(View.GONE);
        holder.revieweFilled.setVisibility(View.VISIBLE);
        holder.revieweBorder.setVisibility(View.GONE);
        holder.resolvedLight.setVisibility(View.VISIBLE);
        holder.resolvedFilled.setVisibility(View.VISIBLE);
        holder.resolvedBorder.setVisibility(View.GONE);
    }

}
