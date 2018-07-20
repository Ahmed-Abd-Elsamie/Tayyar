package com.example.root.myapplication.FragmentsPages;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.root.myapplication.MainActivity;
import com.example.root.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tap2 extends AppCompatActivity {

    Button lorder, lmarkets,laccount,Content;
    EditText txtName;
    EditText txtEmail;
    EditText txtPhone;
    CircleImageView BtnChangeImg;
    boolean check = true;
    Button BtnEdit;




    int colorClicked = Color.parseColor("#08959f");
    int color = Color.parseColor("#d1cfcf");
    int colorBlack = Color.parseColor("#000000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap2);
        getSupportActionBar().hide();



        laccount = (Button)findViewById(R.id.btn_account);
        lorder = (Button)findViewById(R.id.btn_order);
        lmarkets = (Button)findViewById(R.id.btn_market);
        txtName = (EditText)findViewById(R.id.txt_edit_name);
        txtEmail = (EditText)findViewById(R.id.txt_edit_email);
        txtPhone = (EditText)findViewById(R.id.txt_edit_number);
        BtnChangeImg = (CircleImageView)findViewById(R.id.btn_change_img);
        BtnEdit = (Button)findViewById(R.id.btn_edit);


        //////////////////////Set InActive//////////////////////////////
        txtName.setEnabled(false);
        txtPhone.setEnabled(false);
        txtEmail.setEnabled(false);
        BtnChangeImg.setEnabled(false);
        check = true;






        laccount.setBackgroundColor(colorClicked);
        laccount.setTextColor(color);
        laccount.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_account_white,0,0);





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

                startActivity(new Intent( Tap2.this,Tap2.class));
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

                startActivity(new Intent( Tap2.this,MainActivity.class));
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

                startActivity(new Intent( Tap2.this,Tap1.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();




            }
        });

        BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(check == true){

                    txtName.setEnabled(true);
                    txtPhone.setEnabled(true);
                    txtEmail.setEnabled(true);
                    BtnChangeImg.setEnabled(true);
                    BtnEdit.setText("Save");
                    BtnEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save,0);
                    check = false;

                }else {

                    txtName.setEnabled(false);
                    txtPhone.setEnabled(false);
                    txtEmail.setEnabled(false);
                    BtnChangeImg.setEnabled(false);
                    BtnEdit.setText("Edit");
                    BtnEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit,0,0,0);
                    check = true;

                }
            }
        });
    }



    public void GettingProfileData(){

        EditText txtName = (EditText)findViewById(R.id.txt_edit_name);
        EditText txtEmail = (EditText)findViewById(R.id.txt_edit_email);
        EditText txtPhone = (EditText)findViewById(R.id.txt_edit_number);
        CircleImageView img = (CircleImageView)findViewById(R.id.btn_change_img);
        RadioButton radMale = (RadioButton)findViewById(R.id.radio_male_profile);
        RadioButton radFemale = (RadioButton)findViewById(R.id.radio_male_profile);




    }



}
