package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.models.BillDetails;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

public class BillDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.bill_type_icon)
    protected ImageView mServiceTypeIcon;
    @BindView(R.id.bill_type)
    protected TextView mServiceType;
    @BindView(R.id.close)
    protected IconTextView mClose;
    @BindView(R.id.no_data)
    protected TextView mNoData;
    @BindView(R.id.content)
    protected ScrollView mContent;
    @BindView(R.id.date)
    protected TextView mDate;
    @BindView(R.id.billing_address)
    protected TextView mBillingAddress;
    @BindView(R.id.meter_number)
    protected TextView mMeterNumber;
    @BindView(R.id.billing_date)
    protected TextView mBillingDate;
    @BindView(R.id.total_consumption)
    protected TextView mTotalConsumption;
    @BindView(R.id.avg_consumption)
    protected TextView mAverageConsumption;
    @BindView(R.id.avg_consumption_graph)
    protected ImageView mConsumptionGraph;
    @BindView(R.id.plan_details)
    protected TextView mPlanDetails;
    @BindView(R.id.cost_per_unit_label)
    protected TextView mCostPerUnitLabel;
    @BindView(R.id.cost_per_unit)
    protected TextView mCostPerUnit;
    @BindView(R.id.pay_by_date)
    protected TextView mPayByDate;
    @BindView(R.id.total)
    protected TextView mTotal;

    private BillDetails bill;

    public BillDetailsFragment() {
    }

    public static BillDetailsFragment newInstance(String param1, String param2) {
        BillDetailsFragment fragment = new BillDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        bill = DataManager.getInstance(getContext()).getmCurrentBill();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_recent_bill_details, container, false);
        ButterKnife.bind(this, content);
        fillData();
        return content;
    }

    @OnClick(R.id.close)
    protected void closeScreen(View v) {
        ((BaseActivity) getActivity()).removeFragment(Constants.FRAGMENTS.BILL_DETAILS);
        ((BaseActivity) getActivity()).updateFragment(Constants.FRAGMENTS.BILLS);
    }

    @OnClick(R.id.pay_btn)
    protected void payBill(View v) {
        Utils.showShortToast(getContext(), "In Progress...");
    }

    private void fillData() {
        if (bill == null) {
            mContent.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mContent.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.GONE);

            String unit = getContext().getResources().getString((bill.getBillType() == Constants.BILL_TYPES.GAS) ? R.string.thm : R.string.kwh);

            mServiceTypeIcon.setImageResource((bill.getBillType() == Constants.BILL_TYPES.GAS) ? R.drawable.gas_icon_black : R.drawable.bulb_icon_black);
            mServiceType.setText((bill.getBillType() == Constants.BILL_TYPES.GAS) ? R.string.teco_gas : R.string.teco_electricity);

            mDate.setText(bill.getBillingDate());
            mBillingAddress.setText(bill.getAddress());
            mMeterNumber.setText(bill.getMeterNumber());
            mBillingDate.setText(bill.getBillingCycle());
            mTotalConsumption.setText(bill.getConsumption() + unit);
            mAverageConsumption.setText(bill.getConsumptionAverage() + unit);
            mPlanDetails.setText(bill.getPlanName());
            mCostPerUnitLabel.setText((bill.getBillType() == Constants.BILL_TYPES.GAS) ? R.string.cost_per_thm : R.string.cost_per_kwh);
            mCostPerUnit.setText(bill.getPlanCost() + "/" + unit);
            mPayByDate.setText(bill.getPayByDate());
            mTotal.setText("$"+bill.getTotal());
        }
    }

}
