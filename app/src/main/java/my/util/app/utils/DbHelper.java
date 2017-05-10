package my.util.app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import my.util.app.models.BillDetails;
import my.util.app.models.IssueDetails;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teco_db.db";
    public static final int DB_VERSION = 1;

    public static final String COMPLAINTS_TABLE = "complaints_table";
    public static final String BILLS_TABLE = "bills_table";

    public static final String COL_ID = "complaint_id";
    public static final String COL_OUTAGE_TYPE = "outage_type";
    public static final String COL_COMPLAINT_DATE = "complaint_date";
    public static final String COL_ADDRESS = "address";
    public static final String COL_STATUS = "status";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_REF_NO = "reference_no";

    public static final String COL_BILL_ADDRESS = "bill_address";
    public static final String COL_BILL_TYPE = "bill_type";
    public static final String COL_BILL_PROVIDER = "bill_provider";
    public static final String COL_BILL_CONSUMPTION = "bill_consumption";
    public static final String COL_BILL_CONSUMPTION_AVERAGE = "bill_consumption_average";
    public static final String COL_BILL_AMOUNT = "bill_amount";
    public static final String COL_BILL_BILLING_DATE = "bill_date";
    public static final String COL_BILL_BILLING_CYCLE = "bill_cycle";
    public static final String COL_BILL_PAY_BY_DATE = "bill_pay_by_date";
    public static final String COL_BILL_METER_NO = "bill_meter_no";
    public static final String COL_BILL_PLAN_NAME = "bill_plan_name";
    public static final String COL_BILL_PLAN_COST = "bill_plan_cost";
    public static final String COL_BILL_TOTAL = "bill_total";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        Log.d("DEBUG_LOG", "DbHelper : created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + COMPLAINTS_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_OUTAGE_TYPE + " TEXT NOT NULL, " +
                COL_COMPLAINT_DATE + " REAL NOT NULL, " +
                COL_ADDRESS + " TEXT NOT NULL, " +
                COL_STATUS + " INTEGER NOT NULL, " +
                COL_LATITUDE + " REAL NOT NULL, " +
                COL_LONGITUDE + " REAL NOT NULL, " +
                COL_REF_NO + " INTEGER NOT NULL);"
        );
        Log.d("DEBUG_LOG", "DbHelper : Table - " + COMPLAINTS_TABLE + " created");

        database.execSQL("CREATE TABLE " + BILLS_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BILL_ADDRESS + " TEXT NOT NULL, " +
                COL_BILL_TYPE + " INTEGER NOT NULL, " +
                COL_BILL_PROVIDER + " TEXT NOT NULL, " +
                COL_BILL_CONSUMPTION + " TEXT NOT NULL, " +
                COL_BILL_CONSUMPTION_AVERAGE + " TEXT, " +
                COL_BILL_AMOUNT + " INTEGER NOT NULL, " +
                COL_BILL_BILLING_DATE + " TEXT NOT NULL, " +
                COL_BILL_BILLING_CYCLE + " TEXT, " +
                COL_BILL_PAY_BY_DATE + " TEXT, " +
                COL_BILL_METER_NO + " TEXT, " +
                COL_BILL_PLAN_NAME + " TEXT, " +
                COL_BILL_PLAN_COST + " TEXT, " +
                COL_BILL_TOTAL + " INTEGER);"
        );
        Log.d("DEBUG_LOG", "DbHelper : Table - " + BILLS_TABLE + " created");
    }

    public long addNewComplaint(IssueDetails issue){
        Log.d("DEBUG_LOG", "DbHelper : new complaint added at : " + issue.getAddress());
        ContentValues values = new ContentValues();
        values.put(COL_OUTAGE_TYPE , issue.getOutageType());
        values.put(COL_COMPLAINT_DATE , issue.getComplaintDate().getTimeInMillis());
        values.put(COL_ADDRESS , issue.getAddress());
        values.put(COL_STATUS , issue.getStatus());
        values.put(COL_LATITUDE , issue.getLatitude());
        values.put(COL_LONGITUDE , issue.getLongitude());
        values.put(COL_REF_NO , issue.getReferenceNo());

        return getWritableDatabase().insert(COMPLAINTS_TABLE, null, values);
        /*getWritableDatabase().execSQL("INSERT INTO " + COMPLAINTS_TABLE + " (" +
                COL_OUTAGE_TYPE + ", " +
                COL_COMPLAINT_DATE + ", " +
                COL_ADDRESS + ", " +
                COL_STATUS + ", " +
                COL_LATITUDE + ", " +
                COL_LONGITUDE + ", " +
                COL_REF_NO + ") VALUES ('" +
                issue.getOutageType() + "', " +
                issue.getComplaintDate().getTimeInMillis() + ", "+
                issue.getAddress() + ", "+
                issue.getStatus() + ", "+
                issue.getLatitude() + ", "+
                issue.getLongitude() + ", "+
                issue.getReferenceNo() +");"
        );*/
    }

    public long addNewBill(BillDetails bill){
        Log.d("DEBUG_LOG", "DbHelper : new bill added at : " + bill.getAddress());
        ContentValues values = new ContentValues();
        values.put(COL_BILL_ADDRESS, bill.getAddress() );
        values.put(COL_BILL_TYPE, bill.getBillType() );
        values.put(COL_BILL_PROVIDER, bill.getProvider() );
        values.put(COL_BILL_CONSUMPTION, bill.getConsumption() );
        values.put(COL_BILL_CONSUMPTION_AVERAGE, bill.getConsumptionAverage() );
        values.put(COL_BILL_AMOUNT, bill.getAmount() );
        values.put(COL_BILL_BILLING_DATE, bill.getBillingDate() );
        values.put(COL_BILL_BILLING_CYCLE, bill.getBillingCycle() );
        values.put(COL_BILL_PAY_BY_DATE, bill.getPayByDate() );
        values.put(COL_BILL_METER_NO, bill.getMeterNumber() );
        values.put(COL_BILL_PLAN_NAME, bill.getPlanName() );
        values.put(COL_BILL_PLAN_COST, bill.getPlanCost() );
        values.put(COL_BILL_TOTAL, bill.getTotal() );

        return getWritableDatabase().insert(BILLS_TABLE, null, values);
    }

    public ArrayList<IssueDetails> getAllComplaints(){
        ArrayList<IssueDetails> complaintsList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + COMPLAINTS_TABLE, null);
        if (cursor.moveToFirst()) {
            IssueDetails issue;
            do {
                issue = new IssueDetails();
                issue.setOutageType(cursor.getString(1));
                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(cursor.getLong(2));
                issue.setComplaintDate(date);
                issue.setAddress(cursor.getString(3));
                issue.setStatus(cursor.getInt(4));
                issue.setLatitude(cursor.getDouble(5));
                issue.setLongitude(cursor.getDouble(6));
                issue.setReferenceNo(cursor.getInt(7));
                complaintsList.add(issue);
            } while (cursor.moveToNext());
        }
        return complaintsList;
    }

    public ArrayList<BillDetails> getAllBills(){
        ArrayList<BillDetails> billsList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + BILLS_TABLE, null);
        if (cursor.moveToFirst()) {
            BillDetails bill;
            do {
                bill = new BillDetails();
                bill.setAddress(cursor.getString(1));
                bill.setBillType(cursor.getInt(2));
                bill.setProvider(cursor.getString(3));
                bill.setConsumption(cursor.getString(4));
                bill.setConsumptionAverage(cursor.getString(5));
                bill.setAmount(cursor.getInt(6));
                bill.setBillingDate(cursor.getString(7));
                bill.setBillingCycle(cursor.getString(8));
                bill.setPayByDate(cursor.getString(9));
                bill.setMeterNumber(cursor.getString(10));
                bill.setPlanName(cursor.getString(11));
                bill.setPlanCost(cursor.getString(12));
                bill.setTotal(cursor.getInt(13));
                billsList.add(bill);
            } while (cursor.moveToNext());
        }
        return billsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DEBUG_LOG", "DbHelper : onUpgrade called " + oldVersion + " > " + newVersion);
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + COMPLAINTS_TABLE);
        onCreate(db);
    }
}
