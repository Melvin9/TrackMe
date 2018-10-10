package com.wallet.track.dev.melvin9.trackme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.wallet.track.dev.melvin9.trackme.Database.DatabaseHelper;
import com.wallet.track.dev.melvin9.trackme.Database.SqlData;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener,MainInterface {


    public static DatabaseHelper db;
    SqlData datas;
    TextView ph,message;
    public static TextView expenceTextView,balanceTextView,incomeTextView;
    Boolean permission = false;
    LinearLayout food;
    LinearLayout bill;
    LinearLayout health;
    LinearLayout party;
    LinearLayout edu;
    LinearLayout other;
    LinearLayout shop;
    LinearLayout cloth;
    LinearLayout gift;
    LinearLayout house;
    LinearLayout container_layout;
    ImageView foodImage;
    ImageView billImage;
    ImageView healthImage;
    ImageView partyImage;
    ImageView eduImage;
    ImageView otherImage;
    ImageView shopImage;
    ImageView clothImage;
    ImageView giftImage;
    ImageView houseImage;
    public static String Bank_ID;//getting from FirstActivity
    public static String Bank_Name;//getting from FirstActivity
    public static String income;//getting from firstActivity
    TextView bank;
    TextView updateIncome;
    Button addBTn;
    List<ListItem> list;
    int counter=0;
    public static PieChart pieChart;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageView;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialAssignments();Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        container_layout.setVisibility(View.INVISIBLE);
        final int[] item_index = {-1};
        final boolean[] first = {true};
        updateIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Income");

                // Set up the input
                final EditText input = new EditText(MainActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db=new DatabaseHelper(MainActivity.this);
                        SqlData data=db.getData();
                        incomeTextView.setText(data.getIncome()+Double.parseDouble(input.getText().toString())+"");
                        balanceTextView.setText(data.getBalance()+Double.parseDouble(input.getText().toString())+"");
                        updateData(11,data.getBalance(),Double.parseDouble(input.getText().toString()));
                        updateIncome(data.getIncome()+Double.parseDouble(input.getText().toString()));
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBankCLick();
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = checkSmsPermission();
            if (permission) {
               addItems();
            }
        }
        addBTn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
            if(!first[0]){
                if(counter<list.size()){
                    final ListItem item1 = list.get(counter);
                    DatabaseHelper data = new DatabaseHelper(MainActivity.this);
                    SqlData data1 = data.getData();
                    first[0] = true;
                    party.setEnabled(true);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    bill.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                    switch (item_index[0]) {
                        case 0:
                            MainActivity.updateData(0, data1.getFood(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 1:
                            MainActivity.updateData(1, data1.getBill(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 2:
                            MainActivity.updateData(2, data1.getTransportation(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 3:
                            MainActivity.updateData(3, data1.getHome(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 4:
                            MainActivity.updateData(4, data1.getEntertainment(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 5:
                            MainActivity.updateData(5, data1.getShopping(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 6:
                            MainActivity.updateData(6, data1.getCloth(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 7:
                            MainActivity.updateData(7, data1.getGift(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 8:
                            MainActivity.updateData(8, data1.getEducation(), Double.parseDouble(item1.getAmount()));
                            break;
                        case 9:
                            MainActivity.updateData(9, data1.getOthers(), Double.parseDouble(item1.getAmount()));
                            break;
                    }
                    MainActivity.updateData(10, data1.getExpense(), Double.parseDouble(item1.getAmount()));
                    MainActivity.updateData(11, data1.getBalance(), -Double.parseDouble(item1.getAmount()));
                    DatabaseHelper data2 = new DatabaseHelper(MainActivity.this);
                    SqlData datas = data2.getData();
                    MainActivity.showPieChart(((float) datas.getFood()/(float)datas.getExpense())*100.0f,((float) datas.getBill()/(float)datas.getExpense())*100.0f,((float) datas.getGift()/(float)datas.getExpense())*100.0f,((float) datas.getShopping()/(float)datas.getExpense())*100.0f,((float) datas.getCloth()/(float)datas.getExpense())*100.0f,((float) datas.getEntertainment()/(float)datas.getExpense())*100.0f,((float) datas.getHome()/(float)datas.getExpense())*100.0f,((float) datas.getTransportation()/(float)datas.getExpense())*100.0f,((float) datas.getEducation()/(float)datas.getExpense())*100.0f,((float) datas.getOthers()/(float)datas.getExpense())*100.0f);
                    Date date = new Date(Long.parseLong(item1.getTime()));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatted = format.format(date);
                    Timestamp timeStamp = Timestamp.valueOf(formatted);
                    MainActivity.updateDate(timeStamp.toString());
                    Toast.makeText(MainActivity.this,"Item Added",Toast.LENGTH_SHORT).show();
                    SqlData finalData = data.getData();
                    MainActivity.balanceTextView.setText(finalData.getBalance() + "");
                    MainActivity.expenceTextView.setText(finalData.getExpense() + "");
                    foodImage.setImageResource(R.drawable.dish);
                    billImage.setImageResource(R.drawable.bill);
                    shopImage.setImageResource(R.drawable.cart);
                    eduImage.setImageResource(R.drawable.classroom);
                    clothImage.setImageResource(R.drawable.fashion);
                    otherImage.setImageResource(R.drawable.other);
                    giftImage.setImageResource(R.drawable.gift);
                    houseImage.setImageResource(R.drawable.house);
                    healthImage.setImageResource(R.drawable.doctor);
                    partyImage.setImageResource(R.drawable.confetti);
                    item_index[0] = -1;
                    food.setBackgroundResource(R.drawable.buttonbackground);
                    bill.setBackgroundResource(R.drawable.buttonbackground);
                    shop.setBackgroundResource(R.drawable.buttonbackground);
                    edu.setBackgroundResource(R.drawable.buttonbackground);
                    cloth.setBackgroundResource(R.drawable.buttonbackground);
                    other.setBackgroundResource(R.drawable.buttonbackground);
                    gift.setBackgroundResource(R.drawable.buttonbackground);
                    house.setBackgroundResource(R.drawable.buttonbackground);
                    health.setBackgroundResource(R.drawable.buttonbackground);
                    party.setBackgroundResource(R.drawable.buttonbackground);
                    counter++;
                    if(counter<list.size()-1){
                        final ListItem item = list.get(counter);
                        ph.setText(item.getNumber());
                        message.setText(item.getMessage());
                    }
                    else{
                        container_layout.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                }

            }
               else{
                Toast.makeText(MainActivity.this,"Select an Item",Toast.LENGTH_SHORT).show();
            }
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 0;
                    foodImage.setImageResource(R.drawable.dishselected);
                    food.setBackgroundResource(R.drawable.buttonbackgroundfood);
                    bill.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    first[0] = true;
                    food.setBackgroundResource(R.drawable.buttonbackground);
                    foodImage.setImageResource(R.drawable.dish);
                    bill.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 1;
                    bill.setBackgroundResource(R.drawable.buttonbackgroundbill);
                    billImage.setImageResource(R.drawable.billselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    bill.setBackgroundResource(R.drawable.buttonbackground);
                    first[0] = true;
                    billImage.setImageResource(R.drawable.bill);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (first[0]) {
                    item_index[0] = 2;
                    health.setBackgroundResource(R.drawable.buttonbackgroundhealth);
                    healthImage.setImageResource(R.drawable.doctorselected);
                    food.setEnabled(false);
                    bill.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    health.setBackgroundResource(R.drawable.buttonbackground);
                    first[0] = true;
                    healthImage.setImageResource(R.drawable.doctor);
                    food.setEnabled(true);
                    bill.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 4;
                    party.setBackgroundResource(R.drawable.buttonbackgroundparty);
                    partyImage.setImageResource(R.drawable.confettiselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    bill.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    party.setBackgroundResource(R.drawable.buttonbackground);
                    first[0] = true;
                    partyImage.setImageResource(R.drawable.confetti);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    bill.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 8;
                    edu.setBackgroundResource(R.drawable.buttonbackgroundedu);
                    eduImage.setImageResource(R.drawable.classroomselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    bill.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    edu.setBackgroundResource(R.drawable.buttonbackground);
                    first[0] = true;
                    eduImage.setImageResource(R.drawable.classroom);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    bill.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 9;
                    other.setBackgroundResource(R.drawable.buttonbackgroundother);
                    otherImage.setImageResource(R.drawable.otherselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    bill.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    first[0] = true;
                    other.setBackgroundResource(R.drawable.buttonbackground);
                    otherImage.setImageResource(R.drawable.other);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    bill.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 5;
                    shop.setBackgroundResource(R.drawable.buttonbackgroundshop);
                    shopImage.setImageResource(R.drawable.cartselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    bill.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    shop.setBackgroundResource(R.drawable.buttonbackground);
                    first[0] = true;
                    shopImage.setImageResource(R.drawable.cart);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    bill.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 6;
                    cloth.setBackgroundResource(R.drawable.buttonbackgroundcloth);
                    clothImage.setImageResource(R.drawable.fashionselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    bill.setEnabled(false);
                    gift.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    first[0] = true;
                    cloth.setBackgroundResource(R.drawable.buttonbackground);
                    clothImage.setImageResource(R.drawable.fashion);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    bill.setEnabled(true);
                    gift.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 7;
                    gift.setBackgroundResource(R.drawable.buttonbackgroundgift);
                    giftImage.setImageResource(R.drawable.giftselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    bill.setEnabled(false);
                    house.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    first[0] = true;
                    gift.setBackgroundResource(R.drawable.buttonbackground);
                    giftImage.setImageResource(R.drawable.gift);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    bill.setEnabled(true);
                    house.setEnabled(true);
                }
            }
        });
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first[0]) {
                    item_index[0] = 3;
                    house.setBackgroundResource(R.drawable.buttonbackgroundhome);
                    houseImage.setImageResource(R.drawable.houseselected);
                    food.setEnabled(false);
                    health.setEnabled(false);
                    party.setEnabled(false);
                    edu.setEnabled(false);
                    other.setEnabled(false);
                    shop.setEnabled(false);
                    cloth.setEnabled(false);
                    gift.setEnabled(false);
                    bill.setEnabled(false);
                    first[0] = false;
                } else {
                    item_index[0] = -1;
                    first[0] = true;
                    house.setBackgroundResource(R.drawable.buttonbackground);
                    houseImage.setImageResource(R.drawable.house);
                    food.setEnabled(true);
                    health.setEnabled(true);
                    party.setEnabled(true);
                    edu.setEnabled(true);
                    other.setEnabled(true);
                    shop.setEnabled(true);
                    cloth.setEnabled(true);
                    gift.setEnabled(true);
                    bill.setEnabled(true);
                }
            }
        });

    }
    /**
     * Updating note in db
     */
    public static void updateData(int item_index,double prev,double present) {
        // updating data in db
        db.updateData(item_index,prev,present);
    }
    public static void updateBank(String bank,String address) {
        // updating bank in db
        db.updateBank(bank,address);
    }
    public static void updateIncome(double income) {
        // updating income in db
        db.updateincome(income);
    }
    public static void updateDate(String time) {
        // updating income in db
        db.setdate(time);
    }


    public boolean checkSmsPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_SMS);
                        addItems();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }
    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
    public static void showPieChart(float food, float bill, float travel, float shop, float cloth, float Party, float home, float health, float edu, float other) {

        pieChart.setUsePercentValues(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setText("");
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        if(food!=0)
            yvalues.add(new PieEntry(food, "Food"));
        if(bill!=0)
            yvalues.add(new PieEntry(bill, "Bill"));
        if(travel!=0)
            yvalues.add(new PieEntry(travel, "Gift"));
        if(shop!=0)
            yvalues.add(new PieEntry(shop, "Shop"));
        if(cloth!=0)
            yvalues.add(new PieEntry(cloth, "Cloth"));
        if(Party!=0)
            yvalues.add(new PieEntry(Party, "Entertain"));
        if(home!=0)
            yvalues.add(new PieEntry(home, "Home"));
        if(health!=0)
            yvalues.add(new PieEntry(health, "Health"));
        if(edu!=0)
            yvalues.add(new PieEntry(edu, "Education"));
        if(other!=0)
            yvalues.add(new PieEntry(other, "Other"));
        PieDataSet dataSet = new PieDataSet(yvalues, "Money Distribution");
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieChart.setEntryLabelTextSize(10f);
        pieChart.setEntryLabelColor(Color.parseColor("#616161"));
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.parseColor("#A1887F"),
                Color.parseColor("#78909C"),
                Color.parseColor("#8BC34A"),
                Color.parseColor("#00ACC1"),
                Color.parseColor("#CDDC39"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#00796B"),
                Color.parseColor("#7CB342"),
                Color.parseColor("#E65100"),
                Color.parseColor("#7986CB")
        );
        PieData pieData=new PieData(dataSet);
        pieData.setValueTextSize(8f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setData(pieData);
        pieChart.animateXY(2400, 2400);
    }

    @Override
    public void onBankCLick() {
        final String names[] ={"FEDERAL BANK","SOUTH INDIAN BANK","STATE BANK OF INDIA","CANARA BANK","BANK OF BARODA","AXIS BANK","UNION BANK","PUNJAB BANK","BANK OF INDIA","UCO BANK","HSBC BANK","IDFC BANK","ICICI BANK","OTHER"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Your Bank");
        builder.setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                switch (names[item]){
                    case "FEDERAL BANK":MainActivity.Bank_Name="FEDERAL BANK";
                        MainActivity.Bank_ID="fed";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "BANK OF BARODA":
                        MainActivity.Bank_Name="BANK OF BARODA";
                        MainActivity.Bank_ID="bob";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "CANARA BANK":
                        MainActivity.Bank_Name="CANARA BANK";
                        MainActivity.Bank_ID="canara";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "SOUTH INDIAN BANK":
                        MainActivity.Bank_Name="SOUTH INDIAN BANK";
                        MainActivity.Bank_ID="sib";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "STATE BANK OF INDIA":
                        MainActivity.Bank_Name="STATE BANK OF INDIA";
                        MainActivity.Bank_ID="sbi";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "AXIS BANK":
                        MainActivity.Bank_Name="AXIS BANK";
                        MainActivity.Bank_ID="axis";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "UNION BANK":
                        MainActivity.Bank_Name="UNION BANK";
                        MainActivity.Bank_ID="union";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "PUNJAB BANK":
                        MainActivity.Bank_Name="PUNJAB BANK";
                        MainActivity.Bank_ID="pun";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "UCO BANK":
                        MainActivity.Bank_Name="UCO BANK";
                        MainActivity.Bank_ID="uco";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "HSBC BANK":
                        MainActivity.Bank_Name="HSBC BANK";
                        MainActivity.Bank_ID="hsbc";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "IDFC INDIA":
                        MainActivity.Bank_Name="IDFC INDIA";
                        MainActivity.Bank_ID="idfc";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "ICICI INDIA":
                        MainActivity.Bank_Name="ICICI INDIA";
                        MainActivity.Bank_ID="icici";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "BANK OF INDIA":
                        MainActivity.Bank_Name="BANK OF INDIA";
                        MainActivity.Bank_ID="boi";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                    case "OTHER":
                        MainActivity.Bank_Name="OTHER";MainActivity.Bank_ID="other";updateBank(Bank_Name,Bank_ID);addItems();
                        Toast.makeText(MainActivity.this,"Bank Updated",Toast.LENGTH_LONG).show();break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initialAssignments() {
        imageView = findViewById(R.id.empty);
        expenceTextView = findViewById(R.id.expenseTextView);
        balanceTextView = findViewById(R.id.balanceTextView);
        incomeTextView = findViewById(R.id.incomeTextView);
        addBTn=findViewById(R.id.addBtn);
        container_layout=findViewById(R.id.container);
        ph=findViewById(R.id.ph_no);
        message=findViewById(R.id.msg);
        updateIncome=findViewById(R.id.updateIncome);
        pieChart = findViewById(R.id.piechart);
        food = findViewById(R.id.foodLinearLayout);
        bill = findViewById(R.id.billLinearLayout);
        health = findViewById(R.id.healthLinearLayout);
        party = findViewById(R.id.partyLinearLayout);
        edu = findViewById(R.id.educationLinearLayout);
        other = findViewById(R.id.otherLinearLayout);
        shop = findViewById(R.id.shopLinearLayout);
        cloth = findViewById(R.id.clothLinearLayout);
        gift = findViewById(R.id.giftLinearLayout);
        house = findViewById(R.id.houseLinearLayout);
        foodImage = findViewById(R.id.foodImageView);
        billImage = findViewById(R.id.billImageView);
        healthImage = findViewById(R.id.healthImageView);
        partyImage = findViewById(R.id.partyImageView);
        eduImage = findViewById(R.id.educationImageView);
        otherImage = findViewById(R.id.otherImageView);
        shopImage = findViewById(R.id.shopImageView);
        clothImage = findViewById(R.id.clothImageView);
        giftImage = findViewById(R.id.giftImageView);
        houseImage = findViewById(R.id.houseImageView);
        PieChart piechart1=findViewById(R.id.piechart);
        db = new DatabaseHelper(this);
        datas = db.getData();
        if(datas.getFood()==0 && datas.getBill()==0 && datas.getGift()==0 && datas.getOthers()==0 && datas.getEducation()==0 && datas.getHealth()==0 && datas.getHome()==0 && datas.getShopping()==0 && datas.getCloth()==0 && datas.getEntertainment()==0){
            piechart1.setUsePercentValues(true);
            piechart1.getLegend().setEnabled(false);
            piechart1.getDescription().setText("");
            piechart1.setExtraOffsets(5,10,5,5);
            piechart1.setDragDecelerationFrictionCoef(0.95f);
            piechart1.setDrawHoleEnabled(true);
            piechart1.setTransparentCircleRadius(61f);
            ArrayList<PieEntry> yvalues = new ArrayList<>();

                yvalues.add(new PieEntry(10.0f, "Food"));
                yvalues.add(new PieEntry(10.0f, "Bill"));
                yvalues.add(new PieEntry(10.0f, "Gift"));
                yvalues.add(new PieEntry(10.0f, "Shop"));
                yvalues.add(new PieEntry(10.0f, "Cloth"));
                yvalues.add(new PieEntry(10.0f, "Entertain"));
                yvalues.add(new PieEntry(10.0f, "Home"));
                yvalues.add(new PieEntry(10.0f, "Health"));
                yvalues.add(new PieEntry(10.0f, "Education"));
                yvalues.add(new PieEntry(10.0f, "Other"));
            PieDataSet dataSet = new PieDataSet(yvalues, "Money Distribution");
            dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            piechart1.setEntryLabelTextSize(10f);
            piechart1.setEntryLabelColor(Color.parseColor("#616161"));
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(Color.parseColor("#A1887F"),
                    Color.parseColor("#78909C"),
                    Color.parseColor("#8BC34A"),
                    Color.parseColor("#00ACC1"),
                    Color.parseColor("#CDDC39"),
                    Color.parseColor("#2196F3"),
                    Color.parseColor("#00796B"),
                    Color.parseColor("#7CB342"),
                    Color.parseColor("#E65100"),
                    Color.parseColor("#7986CB")
            );
            PieData pieData=new PieData(dataSet);
            pieData.setValueTextSize(8f);
            pieData.setValueTextColor(Color.DKGRAY);
            piechart1.setData(pieData);
            piechart1.animateXY(2400, 2400);
        }else
            MainActivity.showPieChart(((float) datas.getFood() / (float) datas.getExpense()) * 100.0f, ((float) datas.getBill() / (float) datas.getExpense()) * 100.0f, ((float) datas.getGift() / (float) datas.getExpense()) * 100.0f, ((float) datas.getShopping() / (float) datas.getExpense()) * 100.0f, ((float) datas.getCloth() / (float) datas.getExpense()) * 100.0f, ((float) datas.getEntertainment() / (float) datas.getExpense()) * 100.0f, ((float) datas.getHome() / (float) datas.getExpense()) * 100.0f, ((float) datas.getHealth() / (float) datas.getExpense()) * 100.0f, ((float) datas.getEducation() / (float) datas.getExpense()) * 100.0f, ((float) datas.getOthers() / (float) datas.getExpense()) * 100.0f);
        Bank_ID = datas.getAddress();
        bank = findViewById(R.id.bank);
        expenceTextView.setText(datas.getExpense() + "");
        balanceTextView.setText(datas.getBalance() + "");
        incomeTextView.setText(datas.getIncome() + "");

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @Override
    public void addItems() {
        AlarmReceiver.B_ID=Bank_ID;
        list = new ArrayList();
        ListItem listItem;
        if(Bank_ID.equals("other")) {
            Uri uriSms = Uri.parse("content://sms/");
            @SuppressLint("Recycle") final Cursor cursor = getContentResolver().query(uriSms,
                    new String[]{"_id", "address", "person", "date", "body", "type"}, "lower(body) like '%debited%'", null, "date");

            assert cursor != null;
            while (cursor.moveToNext()) {
                container_layout.setVisibility(View.VISIBLE);
                String address = cursor.getString(1);
                String amt = null;
                String msg = cursor.getString(4);
                String date = cursor.getString(3);
                SqlData data = db.getData();
                if (address != null) {
                    Date date1 = new Date(Long.parseLong(date));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatted = format.format(date1);
                    if (formatted.compareTo(data.getTimestamp()) > 0) {
                        Pattern regEx1
                                = Pattern.compile("(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Aa][Cc][Cc][Tt].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)");
                        // Find instance of pattern matches
                        Matcher m1 = regEx1.matcher(msg);
                        if (m1.find()) {
                            try {
                                Pattern regEx
                                        = Pattern.compile("(?:(?:Rs|RS|inr|INR)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
                                // Find instance of pattern matches
                                Matcher m = regEx.matcher(msg);
                                if (m.find()) {
                                    try {
                                        Log.e("amount_value= ", "" + m.group(0));
                                        amt = (m.group(0).replaceAll("inr", ""));
                                        amt = amt.replaceAll("rs", "");
                                        amt = amt.replaceAll("Rs.", "");
                                        amt = amt.replaceAll("rs.", "");
                                        amt = amt.replaceAll("Rs", "");
                                        amt = amt.replaceAll("INR", "");
                                        amt = amt.replaceAll("INR.", "");
                                        amt = amt.replaceAll("inr", "");
                                        amt = amt.replaceAll(" ", "");
                                        amt = amt.replaceAll(",", "");

                                    } catch (Exception ignored) {
                                    }
                                } else {
                                    Log.e("No_matchedValue ", "No_matchedValue ");
                                }

                                listItem = new ListItem();
                                listItem.setNumber(address);
                                listItem.setTime(date);
                                listItem.setMessage(msg);
                                listItem.setAmount(amt);
                                list.add(listItem);
                            } catch (Exception ignored) {
                            }
                        } else {
                            Log.e("No_matchedValue ", "No_matchedValue ");
                        }


                    }

                }
            }

        }else{
            Uri uriSms = Uri.parse("content://sms/");
            @SuppressLint("Recycle") final Cursor cursor = getContentResolver().query(uriSms,
                    new String[]{"_id", "address","person", "date", "body","type"}, "LOWER(address) like '%"+Bank_ID+"%' and lower(body) like '%debited%'",null, "date");

            assert cursor != null;
            while (cursor.moveToNext()) {
                container_layout.setVisibility(View.VISIBLE);
                String address = cursor.getString(1);
                String amt = null;
                String msg = cursor.getString(4);
                String date = cursor.getString(3);
                SqlData data=db.getData();
                if(address!=null){
                    Date date1 = new Date(Long.parseLong(date));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatted = format.format(date1);
                    if(formatted.compareTo(data.getTimestamp())>0){
                        Pattern regEx
                                = Pattern.compile("(?:(?:Rs|RS|inr|INR)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
                        // Find instance of pattern matches
                        Matcher m = regEx.matcher(msg);
                        if (m.find()) {
                            try {
                                Log.e("amount_value= ", "" + m.group(0));
                                amt = (m.group(0).replaceAll("inr", ""));
                                amt = amt.replaceAll("rs", "");
                                amt = amt.replaceAll("Rs.", "");
                                amt = amt.replaceAll("rs.", "");
                                amt = amt.replaceAll("Rs", "");
                                amt = amt.replaceAll("INR", "");
                                amt = amt.replaceAll("INR.", "");
                                amt = amt.replaceAll("inr", "");
                                amt = amt.replaceAll(" ", "");
                                amt = amt.replaceAll(",", "");

                            } catch (Exception ignored) {
                            }
                        } else {
                            Log.e("No_matchedValue ", "No_matchedValue ");
                        }

                        listItem = new ListItem();
                        listItem.setNumber(address);
                        listItem.setTime(date);
                        listItem.setMessage(msg);
                        listItem.setAmount(amt);
                        list.add(listItem);

                    }

                }
        }



        }
        if(counter<list.size()){
            final ListItem item = list.get(0);
            MainActivity.imageView.setVisibility(View.INVISIBLE);
            ph.setText(item.getNumber());
            message.setText(item.getMessage());
        }else{
            MainActivity.imageView.setVisibility(View.VISIBLE);
            container_layout.setVisibility(View.INVISIBLE);
        }

    }

}