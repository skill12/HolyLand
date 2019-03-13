package com.kjh85skill12.holyland;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatingActivity extends AppCompatActivity {

    ListView listView;

    EditText etmsg;

    ArrayList<MessageItem> messageItems = new ArrayList<>();

    ChatAdapter chatAdapter;

    DatabaseReference chatRef;

    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);

        startTime = Calendar.getInstance().getTimeInMillis();

        getSupportActionBar().setTitle(G.chatName);

        listView = findViewById(R.id.listview);

        chatAdapter = new ChatAdapter(messageItems,getLayoutInflater(),this);
        listView.setAdapter(chatAdapter);

        etmsg = findViewById(R.id.et);

        chatRef = FirebaseDatabase.getInstance().getReference("chat");

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);

                if(messageItem.getTimeMillis()<startTime) return;

                messageItems.add(messageItem);

                chatAdapter.notifyDataSetChanged();

                listView.setSelection(messageItems.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickSend(View view) {

        String name = G.chatName;
        String msg = etmsg.getText().toString();
        String profileUrl = G.chatImgUrl;

        Calendar calendar = Calendar.getInstance();
        String time = calendar.get(Calendar.HOUR_OF_DAY)+" : "+calendar.get(Calendar.MINUTE);

        MessageItem messageItem = new MessageItem(name,msg,time,profileUrl,calendar.getTimeInMillis());

        chatRef.push().setValue(messageItem);
        etmsg.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    public void clickLogOut(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그아웃 하시겠습니까?");
        builder.setMessage("확인버튼을 누르시면\n채팅이 종료되며 다음 실행시\n자동으로 로그인되지 않습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                G.nextLogin = true;
                Intent intent = new Intent(ChatingActivity.this,ChatLogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("취소",null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
