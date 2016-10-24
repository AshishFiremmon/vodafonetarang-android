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

import Modal.ResolveDetalModal;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

/**
 * A login screen that offers login via email/password.
 */
public class PendingTicketActivity extends AppCompatActivity implements IResponse {


    ArrayList<ResolveDetalModal> list=new ArrayList<>();

    ListView lv;
    ProgressDialog mProgressDialog;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.pending_screen);
        handler=new Handler();

        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                this);

        if(_checkInternetConnection.checkInterntConnection())
        {
            resolvelist();
            mProgressDialog = ProgressDialog.show(PendingTicketActivity.this, null,
                    "Please Wait....", true);
            mProgressDialog.setCancelable(false);

        }
        else {
            Toast.makeText(PendingTicketActivity.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }


        lv = (ListView)findViewById(R.id.listView1);


    }
    public void resolvelist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();

                SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


                data.add(new BasicNameValuePair("user_id",sharedpreferences.getString("USER_ID", "")));


                new Web().requestPostStringData(AppUrl.pendingUrl, data, PendingTicketActivity.this, 100);



            }
        }).start();
    }
    @Override
    public void onComplete(final String result, int i) {
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
                            list.add(new ResolveDetalModal(jObj1.getString("ticket_id"), jObj1.getString("issue_raised_on"), jObj1.getString("closed_on"), jObj1.getString("issue"), jObj1.getString("alt_number"), jObj1.getString("zone"),"", jObj1.getString("expected_closer_date")));

                        }
                        MyAdapter adapter = new MyAdapter(PendingTicketActivity.this);
                        lv.setAdapter(adapter);


                    } else {
                        DialogInterfacecustom.loginResponceDialog(PendingTicketActivity.this, obj.getString("message").toString(), "");


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
            return list.size();
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
       final      TextView tv1, tv2,tv3,tv4,tv5,tv6;
            LayoutInflater inflater;
            inflater = LayoutInflater.from(PendingTicketActivity.this);
            View row1 = inflater.inflate(R.layout.record_list, parent, false);

            tv1=(TextView)row1.findViewById(R.id.ticketNumberTxt);
            tv2=(TextView)row1.findViewById(R.id.originedateTxt);
            tv3=(TextView)row1.findViewById(R.id.resolvedateTxt);
            tv4=(TextView)row1.findViewById(R.id.ticketsummery);
            tv5=(TextView)row1.findViewById(R.id.phonenoTxt);
            tv6=(TextView)row1.findViewById(R.id.zoneTxt);
            tv1.setText(list.get(position).getTicket_no());
            tv2.setText(list.get(position).getOrigin_date());
            tv3.setText(list.get(position).getExp_clouser_date());
            tv4.setText(list.get(position).getTicket_summery());
            tv4.setVisibility(View.GONE);
            tv5.setText(list.get(position).getMobile());
            tv6.setText(list.get(position).getZone());
            row1.findViewById(R.id.summery).setVisibility(View.GONE);
            row1.findViewById(R.id.commentBtn).setVisibility(View.GONE);
            row1.findViewById(R.id.resendBtn).setVisibility(View.GONE);
            ((TextView) row1.findViewById(R.id.resolveTxt)).setText("Expected closure date");

            return row1;
        }

    }
}

