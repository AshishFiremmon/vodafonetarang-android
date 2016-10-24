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
import android.text.TextUtils;
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
public class RagisterActivity extends AppCompatActivity implements IResponse {


    // UI references.
    EditText first_name,last_name,email,mobile,password,confirmpass;
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
        setContentView(R.layout.activity_register);
        handler=new Handler();
        first_name=(EditText)findViewById(R.id.fnametxt);
        last_name=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);
        confirmpass=(EditText)findViewById(R.id.confirmpass);








        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        RagisterActivity. this);

                if(_checkInternetConnection.checkInterntConnection())
                {

                    if(TextUtils.isEmpty(first_name.getText().toString())||TextUtils.isEmpty(last_name.getText().toString())||TextUtils.isEmpty(email.getText().toString())||TextUtils.isEmpty(mobile.getText().toString())||TextUtils.isEmpty(mobile.getText().toString())||TextUtils.isEmpty(confirmpass.getText().toString()))
                    {
                        Toast.makeText(RagisterActivity.this, "All Field are required", Toast.LENGTH_SHORT).show();

                    }else {

                        if(password.getText().toString().equals(confirmpass.getText().toString())) {


                            if(Validation.isMobileNoValid(mobile.getText().toString())) {
                                if(Validation.isEmailAddress(email, true))
                                {


                                    register();
                                    mProgressDialog = ProgressDialog.show(RagisterActivity.this, null,
                                            "Please Wait....", true);
                                    mProgressDialog.setCancelable(true);


                                }else
                                {
//                                mobile.setError("Enter valid mobile number");

                                    email.setText("");
                                    Toast.makeText(RagisterActivity.this, "Enter valid Email id",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }else
                            {
//                                    email.setError("Enter valid Email Id");
                             /*   email.setText("");
                                Toast.makeText(RagisterActivity.this, "Enter valid Email Id",
                                        Toast.LENGTH_SHORT).show();
*/

                                mobile.setText("");
                                Toast.makeText(RagisterActivity.this, "Enter valid Mobile No.",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }else {
                            Toast.makeText(RagisterActivity.this, "Password Mismatch...",
                                    Toast.LENGTH_SHORT).show();
                            password.setText("");
                            confirmpass.setText("");
                        }

                    }

                } else {
                    Toast.makeText(RagisterActivity.this, "Check Internet Connection",
                            Toast.LENGTH_SHORT).show();

                }


            }
        });

        findViewById(R.id.alreadylogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RagisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    public void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("email", email.getText().toString()));
                data.add(new BasicNameValuePair("first_name",  first_name.getText().toString()));
                data.add(new BasicNameValuePair("last_name", last_name.getText().toString()));
                data.add(new BasicNameValuePair("phone",  mobile.getText().toString()));

                data.add(new BasicNameValuePair("password", password.getText().toString()));


                new Web().requestPostStringData(AppUrl.registerUrl, data, RagisterActivity.this, 100);



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
                        startActivity(new Intent(RagisterActivity.this, MainActivity.class));


                        Toast.makeText(RagisterActivity.this, "Successfully Login....", Toast.LENGTH_LONG).show();


                        JSONObject jObj2=obj.getJSONObject("data");
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("LOGIN", true);
                        editor.putString("USER_ID", jObj2.getString("user_id"));
                      /*  editor.putString("MOBILE", jObj2.getString("mobile"));
                        editor.putString("EMAIL", jObj2.getString("email"));
                        editor.putString("NAME", jObj2.getString("username"));
                        editor.putString("USER_AUTH", jObj2.getString("auth_token"));
*/
                        editor.commit();

                        finish();


                    } else {
                        DialogInterfacecustom.loginResponceDialog(RagisterActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }
}

