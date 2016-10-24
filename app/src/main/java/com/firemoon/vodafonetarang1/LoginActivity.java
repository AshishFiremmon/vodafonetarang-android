package com.firemoon.vodafonetarang1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import Util.Validation;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements IResponse {
     ProgressDialog mProgressDialog;
    private Handler handler;


    // UI references.
    private EditText mobile,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_login);
        mobile=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Boolean yourLocked = sharedpreferences.getBoolean("LOGIN", false);
        if(yourLocked)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        handler=new Handler();



        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        LoginActivity.this);

                if(_checkInternetConnection.checkInterntConnection())
                {


                    if(Validation.isMobileNoValid(mobile.getText().toString())) {
                        if(!password.getText().toString().equalsIgnoreCase("")) {

                            login();
                            mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
                                    "Please Wait....", true);
                            mProgressDialog.setCancelable(true);

                        }else
                        {
//                            password.setError("Enter password");
                            Toast.makeText(LoginActivity.this, "Enter password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else
                    {
//                        mobile.setError("Enter valid Email id");
                        mobile.setText("");
                        Toast.makeText(LoginActivity.this, "Enter valid Mobile No.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "Check Internet Connection",
                            Toast.LENGTH_SHORT).show();

                }




            }
        });
        findViewById(R.id.registerTXT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RagisterActivity.class));
            }
        });
        findViewById(R.id.forgetpassTXT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
            }
        });

    }
    public void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                   data.add(new BasicNameValuePair("phone", mobile.getText().toString()));

                data.add(new BasicNameValuePair("password", password.getText().toString()));


                new Web().requestPostStringData(AppUrl.loginUrl, data, LoginActivity.this, 100);



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
                       /* Intent intent = new Intent(SignIn.this, BaseActivity.class);
                        startActivity(intent);
                   */
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));


                        Toast.makeText(LoginActivity.this, "Successfully Login....", Toast.LENGTH_LONG).show();


                        JSONObject jObj2=obj.getJSONObject("data");
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("LOGIN", true);
                        editor.putString("USER_ID", jObj2.getString("user_id"));

                        editor.putString("ADMIN",  jObj2.getString("user_type"));

                     /*   editor.putString("MOBILE", jObj2.getString("mobile"));
                        editor.putString("EMAIL", jObj2.getString("email"));
                        editor.putString("NAME", jObj2.getString("username"));
                        editor.putString("USER_AUTH", jObj2.getString("auth_token"));
*/
                        editor.commit();

                            finish();



                    } else {
                        DialogInterfacecustom.loginResponceDialog(LoginActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }
}

