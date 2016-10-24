package com.firemoon.vodafonetarang1;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Db.Contact;
import Db.DatabaseHandler;

/**
 * A login screen that offers login via email/password.
 */
public class TicketInOutboxActivity extends AppCompatActivity{


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Handler handler;
    DatabaseHandler db;
    ListView lv;
    ArrayList<String> listdate=new ArrayList<>();
    ArrayList<String> listticket=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.outbox_screen);
        lv = (ListView)findViewById(R.id.listView1);

        handler=new Handler();
         db = new DatabaseHandler(this);



        handler.post(new Runnable() {

            @Override
            public void run() {

                List<Contact> contacts = db.getAllContacts();

                for (Contact cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    listdate.add(cn.getName());
                    listticket.add(cn.getID()+"");

                }

                MyAdapter adapter = new MyAdapter(TicketInOutboxActivity.this);
                lv.setAdapter(adapter);


            }
        });

    }

    public class MyAdapter extends BaseAdapter {


        public MyAdapter(Context context)
        {

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listdate.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final  int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView tv1, tv2,tv3,tv4,tv5,tv6;
            LayoutInflater inflater;
            inflater = LayoutInflater.from(TicketInOutboxActivity.this);
            View row1 = inflater.inflate(R.layout.outbox_list, parent, false);

            tv1=(TextView)row1.findViewById(R.id.date);
            tv2=(TextView)row1.findViewById(R.id.ticketNumberTxt);
              tv1.setText(listdate.get(position));
            tv2.setText(listticket.get(position));


            return row1;
        }

    }
}

