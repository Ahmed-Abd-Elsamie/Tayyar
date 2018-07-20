package com.example.root.myapplication;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.myapplication.FragmentsPages.Tap1;
import com.example.root.myapplication.OwnerUserSetup.Owner;
import com.example.root.myapplication.OwnerUserSetup.chat_with_details;
import com.example.root.myapplication.OwnerUserSetup.shop_item;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AskOrder extends AppCompatActivity {


    Button BtnAsk;
    LinearLayout ListOfComments;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    DatabaseReference reference , referenceOrders , referenceShopOrders ,referenceUser , referenceallusers;
    TextView txtDistance , commentState , txtType;
    String uid;
    private static final int PLACE_PICKER_REQUEST = 1;
    Place place;
    TextView txtMaplocation , shopDescription;
    String OrderDetails;
    String namex;
    String emailx;
    String phonex;
    String mapLoc = ".";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    ImageButton BtnComment;
    String n;
    ImageView ShopImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_order);


        BtnAsk = (Button)findViewById(R.id.btn_ask_order);
        ListOfComments = (LinearLayout)findViewById(R.id.list_comment);
        progressBar = (ProgressBar)findViewById(R.id.prog);
        txtDistance = (TextView)findViewById(R.id.txt_distance);
        commentState = (TextView)findViewById(R.id.txt_no_comment);
        shopDescription = (TextView)findViewById(R.id.txt_desc);
        txtType = (TextView) findViewById(R.id.type);
        BtnComment = (ImageButton)findViewById(R.id.new_comment);
        ShopImg = (ImageView)findViewById(R.id.imageViewMarket);



        // start firebase

        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();


        reference = FirebaseDatabase.getInstance().getReference().child("comments").child(shop_item.shop_id);
        referenceOrders = FirebaseDatabase.getInstance().getReference().child("userorders").child(uid);
        referenceShopOrders = FirebaseDatabase.getInstance().getReference().child("shoporders");
        referenceUser = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        referenceallusers = FirebaseDatabase.getInstance().getReference().child("users");


        Toast.makeText(getApplicationContext(),shop_item.shop_id , Toast.LENGTH_SHORT).show();



        BtnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDialog();

            }
        });

        BtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddnewComment();

            }
        });








    }

    private void AddnewComment() {

        View view = LayoutInflater.from(AskOrder.this).inflate(R.layout.add_comment,null);

        final EditText txtComment = (EditText)view.findViewById(R.id.txt_new_comment);
        final RatingBar ratingBar = (RatingBar)view.findViewById(R.id.rat);

        final Firebase firebase = new Firebase("https://sblog-6edce.firebaseio.com/comments/" + shop_item.shop_id);



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("New Comment")
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        if (!TextUtils.isEmpty(txtComment.getText())){

                            Map<String, String> map = new HashMap<String, String>();
                            map.put("comment", txtComment.getText().toString());
                            map.put("userName", n);
                            map.put("rat" , String.valueOf(ratingBar.getRating()));
                            firebase.push().setValue(map);

                        }else {
                            return;
                        }



                    }

                })
                .show();

    }


    @Override
    protected void onStart() {
        super.onStart();



        // getting my data


        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //getting my data
                n = dataSnapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        referenceallusers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String desc = dataSnapshot.child(shop_item.shop_id).child("description").getValue().toString();
                String type = dataSnapshot.child(shop_item.shop_id).child("shop_type").getValue().toString();
                String img = dataSnapshot.child(shop_item.shop_id).child("img2").getValue().toString();

                shopDescription.setText(desc);
                txtType.setText(type);
                if (img.equals("default")){
                    ShopImg.setImageResource(R.drawable.market_show);
                }else {
                    Picasso.with(AskOrder.this).load(img).into(ShopImg);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //     Reading Comments



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChildren() == false){
                    commentState.setText("No Comments for this Shop");
                }
                else {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        String name = snapshot.child("userName").getValue().toString();
                        String comment = snapshot.child("comment").getValue().toString();
                        String rat = snapshot.child("rat").getValue().toString();

                        AddCommentBox(comment , name , rat);

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void AddCommentBox(String usercomment , String username , String rat) {

            TextView textView = new TextView(AskOrder.this);
            LinearLayout container = new LinearLayout(AskOrder.this);
            RatingBar ratingBar = new RatingBar(AskOrder.this);



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

           TextView textView2 = new TextView(AskOrder.this);
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {

                place = PlacePicker.getPlace(this, data);
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                mapLoc = place.getAddress().toString();
                txtMaplocation.setText(mapLoc);


            }

        }
    }



    public void ShowDialog(){

        LayoutInflater inflater = getLayoutInflater();
        //View view = inflater.inflate(R.layout.order_details,null);
        View view = LayoutInflater.from(AskOrder.this).inflate(R.layout.order_details,null);

        final RadioButton mapLocation = (RadioButton) view.findViewById(R.id.radio_map_location);
        txtMaplocation = (TextView) view.findViewById(R.id.txt_map_location);
        final EditText txtOrder = (EditText) view.findViewById(R.id.txt_order_details);


        mapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AskOrder.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });




        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("New Order")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {





                        if (TextUtils.isEmpty(txtOrder.getText())){
                            Toast.makeText(getApplicationContext(),"Please add an Order Describtion",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        OrderDetails = txtOrder.getText().toString();


                        DatabaseReference order = referenceOrders.push();
                        order.child("location").setValue(txtMaplocation.getText().toString());
                        order.child("details").setValue(OrderDetails);
                        order.child("id").setValue(shop_item.shop_id);

                        // adding to shops orders

                        referenceUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //getting my data

                                namex = dataSnapshot.child("name").getValue(String.class);
                                phonex = dataSnapshot.child("contact").getValue(String.class);
                                emailx = dataSnapshot.child("email").getValue(String.class);

                               /*
                                DatabaseReference r = referenceShopOrders.child(shop_item.shop_id).push();

                                r.child("order_details").setValue(OrderDetails);
                                r.child("costumer_location").setValue(mapLoc);
                                r.child("costumer_name").setValue(namex);
                                r.child("costumer_phone").setValue(phonex);
                                r.child("costumer_email").setValue(emailx);
                                r.child("costumer_id").setValue(uid);

                                */

                                Firebase firebase = new Firebase("https://sblog-6edce.firebaseio.com/shoporders/");
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("order_details", OrderDetails);
                                map.put("costumer_location", mapLoc);
                                map.put("costumer_name", namex);
                                map.put("costumer_phone", phonex);
                                map.put("costumer_email", emailx);
                                map.put("costumer_id", uid);

                                firebase.push().setValue(map);



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        // adding to shops orders









                    }
                })
                .show();





    }



    private void getLocationPermission(){
       // Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                //initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



}
