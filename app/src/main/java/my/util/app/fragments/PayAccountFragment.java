package my.util.app.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.activity.SignUpActivity;
import my.util.app.models.BillDetails;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

public class PayAccountFragment extends Fragment {

    @BindView(R.id.acc_no_layout)
    LinearLayout accNoLayout;
    @BindView(R.id.details_layout)
    LinearLayout detailsCardLayout;
    @BindView(R.id.content)
    ScrollView detailsLayout;
    @BindView(R.id.go_back)
    LinearLayout backLayout;

    @BindView(R.id.pay_input_account)
    EditText payAccountEdit;
    @BindView(R.id.enter)
    ImageView enter;
    @BindView(R.id.progress)
    IconTextView progress;
    @BindView(R.id.btn_link_signup)
    Button signup;
    @BindView(R.id.pay_account_help)
    TextView payAccountHelp;

    @BindView(R.id.left_circle)
    ImageView leftCircle;
    @BindView(R.id.right_circle)
    ImageView rightCircle;

    @BindView(R.id.bill_type_icon)
    ImageView billTypeIcon;
    @BindView(R.id.bill_type)
    TextView billType;
    @BindView(R.id.complaint_date)
    TextView complaintDate;
    @BindView(R.id.consumption)
    TextView consumption;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.total_bill)
    TextView totalBill;

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

    String title;
    int page;
    BillDetails bill;

    public static PayAccountFragment newInstance(String title, int pageNumber) {
        PayAccountFragment fragment = new PayAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PAGE_TITLE, title);
        bundle.putInt(Constants.PAGE_NUMBER, pageNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(Constants.PAGE_TITLE);
        page = getArguments().getInt(Constants.PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_pay_account, container, false);
        ButterKnife.bind(this, content);
        payAccountHelp.setVisibility(View.INVISIBLE);

        Drawable circle = getContext().getResources().getDrawable(R.drawable.white_circle_filled);
        Bitmap circleBitmap = Utils.drawableToBitmap(circle);
        if (circleBitmap != null) {
            int width = circleBitmap.getWidth();
            int height = circleBitmap.getHeight();
            int x = width / 2;
            Bitmap leftBitmap = Bitmap.createBitmap(circleBitmap, x, 0, x, height, null, false);
            leftCircle.setImageBitmap(leftBitmap);
            Bitmap rightBitmap = Bitmap.createBitmap(circleBitmap, 0, 0, x, height, null, false);
            rightCircle.setImageBitmap(rightBitmap);
        }
        populateBillCard();
        populateBillDetails();

        return content;
    }

    @OnClick(R.id.btn_link_signup)
    protected void signUp(View v) {
        Intent signupIntent = new Intent();
        signupIntent.setClass(getActivity(), SignUpActivity.class);
        startActivity(signupIntent);
    }

    @OnClick(R.id.pay_info)
    protected void showPayInfo(View v) {
        payAccountHelp.setVisibility(View.VISIBLE);

        payAccountHelp.postDelayed(new Runnable() {
            @Override
            public void run() {
                payAccountHelp.setVisibility(View.INVISIBLE);
            }
        }, Constants.INFO_DISPLAY_TIME);
    }

    @OnClick(R.id.enter)
    protected void pay(View v) {
        String accountNo = payAccountEdit.getText().toString();
        if (!TextUtils.isEmpty(accountNo) && accountNo.length() >= Constants.ACC_NO_LEN) {
            enter.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            progress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                    accNoLayout.setVisibility(View.GONE);
                    detailsCardLayout.setVisibility(View.VISIBLE);
                }
            }, Constants.PROGRESS_TIME);
        } else {
            enter.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            Utils.showShortToast(getContext(), "Please enter a valid account no.");
        }
    }

    @OnClick(R.id.bill_details)
    protected void showBillDetails(View v) {
        DataManager.getInstance(getContext()).setmCurrentBill(bill);
        detailsCardLayout.setVisibility(View.GONE);
        detailsLayout.setVisibility(View.VISIBLE);
        backLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.pay)
    protected void payBill(View v) {
        Utils.showShortToast(getContext(), "In Progress...");
    }

    @OnClick(R.id.pay_btn)
    protected void payBillBtn(View v) {
        Utils.showShortToast(getContext(), "In Progress...");
    }

    @OnClick(R.id.go_back)
    protected void closeDetails(View v) {
        detailsCardLayout.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.GONE);
    }

    private void populateBillCard() {
        bill = Constants.getFriendsBill();
        int type = bill.getBillType();
        billTypeIcon.setImageResource(type == 1 ? R.drawable.bulb_icon : R.drawable.gas_icon);
        billType.setText(type == 1 ? "Elctricity Services" : "Gas Services");
        complaintDate.setText(bill.getBillingDate());
        address.setText(bill.getAddress());
        consumption.setText(bill.getConsumption() + (type == 1 ? " Kwh" : " Thm"));
        amount.setText("$" + bill.getTotal());
        totalBill.setText("$" + bill.getTotal());
    }

    private void populateBillDetails() {
        String unit = getContext().getResources().getString((bill.getBillType() == Constants.BILL_TYPES.GAS) ? R.string.thm : R.string.kwh);
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
        mTotal.setText("$" + bill.getTotal());
    }
}
