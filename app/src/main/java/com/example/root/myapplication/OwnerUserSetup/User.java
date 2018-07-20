package com.example.root.myapplication.OwnerUserSetup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.myapplication.MainActivity;
import com.example.root.myapplication.Maps.PlaceAutocompleteAdapter;
import com.example.root.myapplication.Maps.models.PlaceInfo;
import com.example.root.myapplication.R;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class User extends AppCompatActivity {


    Button BtnSubmit;
    EditText txtname ,txtemail;
    RadioButton btnMale , btnFemale;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    String uid;
    int GALARY_REQUEST1 = 100;
    int GALARY_REQUEST2 = 101;

    Uri imgurl1;
    Uri imgurl2;
    String userID;

    StorageReference storageReference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();


        BtnSubmit = (Button)findViewById(R.id.btn_submit_user);

        txtname = (EditText)findViewById(R.id.txt_user_name);
        txtemail = (EditText)findViewById(R.id.txt_user_email);
        btnMale = (RadioButton)findViewById(R.id.radio_male_user);
        btnFemale = (RadioButton)findViewById(R.id.radio_female_user);


        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        storageReference = FirebaseStorage.getInstance().getReference().child("owner_imgs");





        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
            }
        });


    }




    public void AddUser(){

        pd = new ProgressDialog(this);
        pd.setMessage("Saving.....");
        pd.setCancelable(false);
        pd.show();



        if (!TextUtils.isEmpty(txtname.getText())
                && !TextUtils.isEmpty(txtemail.getText())
                && !TextUtils.isEmpty(txtemail.getText())){

            reference.child("name").setValue(txtname.getText().toString());
            reference.child("email").setValue(txtemail.getText().toString());
            reference.child("type").setValue("user");



            if (btnMale.isChecked() == true){
                reference.child("gender").setValue("male");

            }else {
                reference.child("gender").setValue("female");

            }

        }else {

            Toast.makeText(getApplicationContext()," Please Fill All Data" , Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }

          SharedPreferences mPreferences;

          mPreferences = getSharedPreferences("User", MODE_PRIVATE);
          SharedPreferences.Editor editor = mPreferences.edit();
          editor.putString("saveuserid", "user");
          editor.commit();



        final Intent y = new Intent(User.this, MainActivity.class);
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
