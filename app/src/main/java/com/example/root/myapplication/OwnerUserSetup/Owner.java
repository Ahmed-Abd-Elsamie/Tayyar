package com.example.root.myapplication.OwnerUserSetup;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.myapplication.MainActivity;
import com.example.root.myapplication.Maps.Maps;
import com.example.root.myapplication.Maps.PlaceAutocompleteAdapter;
import com.example.root.myapplication.Maps.models.PlaceInfo;
import com.example.root.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Owner extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {


    FirebaseAuth mAuth;
    DatabaseReference reference;
    String uid;
    int GALARY_REQUEST1 = 100;
    int GALARY_REQUEST2 = 101;

    Uri imgurl1;
    Uri imgurl2;

    StorageReference storageReference;
    ProgressDialog pd;


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }


    Button BtnSubmit;
    Button BtnLocation;
    ImageButton Btnimg1 , Btnimg2;
    Spinner spinner;
    EditText txtname ,txtemail , txtphone , txtmarketname;
    RadioButton btnMale , btnFemale;
    private static final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    TextView txtPlace;
    Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        getSupportActionBar().hide();


        spinner = (Spinner)findViewById(R.id.spinner_shop_type);
        txtname = (EditText)findViewById(R.id.txt_full_name);
        txtemail = (EditText)findViewById(R.id.txt_full_email);
        txtphone = (EditText)findViewById(R.id.txt_full_number);
        txtmarketname = (EditText)findViewById(R.id.txt_market_name);
        txtPlace = (TextView)findViewById(R.id.txt_marketplace);


        BtnSubmit = (Button)findViewById(R.id.btn_submit);
        btnMale = (RadioButton)findViewById(R.id.radio_male);
        btnFemale = (RadioButton)findViewById(R.id.radio_female);
        BtnLocation = (Button)findViewById(R.id.btn_select_location);
        Btnimg1 = (ImageButton)findViewById(R.id.market_img1);
        Btnimg2 = (ImageButton)findViewById(R.id.market_img2);


        // Firebase Utils

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        storageReference = FirebaseStorage.getInstance().getReference().child("owner_imgs");





        BtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(Owner.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });

        Btnimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALARY_REQUEST1);

            }
        });


        Btnimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALARY_REQUEST2);


            }
        });




        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddOwner();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST){

            if (resultCode == RESULT_OK){

                place = PlacePicker.getPlace(this,data);
                Toast.makeText(getApplicationContext(),place.getName(),Toast.LENGTH_SHORT).show();
                txtPlace.setText(place.getAddress());



            }
        }else if(resultCode == Activity.RESULT_OK && requestCode == GALARY_REQUEST1){
            imgurl1 = data.getData();
        }else if(resultCode == Activity.RESULT_OK && requestCode == GALARY_REQUEST2){
            imgurl2 = data.getData();
        }
    }



    public void AddOwner(){

        pd = new ProgressDialog(this);
        pd.setMessage("Saving.....");
        pd.setCancelable(false);
        pd.show();



        if (!TextUtils.isEmpty(txtname.getText())
                && !TextUtils.isEmpty(txtemail.getText())
                && !TextUtils.isEmpty(txtmarketname.getText())
                && !TextUtils.isEmpty(txtphone.getText())
                && !TextUtils.isEmpty(txtPlace.getText()) ){

            reference.child("name").setValue(txtname.getText().toString());
            reference.child("email").setValue(txtemail.getText().toString());
            reference.child("shop_type").setValue(spinner.getSelectedItem().toString());
            reference.child("type").setValue("owner");
            reference.child("shop_name").setValue(txtmarketname.getText().toString());
            reference.child("shop_location").setValue(place.getAddress().toString());
            reference.child("id").setValue(uid);
            reference.child("description").setValue("default");



            if (btnMale.isChecked() == true){
                reference.child("gender").setValue("male");

            }else {
                reference.child("gender").setValue("female");

            }

        }else {

            Toast.makeText(getApplicationContext()," Please Fill All Data" , Toast.LENGTH_SHORT).show();
            return;
        }




        if (imgurl1 != null && imgurl2 != null) {


            // Adding first image
            StorageReference file_path = storageReference.child(imgurl1.getLastPathSegment());
            file_path.putFile(imgurl1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri = taskSnapshot.getDownloadUrl().toString();
                    reference.child("img1").setValue(downloadUri);

                }
            });

            // Adding second image

            StorageReference file_path2 = storageReference.child(imgurl2.getLastPathSegment());
            file_path2.putFile(imgurl2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri = taskSnapshot.getDownloadUrl().toString();
                    reference.child("img2").setValue(downloadUri);


                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    pd.dismiss();

                    Intent y = new Intent(Owner.this, MainActivity.class);
                    y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    SharedPreferences mPreferences;

                    mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("saveuserid", "owner");
                    editor.commit();


                    startActivity(y);
                }
            });


        }else{
            reference.child("img1").setValue("default");
            reference.child("img2").setValue("default");

            pd.dismiss();



            SharedPreferences mPreferences;

            mPreferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("saveuserid", "owner");
            editor.commit();


            final Intent y = new Intent(Owner.this, MainActivity.class);
            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            /// delay

            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {

                    startActivity(y);

                    pd.dismiss();

                }
            }.start();

        }


    }



}