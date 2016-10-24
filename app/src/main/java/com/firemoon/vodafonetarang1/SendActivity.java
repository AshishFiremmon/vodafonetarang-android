package com.firemoon.vodafonetarang1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

/**
 * A login screen that offers login via email/password.
 */
public class SendActivity extends AppCompatActivity implements IResponse {


    // UI references.
     ProgressDialog mProgressDialog;
    private Handler handler;
    ArrayList<String> listdate=new ArrayList<>();
    ArrayList<String> listticket=new ArrayList<>();

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.send_screen);
        handler=new Handler();
        lv = (ListView)findViewById(R.id.listView1);

        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
               this);

		if(_checkInternetConnection.checkInterntConnection())
		{
            sendTicketlist();
			mProgressDialog = ProgressDialog.show(SendActivity.this, null,
                    "Please Wait....", true);
			mProgressDialog.setCancelable(true);

		}
		else {
			Toast.makeText(SendActivity.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();

		}



    }
    public void sendTicketlist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();

                SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


                data.add(new BasicNameValuePair("user_id",sharedpreferences.getString("USER_ID", "")));


                new Web().requestPostStringData(AppUrl.sendTicketUrl, data, SendActivity.this, 100);



            }
        }).start();
    }
    @Override
    public void onComplete(final  String result, int i) {
        mProgressDialog.cancel();

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    if (obj.getString("error").equals("false")) {


                        JSONArray array = obj.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jObj1 = array.getJSONObject(i);
                            listdate.add(jObj1.getString("issue_raised_on"));
                            listticket.add(jObj1.getString("ticket_id"));

                        }
                        MyAdapter adapter = new MyAdapter(SendActivity.this);
                        lv.setAdapter(adapter);


                    } else {
                        DialogInterfacecustom.loginResponceDialog(SendActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
            inflater = LayoutInflater.from(SendActivity.this);
            View row1 = inflater.inflate(R.layout.outbox_list, parent, false);

            tv1=(TextView)row1.findViewById(R.id.date);
            tv2=(TextView)row1.findViewById(R.id.ticketNumberTxt);
            tv1.setText(listdate.get(position));
            tv2.setText(listticket.get(position));


            return row1;
        }

    }
}

