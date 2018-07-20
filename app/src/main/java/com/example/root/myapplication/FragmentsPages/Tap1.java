package com.example.root.myapplication.FragmentsPages;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.root.myapplication.AskOrder;
import com.example.root.myapplication.Chat;
import com.example.root.myapplication.MainActivity;
import com.example.root.myapplication.OwnerUserSetup.chat_with_details;
import com.example.root.myapplication.OwnerUserSetup.shop_item;
import com.example.root.myapplication.PhoneAuthActivity;
import com.example.root.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tap1 extends AppCompatActivity {

    Button lorder, lmarkets,laccount,Content;
    int colorClicked = Color.parseColor("#08959f");
    int color = Color.parseColor("#d1cfcf");
    int colorBlack = Color.parseColor("#000000");
    DatabaseReference referenceOrders;
    FirebaseAuth mAuth;
    String uid;
    public String temporary;
    public String temporary2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap1);
        getSupportActionBar().hide();



        laccount = (Button)findViewById(R.id.btn_account);
        lorder = (Button)findViewById(R.id.btn_order);
        lmarkets = (Button)findViewById(R.id.btn_market);




        lorder.setBackgroundColor(colorClicked);
        lorder.setTextColor(color);
        lorder.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_order_menu_white,0,0);


        // firebase

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();




////////////////////////////////
        SharedPreferences mPreferences;
        mPreferences = getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "user");

        if(temporary.equals("user")){

            Toast.makeText(getApplicationContext(),"User" , Toast.LENGTH_SHORT).show();
            referenceOrders = FirebaseDatabase.getInstance().getReference().child("userorders").child(uid);


        }else{

            Toast.makeText(getApplicationContext(),"Owner" , Toast.LENGTH_SHORT).show();
            referenceOrders = FirebaseDatabase.getInstance().getReference().child("shoporders").child(uid);

        }

////////////////////////////////



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

                startActivity(new Intent( Tap1.this,Tap2.class));
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



                startActivity(new Intent( Tap1.this,MainActivity.class));
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

                startActivity(new Intent( Tap1.this,Tap1.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        GetAllOrders();


    }



    public void GetAllOrders(){

        final ListView lv = (ListView)findViewById(R.id.list_orders);

        final List<Map<String, String>> data = new ArrayList<Map<String,String>>();





        referenceOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final Map<String, String> dataMap = new HashMap<String, String>();


                    if(temporary.equals("user")){


                        if (snapshot.hasChildren() == false){
                            // do something

                        }else {

                            String location = snapshot.child("location").getValue().toString();
                            String details = snapshot.child("details").getValue().toString();
                            String id = snapshot.child("id").getValue().toString();


                            dataMap.put("location", location);
                            dataMap.put("details", details);
                            dataMap.put("id", id);

                            data.add(dataMap);

                        }


                    }else{

                        if (snapshot.hasChildren() == false){
                            // do something

                        }else {

                            String name = snapshot.child("costumer_name").getValue().toString();
                            String location = snapshot.child("costumer_location").getValue().toString();
                            String email = snapshot.child("costumer_email").getValue().toString();
                            String details = snapshot.child("order_details").getValue().toString();
                            String phone = snapshot.child("costumer_phone").getValue().toString();
                            String id = snapshot.child("costumer_id").getValue().toString();


                            dataMap.put("costumer_name", name);
                            dataMap.put("costumer_location", location);
                            dataMap.put("costumer_email", email);
                            dataMap.put("order_details", details);
                            dataMap.put("costumer_phone", phone);
                            dataMap.put("costumer_id", id);

                            data.add(dataMap);

                        }



                    }




                }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if(temporary.equals("user")){

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                            (List<? extends Map<String, ?>>) data,
                            R.layout.item,
                            new String[] {"details", "location"} ,
                            new int[] {R.id.order_name_x, R.id.order_location_x }){

                        //overload the getChildView or any other Override methods


                    };

                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            Map<String,String> item = data.get(i);
                            final String location = item.get("location");
                            final String detl = item.get("details");
                            final String id = item.get("id");

                            View v = LayoutInflater.from(Tap1.this).inflate(R.layout.item_order_user_details,null);

                            Button btnChat = (Button) v.findViewById(R.id.btn_chat_user);
                            btnChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    chat_with_details.chatid = id;

                                    Intent intent = new Intent(Tap1.this,Chat.class);
                                    startActivity(intent);
                                    Toast.makeText(Tap1.this,id,Toast.LENGTH_SHORT).show();

                                }

                            });


                            final AlertDialog.Builder builder = new AlertDialog.Builder(Tap1.this);
                            builder.setTitle("More Details")
                                    .setMessage("Details : " + detl + "\n" + "Location : " + location + "\n\n\n" + "Delete Order?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(Tap1.this,"Order Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .setView(v)
                                    .show();



                        }
                    });


                }else{

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                            (List<? extends Map<String, ?>>) data,
                            R.layout.item_order,
                            new String[] {"costumer_name", "costumer_location","costumer_email"} ,
                            new int[] {R.id.order_name, R.id.order_location , R.id.order_date}){


                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View v = super.getView(position, convertView, parent);

                            Button btnAccept = (Button)v.findViewById(R.id.btn_accept);
                            Button btnDecline = (Button)v.findViewById(R.id.btn_decline);
                            Button btnMore = (Button)v.findViewById(R.id.btn_more);
                            Button btnChat = (Button)v.findViewById(R.id.btn_chat);

                            Map<String,String> item = data.get(position);
                            final String location = item.get("costumer_location");
                            final String number = item.get("costumer_phone");
                            final String id = item.get("costumer_id");
                            final String details = item.get("order_details");



                            btnAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    Toast.makeText(Tap1.this,"Order Accepted Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });

                            btnDecline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    Toast.makeText(Tap1.this,"Order Declined Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });

                            btnChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {

                                    chat_with_details.chatid = id;

                                    Intent intent = new Intent(Tap1.this,Chat.class);
                                    startActivity(intent);
                                    Toast.makeText(Tap1.this,id,Toast.LENGTH_SHORT).show();


                                }
                            });

                            btnMore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Tap1.this);
                                    builder.setTitle("More Details")
                                            .setMessage("Phone : " + number + "\n" + "location : " + location + "\n\n" + "Details : " + details)
                                            .show();

                                }
                            });



                            return v;
                        }


                        //overload the getChildView or any other Override methods
                    };

                    lv.setAdapter(adapter);



                }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }


}
