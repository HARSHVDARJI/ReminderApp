package com.project.harsh.reminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText date, name;
    Button setbtn;
    Calendar c;
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;
    private String id,rname,rdatetime;
    int mHour;
    int mMinute;
    private boolean isUpdate;
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (EditText) findViewById(R.id.gettdate);
        name = (EditText) findViewById(R.id.getname);
        setbtn = (Button)findViewById(R.id.setbtn);

        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            id=getIntent().getExtras().getString("ID");
            rname=getIntent().getExtras().getString("name");
            rdatetime=getIntent().getExtras().getString("datetime");
            name.setText(rname);
            date.setText(rdatetime);

        }

        final SQLiteDatabase db = openOrCreateDatabase("myDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS myTable (FirstName VARCHAR,Datetime VARCHAR)");

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rname=name.getText().toString().trim();
                rdatetime=date.getText().toString().trim();
                if(rname.length()>0 && rdatetime.length()>0)
                {
                    saveData();
                }else
                {
                    AlertDialog.Builder alertBuilder=new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setTitle("Invalid Data");
                    alertBuilder.setMessage("Please, Enter valid data");
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertBuilder.create().show();
                }

            }
//                String fname,lname;
//
//                fname = name.getText().toString();
//                lname = date.getText().toString();
//
//                db.execSQL("INSERT INTO myTable VALUES ('"+fname+"','"+lname+"')");
//                name.setText("");
//                date.setText("");
//                saveData();
            });

        mHelper=new DbHelper(this);
    }

    private void saveData() {
        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DbHelper.KEY_FNAME,rname);
        values.put(DbHelper.KEY_LNAME,rdatetime );

        System.out.println("");
        if(isUpdate)
        {
            //update database with new data
            dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
        }
        else
        {
            //insert data into database
            dataBase.insert(DbHelper.TABLE_NAME, null, values);
        }
        //close database
        dataBase.close();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        date.setText(date_time + " " + hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
