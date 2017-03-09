package com.project.harsh.reminderapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private DbHelper mHelper;
    private SQLiteDatabase dataBase;

    private ArrayList<String> userId = new ArrayList<String>();
    private ArrayList<String> user_name = new ArrayList<String>();
    private ArrayList<String> user_datetime = new ArrayList<String>();

    private ListView userlist;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);

        userlist = (ListView)findViewById(R.id.List);
        mHelper = new DbHelper(this);

        Button add = (Button)findViewById(R.id.setdate);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayActivity.this, MainActivity.class);
                i.putExtra("update", false);
                startActivity(i);
            }
        });

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("name", user_name.get(arg2));
                i.putExtra("datetime", user_datetime.get(arg2));
                i.putExtra("ID", userId.get(arg2));
                i.putExtra("update", true);
                startActivity(i);

            }
        });

        userlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

                builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setTitle("Delete " + user_name.get(arg2) + " " + user_datetime.get(arg2));
                builder.setMessage("Do you want to delete ?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText( getApplicationContext(),
                                user_name.get(arg2) + " "
                                        + user_datetime.get(arg2)
                                        + " is deleted.", Toast.LENGTH_SHORT).show();

                        dataBase.delete(
                                DbHelper.TABLE_NAME,
                                DbHelper.KEY_ID + "="
                                        + userId.get(arg2), null);
                        displayData();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME, null);

        userId.clear();
        user_name.clear();
        user_datetime.clear();
        if (mCursor.moveToFirst()) {
            do {
                userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                user_name.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_FNAME)));
                user_datetime.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_LNAME)));

            } while (mCursor.moveToNext());
        }
        DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId,user_name,user_datetime);
        userlist.setAdapter(disadpt);
        mCursor.close();
    }
}