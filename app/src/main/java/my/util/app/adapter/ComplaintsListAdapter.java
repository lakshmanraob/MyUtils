package my.util.app.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import my.util.app.models.LoginResult;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

public class ComplaintsListAdapter extends RecyclerView.Adapter<ComplaintsListAdapter.ViewHolder> {

    private LoginResult[] complaints;
    private boolean weekLabelDisplayed;
    private boolean monthLabelDisplayed;
    private boolean previousLabelDisplayed;

    public ComplaintsListAdapter(LoginResult[] complaints) {
        this.complaints = complaints;
    }

    public void updateList(LoginResult[] complaintsList) {
        //this.complaints = Utils.sortComplaintsList(complaintsList); // TODO:
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LoginResult issue = complaints[position];

        Log.d("DEBUG_LOG", "onBindViewHolder " + issue.getOutageType() + " - " + issue.getIssueStatus());

//        Calendar complaintDate = issue.getComplaintDate();
        String type = issue.getOutageType();
        holder.outageType.setText(String.valueOf(type.charAt(0)).toUpperCase() + type.substring(1, type.length()).toLowerCase());
//        holder.complaintDate.setText(complaintDate.get(Calendar.MONTH) + "/" +
//                complaintDate.get(Calendar.DAY_OF_MONTH) + "/" + complaintDate.get(Calendar.YEAR));
        holder.complaintDate.setText(issue.getIssueDate());
        holder.referenceNumber.setText(issue.getQmnum());
//        holder.outageSubType.setText("outageSubType");
        holder.complaintAddress.setText(issue.getLatiServAdd() + " - " + issue.getLongServAdd());

        String status = issue.getIssueStatus();
        if (Constants.COMPLAINT_STATUS.CL.equalsIgnoreCase(status)) {
            Log.d("DEBUG_LOG", "showResolvedStatus");
            showResolvedStatus(holder);
        } else if (Constants.COMPLAINT_STATUS.DP.equalsIgnoreCase(status) ||
                Constants.COMPLAINT_STATUS.AC.equalsIgnoreCase(status) ||
                Constants.COMPLAINT_STATUS.EN.equalsIgnoreCase(status) ||
                Constants.COMPLAINT_STATUS.AR.equalsIgnoreCase(status)) {
            Log.d("DEBUG_LOG", "showUnderReviewStatus");
            showUnderReviewStatus(holder, status);
        } else if (Constants.COMPLAINT_STATUS.RC.equalsIgnoreCase(status)) {
            Log.d("DEBUG_LOG", "showSubmittedStatus");
            showSubmittedStatus(holder);
        }

        /*if ((Constants.COMPLAINTS_TIMINGS.THIS_WEEK == issue.getComplaintTiming()) &&
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
        }*/

    }

    @Override
    public int getItemCount() {
        return complaints.length;
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
        @BindView(R.id.status_label)
        TextView statusLabel;
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

        holder.revieweLight.setVisibility(View.GONE);
        holder.revieweFilled.setVisibility(View.GONE);
        holder.revieweBorder.setVisibility(View.VISIBLE);

        holder.resolvedLight.setVisibility(View.GONE);
        holder.resolvedFilled.setVisibility(View.GONE);
        holder.resolvedBorder.setVisibility(View.VISIBLE);
    }

    private void showUnderReviewStatus(ViewHolder holder, String label) {
        holder.reportedLight.setVisibility(View.GONE);
        holder.reportedFilled.setVisibility(View.VISIBLE);
        holder.reportedBorder.setVisibility(View.GONE);

        holder.revieweLight.setVisibility(View.VISIBLE);
        holder.revieweFilled.setVisibility(View.VISIBLE);
        holder.revieweBorder.setVisibility(View.GONE);

        holder.statusLabel.setText(Utils.getStatusString(label));

        holder.resolvedLight.setVisibility(View.GONE);
        holder.resolvedFilled.setVisibility(View.GONE);
        holder.resolvedBorder.setVisibility(View.VISIBLE);
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
