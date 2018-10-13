package com.wallet.track.dev.melvin9.trackme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.track.dev.melvin9.trackme.Database.DatabaseHelper;
import com.wallet.track.dev.melvin9.trackme.Database.SqlData;

public class FirstActivity extends AppCompatActivity {
EditText editText;
Button bankBtn;
Button next;
private DatabaseHelper db;
String bankname;
String address_bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        editText=findViewById(R.id.income);
        bankBtn=findViewById(R.id.bankBtn);
        next=findViewById(R.id.next);
        db=new DatabaseHelper(this);
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean first=sharedPreferences.getBoolean("firstStart",true);
        if(!first){
            Intent i=new Intent(FirstActivity.this,MainActivity.class);
            startActivity(i);
        }
        bankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String names[] ={"FEDERAL BANK","SOUTH INDIAN BANK","STATE BANK OF INDIA","CANARA BANK","BANK OF BARODA","AXIS BANK","UNION BANK","PUNJAB BANK","BANK OF INDIA","UCO BANK","HSBC BANK","IDFC BANK","ICICI BANK","OTHER"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
                builder.setTitle("Select Your Bank");
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (names[item]){
                            case "FEDERAL BANK":MainActivity.Bank_Name="FEDERAL BANK";bankname="FEDERAL BANK";address_bank="fed";
                                MainActivity.Bank_ID="fed";break;
                            case "BANK OF BARODA":
                                MainActivity.Bank_Name="BANK OF BARODA";bankname="BANK OF BARODA";address_bank="bob";
                                MainActivity.Bank_ID="bob";break;
                            case "CANARA BANK":
                                MainActivity.Bank_Name="CANARA BANK";bankname="CANARA BANK";address_bank="canara";
                                MainActivity.Bank_ID="canara";break;
                            case "SOUTH INDIAN BANK":
                                MainActivity.Bank_Name="SOUTH INDIAN BANK";bankname="SOUTH INDIAN BANK";address_bank="sib";
                                MainActivity.Bank_ID="sib";break;
                            case "STATE BANK OF INDIA":
                                MainActivity.Bank_Name="STATE BANK OF INDIA";bankname="STATE BANK OF INDIA";address_bank="sbi";
                                MainActivity.Bank_ID="sbi";break;
                            case "AXIS BANK":
                                MainActivity.Bank_Name="AXIS BANK";bankname="AXIS BANK";address_bank="axis";
                                MainActivity.Bank_ID="axis";break;
                            case "UNION BANK":
                                MainActivity.Bank_Name="UNION BANK";bankname="UNION BANK";address_bank="union";
                                MainActivity.Bank_ID="union";break;
                            case "PUNJAB BANK":
                                MainActivity.Bank_Name="PUNJAB BANK";bankname="PUNJAB BANK";address_bank="pun";
                                MainActivity.Bank_ID="pun";break;
                            case "UCO BANK":
                                MainActivity.Bank_Name="UCO BANK";bankname="UCO BANK";address_bank="uco";
                                MainActivity.Bank_ID="uco";break;
                            case "HSBC BANK":
                                MainActivity.Bank_Name="HSBC BANK";bankname="HSBC BANK";address_bank="hsbc";
                                MainActivity.Bank_ID="hsbc";break;
                            case "IDFC INDIA":
                                MainActivity.Bank_Name="IDFC INDIA";bankname="IDFC INDIA";address_bank="idfc";
                                MainActivity.Bank_ID="idfc";break;
                            case "ICICI INDIA":
                                MainActivity.Bank_Name="ICICI INDIA";bankname="ICICI INDIA";address_bank="icici";
                                MainActivity.Bank_ID="icici";break;
                            case "BANK OF INDIA":
                                MainActivity.Bank_Name="BANK OF INDIA";bankname="BANK OF INDIA";address_bank="boi";
                                MainActivity.Bank_ID="boi";break;
                            case "OTHER":
                                MainActivity.Bank_Name="OTHER";bankname="OTHER"; address_bank="other";MainActivity.Bank_ID="other";break;
                        }
                        bankBtn.setText(bankname);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createData(editText.getText().toString(),bankname,address_bank);
                MainActivity.income=editText.getText().toString();
                Intent i=new Intent(FirstActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void createData(String income,String bank,String address) {
        // inserting data in db and getting
        db.insertData(income,bank,address);
        SharedPreferences preferences=getSharedPreferences("prefs",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
}
