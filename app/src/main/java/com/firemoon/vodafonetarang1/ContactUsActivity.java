package com.firemoon.vodafonetarang1;

import android.app.ProgressDialog;
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

import static com.firemoon.vodafonetarang1.R.id.emailEt;
import static com.firemoon.vodafonetarang1.R.id.massageEt;
import static com.firemoon.vodafonetarang1.R.id.mobileet;
import static com.firemoon.vodafonetarang1.R.id.nameEt;

/**
 * A login screen that offers login via email/password.
 */
public class ContactUsActivity extends AppCompatActivity implements IResponse {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText msg,nameet,emailet,mobileEt;
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
        setContentView(R.layout.contact_screen);
        handler=new Handler();
        msg=(EditText)findViewById(massageEt);
        emailet=(EditText)findViewById(emailEt);
        mobileEt=(EditText)findViewById(mobileet);
        nameet=(EditText)findViewById(nameEt);
        findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        ContactUsActivity.this);

                if(_checkInternetConnection.checkInterntConnection())
                {
                    feedback();
                    mProgressDialog = ProgressDialog.show(ContactUsActivity.this, null,
                            "Please Wait....", true);
                    mProgressDialog.setCancelable(false);

                }
                else {
                    Toast.makeText(ContactUsActivity.this, "Check Internet Connection",
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
                data.add(new BasicNameValuePair("name",nameet.getText().toString()));
                data.add(new BasicNameValuePair("email",emailet.getText().toString()));
                data.add(new BasicNameValuePair("phone",mobileEt.getText().toString()));
                data.add(new BasicNameValuePair("message",msg.getText().toString()));
                new Web().requestPostStringData(AppUrl.contactusUrl, data, ContactUsActivity.this, 100);
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


                        DialogInterfacecustom.loginResponceDialog(ContactUsActivity.this, obj.getString("message").toString(), "");


                    } else {
                        DialogInterfacecustom.loginResponceDialog(ContactUsActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

}

