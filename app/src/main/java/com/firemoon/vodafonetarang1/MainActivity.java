package com.firemoon.vodafonetarang1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import Util.AppUrl;

public class MainActivity extends AppCompatActivity
        {
    DrawerLayout drawer;
    LinearLayout viewlyt ,feedbackLyt,contactLyt;
            TextView viewTicket;
            SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppUrl.location=false;
           drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
   final     View headerLayout = navigationView.getHeaderView(0);
         sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        if(sharedpreferences.getString("ADMIN","").equals("3"))
        {
            headerLayout.findViewById(R.id.feedbackLyt).setVisibility(View.VISIBLE);
            findViewById(R.id.adminBtn).setVisibility(View.VISIBLE);

        }

        viewTicket=(TextView)  headerLayout.findViewById(R.id.viewTicket);
        viewlyt=(LinearLayout)headerLayout.findViewById(R.id.ticketLyt);
        feedbackLyt=(LinearLayout)headerLayout.findViewById(R.id.feedbackLyt);
        contactLyt=(LinearLayout)headerLayout.findViewById(R.id.contactLyt);

        headerLayout.findViewById(R.id.viewTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(viewlyt.getVisibility()==View.VISIBLE)
{
   viewlyt.setVisibility(View.GONE);
    contactLyt.setVisibility(View.VISIBLE);

    headerLayout.findViewById(R.id.raiseLyt).setVisibility(View.VISIBLE);
    viewTicket.setTypeface(null, Typeface.NORMAL);
    feedbackLyt.setVisibility(View.GONE);
    if(sharedpreferences.getString("ADMIN","").equals("3"))
    {
        headerLayout.findViewById(R.id.feedbackLyt).setVisibility(View.VISIBLE);

    }


} else {
    contactLyt.setVisibility(View.GONE);
    viewlyt.setVisibility(View.VISIBLE);
    headerLayout.findViewById(R.id.raiseLyt).setVisibility(View.GONE);
    viewTicket.setTypeface(null, Typeface.BOLD);
    feedbackLyt.setVisibility(View.GONE);
}

            }
        });

        headerLayout.findViewById(R.id.raiseTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

           startActivity(new Intent(MainActivity.this,FormActivity.class));

            }
        });
        headerLayout.findViewById(R.id.contactTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));

            }
        });
        headerLayout.findViewById(R.id.pendingTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

                startActivity(new Intent(MainActivity.this, PendingTicketActivity.class));

            }
        });
        headerLayout.findViewById(R.id.resolvedTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

                startActivity(new Intent(MainActivity.this, ResolveActivity.class));

            }
        });
        headerLayout.findViewById(R.id.sendTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

                startActivity(new Intent(MainActivity.this, SendActivity.class));

            }
        });
        headerLayout.findViewById(R.id.outboxTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, TicketInOutboxActivity.class));


            }
        });

        findViewById(R.id.raiseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, FormActivity.class));

            }
        });


        headerLayout.findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
//                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nutrashopy.biz/vodafone"));
                startActivity(browserIntent);

            }
        });


        findViewById(R.id.adminBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nutrashopy.biz/vodafone"));
                startActivity(browserIntent);

            }
        });
        findViewById(R.id.viewBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Pending Ticket", "Resolved Ticket", "Send Ticket","Ticket In Outbox"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AppCompatAlertDialogStyle);
//                builder.setTitle("Select Sub Issue");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
//                        items.setText(items.get(item));
                        if(item==0)
                        {
                            startActivity(new Intent(MainActivity.this, PendingTicketActivity.class));

                        }else   if(item==1)
                        {
                            startActivity(new Intent(MainActivity.this, ResolveActivity.class));

                        }else   if(item==2)
                        {
                            startActivity(new Intent(MainActivity.this, SendActivity.class));

                        }else   if(item==3)
                        {
                            startActivity(new Intent(MainActivity.this, TicketInOutboxActivity.class));

                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            /*    //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this,  findViewById(R.id.viewBtn));
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                MainActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu*/
            }
        });




//        DatabaseHandler     db = new DatabaseHandler(this);

//     db.clearTable();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        alertDialogBuilder.setMessage("Do you want to use your current location");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
AppUrl.currentLocation=true;
                GPSTracker tracker = new GPSTracker(MainActivity.this);
                if (!tracker.canGetLocation()) {
                    showSettingsAlert();
                } else {

                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



    }

      /*      public LatLng getLocationFromAddress(Context context,String strAddress) {

                Geocoder coder = new Geocoder(context);
                List<Address> address;
                LatLng p1 = null;

                try {
                    address = coder.getFromLocationName(strAddress, 5);
                    if (address == null) {
                        return null;
                    }
                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();
                    AppLog.logPrint(location.getLatitude() + "      " + location.getLongitude());

                    p1 = new LatLng(location.getLatitude(), location.getLongitude() );

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

                return p1;
            }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
            public void showSettingsAlert(){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                // Setting Dialog Title
                alertDialog.setTitle("GPS is settings");

                // Setting Dialog Message
                alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                // On pressing Settings button
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                    }
                });

                // on pressing cancel button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }


}
