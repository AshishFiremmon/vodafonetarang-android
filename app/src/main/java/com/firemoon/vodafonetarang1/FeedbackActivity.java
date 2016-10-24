package com.firemoon.vodafonetarang1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
public class FeedbackActivity extends AppCompatActivity implements IResponse {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText feedbackmsg;
    private View mProgressView;
    private View mLoginFormView;
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
        setContentView(R.layout.feedback_screen);
        handler=new Handler();
        feedbackmsg=(EditText)findViewById(R.id.feedbackmsg);
        findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        FeedbackActivity.this);

                if(_checkInternetConnection.checkInterntConnection())
                {
                    feedback();
                    mProgressDialog = ProgressDialog.show(FeedbackActivity.this, null,
                            "Please Wait....", true);
                    mProgressDialog.setCancelable(false);

                }
                else {
                    Toast.makeText(FeedbackActivity.this, "Check Internet Connection",
                            Toast.LENGTH_SHORT).show();

                }


            }
        });
          }
    public void feedback() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();

                SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


                data.add(new BasicNameValuePair("ticket_id",sharedpreferences.getString("TICKET_ID", "")));

                data.add(new BasicNameValuePair("ticket_feedback",feedbackmsg.getText().toString()));

                new Web().requestPostStringData(AppUrl.feedbackTicketUrl, data, FeedbackActivity.this, 100);



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


                        DialogInterfacecustom.loginResponceDialog(FeedbackActivity.this, obj.getString("message").toString(), "");


                    } else {
                        DialogInterfacecustom.loginResponceDialog(FeedbackActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

}

