package com.example.root.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.myapplication.FragmentsPages.Tap1;
import com.example.root.myapplication.FragmentsPages.Tap2;
import com.example.root.myapplication.Maps.Maps;
import com.example.root.myapplication.OwnerUserSetup.chat_with_details;
import com.example.root.myapplication.OwnerUserSetup.shop_item;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public String temporary;
    public String temporary2;

    public String userid;
    public static int cx,cy ;
    private FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;
    private int MODE_PRIVATE;

    ImageButton btnOrder;
    ImageButton btnMarket;
    ImageButton btnAccount;
    Button lorder, lmarkets,laccount,Content;
    int colorClicked = Color.parseColor("#08959f");
    int color = Color.parseColor("#d1cfcf");
    int colorBlack = Color.parseColor("#000000");

    ImageButton BtnMaps;

    RelativeLayout relativeLayout;
    ArrayAdapter arrayAdapter;
    ImageView shopImg;
    TextView txtShopName;
    String uid;
    TextView commentState;



    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startauthentication();







    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }





    public void startauthentication(){

        SharedPreferences mPreferences;
        mPreferences = getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");

        if(temporary.equals("owner")){

            ///////////////////Start As A Owner////////////////////////////////////


            Toast.makeText(getApplicationContext(),"Owner" , Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_main);

            getSupportActionBar().hide();


            SharedPreferences mmPreferences;
            mmPreferences = MainActivity.this.getSharedPreferences("User", MODE_PRIVATE);

            temporary = mmPreferences.getString("saveuserid", "");

            if(temporary!= null && !temporary.isEmpty()) {

                mAuth = FirebaseAuth.getInstance();
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                userid = currentFirebaseUser.getUid();


            } else
            {


            }

            uid = mAuth.getCurrentUser().getUid().toString();


            View v = (View)findViewById(R.id.in);
            View v2 = (View)findViewById(R.id.in2);
            v.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.VISIBLE);



            // Assign Views

            laccount = (Button)findViewById(R.id.btn_account);
            lorder = (Button)findViewById(R.id.btn_order);
            lmarkets = (Button)findViewById(R.id.btn_market);
            BtnMaps = (ImageButton)findViewById(R.id.btn_maps);






            // initalizing Firebase utils


            //mAuth = FirebaseAuth.getInstance();
            //uid = mAuth.getCurrentUser().getUid().toString();
            reference = FirebaseDatabase.getInstance().getReference().child("users");

            GetMyShopData();

            hideKeyboard(this);




            BtnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isServicesOK()){
                        startActivity(new Intent(MainActivity.this,Maps.class));
                    }


                }
            });






            lmarkets.setBackgroundColor(colorClicked);
            lmarkets.setTextColor(color);
            lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_white,0,0);






            laccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(color);
                    laccount.setBackgroundColor(colorClicked);
                    lorder.setBackgroundColor(color);

                    lmarkets.setTextColor(colorBlack);
                    lorder.setTextColor(colorBlack);
                    laccount.setTextColor(color);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_black,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_white,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu,0,0);

                    startActivity(new Intent( MainActivity.this,Tap2.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();



                }
            });




            lmarkets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(colorClicked);
                    laccount.setBackgroundColor(color);
                    lorder.setBackgroundColor(color);

                    lmarkets.setTextColor(color);
                    lorder.setTextColor(colorBlack);
                    laccount.setTextColor(colorBlack);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_white,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_black,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu,0,0);

                    startActivity(new Intent( MainActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    finish();


                }
            });




            lorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(color);
                    laccount.setBackgroundColor(color);
                    lorder.setBackgroundColor(colorClicked);

                    lmarkets.setTextColor(colorBlack);
                    lorder.setTextColor(color);
                    laccount.setTextColor(colorBlack);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_black,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_black,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu_white,0,0);

                    startActivity(new Intent( MainActivity.this,Tap1.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    finish();




                }
            });





            ///////////////////Start As A User////////////////////////////////////


        }else if (temporary.equals("user")){

            Toast.makeText(getApplicationContext(),"User" , Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);

            //GetAllShops();

            setContentView(R.layout.activity_main);
            getSupportActionBar().hide();

            SharedPreferences mmPreferences;
            mmPreferences = MainActivity.this.getSharedPreferences("User", MODE_PRIVATE);

            temporary = mmPreferences.getString("saveuserid", "");

            if(temporary!= null && !temporary.isEmpty()) {

                mAuth = FirebaseAuth.getInstance();
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                userid = currentFirebaseUser.getUid();


            } else
            {


            }




            // Assign Views

            laccount = (Button)findViewById(R.id.btn_account);
            lorder = (Button)findViewById(R.id.btn_order);
            lmarkets = (Button)findViewById(R.id.btn_market);
            BtnMaps = (ImageButton)findViewById(R.id.btn_maps);






            // initalizing Firebase utils


            //mAuth = FirebaseAuth.getInstance();
            //uid = mAuth.getCurrentUser().getUid().toString();
            reference = FirebaseDatabase.getInstance().getReference().child("users");
            uid = mAuth.getCurrentUser().getUid().toString();

            GetAllShops();

            hideKeyboard(this);


            BtnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isServicesOK()){
                        startActivity(new Intent(MainActivity.this,Maps.class));
                    }


                }
            });






            lmarkets.setBackgroundColor(colorClicked);
            lmarkets.setTextColor(color);
            lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_white,0,0);






            laccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(color);
                    laccount.setBackgroundColor(colorClicked);
                    lorder.setBackgroundColor(color);

                    lmarkets.setTextColor(colorBlack);
                    lorder.setTextColor(colorBlack);
                    laccount.setTextColor(color);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_black,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_white,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu,0,0);

                    startActivity(new Intent( MainActivity.this,Tap2.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();



                }
            });




            lmarkets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(colorClicked);
                    laccount.setBackgroundColor(color);
                    lorder.setBackgroundColor(color);

                    lmarkets.setTextColor(color);
                    lorder.setTextColor(colorBlack);
                    laccount.setTextColor(colorBlack);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_white,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_black,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu,0,0);

                    startActivity(new Intent( MainActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    finish();


                }
            });




            lorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lmarkets.setBackgroundColor(color);
                    laccount.setBackgroundColor(color);
                    lorder.setBackgroundColor(colorClicked);

                    lmarkets.setTextColor(colorBlack);
                    lorder.setTextColor(color);
                    laccount.setTextColor(colorBlack);

                    lmarkets.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_market_black,0,0);
                    laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_black,0,0);
                    lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu_white,0,0);

                    startActivity(new Intent( MainActivity.this,Tap1.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    finish();




                }
            });



        }else{

            Intent y = new Intent(MainActivity.this, PhoneAuthActivity.class);
            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(y);

        }

    }


    public void GetAllShops(){

        final ListView lv = (ListView)findViewById(R.id.list_markets);

        final List<Map<String, String>> data = new ArrayList<Map<String,String>>();





        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final Map<String, String> dataMap = new HashMap<String, String>();

                    if (!snapshot.hasChild("img1")){
                        //continue;
                    }else {
                        String v1 = snapshot.child("shop_name").getValue().toString();
                        String v2 = snapshot.child("shop_location").getValue().toString();
                        String id = snapshot.child("id").getValue().toString();

                        dataMap.put("shop_name", v1); //icon
                        dataMap.put("shop_location", v2);
                        dataMap.put("id", id);

                        data.add(dataMap);

                    }


                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        (List<? extends Map<String, ?>>) data,
                        R.layout.favorit_list_item,
                        new String[] {"shop_name", "shop_location"} ,
                        new int[] {R.id.txt_shop_name, R.id.txt_shop_desc}){

                    //overload the getChildView or any other Override methods
                };

                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Map<String,String> itemShop = data.get(i);
                String name = itemShop.get("shop_name");
                String id = itemShop.get("id");

                shop_item.shop_name = name;
                shop_item.shop_id = id;


                Intent intent = new Intent(MainActivity.this,AskOrder.class);
                startActivity(intent);

            }
        });





    }

    public void GetMyShopData(){

        shopImg = (ImageView)findViewById(R.id.shop_img);
        txtShopName = (TextView)findViewById(R.id.shop_name_owner);
        commentState = (TextView)findViewById(R.id.txt_no_comment);

        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String shop = dataSnapshot.child("shop_name").getValue(String.class);
                String imgurl = dataSnapshot.child("img1").getValue(String.class);

                Picasso.with(MainActivity.this).load(imgurl).into(shopImg);
                txtShopName.setText(shop);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        GetComments();


    }

    private void GetComments() {

        //     Reading Comments
        //commentState = (TextView)findViewById(R.id.txt_no_comment);

        final DatabaseReference referenceComments = FirebaseDatabase.getInstance().getReference().child("comments");


        referenceComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChild(uid)){


                    referenceComments.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                                String name = snapshot.child("userName").getValue().toString();
                                String comment = snapshot.child("comment").getValue().toString();
                                String rat = snapshot.child("rat").getValue().toString();

                                AddCommentBox(comment , name , rat);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                } else {

                    commentState.setText("No Comments for your Shop");

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }





    public void AddCommentBox(String usercomment , String username , String rat) {
        LinearLayout ListOfComments;
        ListOfComments = (LinearLayout)findViewById(R.id.list_comment);


        TextView textView = new TextView(MainActivity.this);
        LinearLayout container = new LinearLayout(MainActivity.this);
        RatingBar ratingBar = new RatingBar(MainActivity.this);



        container.setBackgroundResource(R.drawable.comment_box);
        textView.setText(username);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, 0, 0, 10);

        textView.setTextSize(18);
        container.setLayoutParams(lp2);
        textView.setLayoutParams(lp);
        textView.setPadding(8,8,8,8);
        textView.setTextColor(Color.parseColor("#000000"));
        container.setGravity(Gravity.CENTER);
        container.setOrientation(LinearLayout.VERTICAL);
        ratingBar.setNumStars(5);
        ratingBar.setLayoutParams(lp);
        ratingBar.setRating(5);
        ratingBar.setEnabled(false);
        ratingBar.setRating(Float.parseFloat(rat));


        // adding second text

        TextView textView2 = new TextView(MainActivity.this);
        textView2.setText(usercomment);
        textView2.setTextSize(18);
        textView2.setLayoutParams(lp);
        textView2.setPadding(8,8,8,8);

        //textView.setBackgroundResource(R.drawable.message_reciever);
        lp.gravity = Gravity.LEFT;
        textView2.setTextColor(Color.parseColor("#000000"));



        container.addView(textView);
        container.addView(ratingBar);
        container.addView(textView2);

        ListOfComments.addView(container);


    }




    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}