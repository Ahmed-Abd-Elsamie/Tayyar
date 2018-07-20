package com.example.root.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.root.myapplication.OwnerUserSetup.chat_with_details;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {



    FirebaseAuth mAuth;
    Firebase databaseReference1;
    Firebase databaseReference2;
    String uid;
    ImageButton btnSend;
    EditText txtmessage;
    LinearLayout layout;
    ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("Conversation");


        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

        databaseReference1 = new Firebase("https://sblog-6edce.firebaseio.com/messages/" + uid + "-" + chat_with_details.chatid);
        databaseReference2 = new Firebase("https://sblog-6edce.firebaseio.com/messages/" + chat_with_details.chatid + "-" + uid);





        // Assign Views
        btnSend = (ImageButton)findViewById(R.id.btn_send);
        txtmessage = (EditText)findViewById(R.id.txt_message);
        layout = (LinearLayout)findViewById(R.id.layout_message);
        scrollView = (ScrollView)findViewById(R.id.scroll);



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = txtmessage.getText().toString();
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", chat_with_details.chatid);
                    databaseReference1.push().setValue(map);
                    databaseReference2.push().setValue(map);

                }
                txtmessage.setText("");
            }


        });

// getting messages

        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String user = map.get("user").toString();

                if(user.equals(chat_with_details.chatid)){
                    addNewMessage( message, 1);
                }
                else{
                    addNewMessage( message, 2);
                }
                scrollView.fullScroll(View.FOCUS_DOWN);

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }



    public void addNewMessage(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setTextSize(18);
        textView.setLayoutParams(lp);
        textView.setPadding(8,8,8,8);


        if(type == 1) {
            textView.setBackgroundResource(R.drawable.message_reciever);
            lp.gravity = Gravity.RIGHT;
            textView.setTextColor(Color.parseColor("#000000"));

        }
        else{
            textView.setBackgroundResource(R.drawable.message_sender);
            lp.gravity = Gravity.LEFT;
            textView.setTextColor(Color.parseColor("#FFFFFF"));

        }

        layout.addView(textView);

    }


}
