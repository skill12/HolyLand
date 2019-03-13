package com.kjh85skill12.holyland;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatLogInActivity extends AppCompatActivity {

    EditText etName;
    ImageView ivProfile;

    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log_in);

        etName = findViewById(R.id.et_name);
        ivProfile = findViewById(R.id.iv_profile);
        if(!G.nextLogin) {
            loadChatData();
            if (G.isLogin) {
                Intent intent = new Intent(this, ChatingActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void clickImage(View view) {
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode ){
            case 10:
                if(resultCode==RESULT_OK){
                    imgUri= data.getData();
                    Picasso.with(this).load(imgUri).into(ivProfile);
                }
                break;
        }
    }

    public void clickBtn(View view) {
        G.chatName = etName.getText().toString();
        G.nextLogin=false;
        saveChatData();
    }

    void saveChatData(){

        if(etName.getText().length() <1) return;

        G.isLogin = true;
        if(imgUri == null){
            Uri uri = Uri.parse("android.resource://com.kjh85skill12.holyland/drawable/"+R.drawable.noimg);
            imgUri = uri;
        }

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName = sdf.format(new Date())+".png";

        final StorageReference imgRef = firebaseStorage.getReference("profileImgs"+fileName);

        UploadTask uploadTask = imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        G.chatImgUrl = uri.toString();
                        Toast.makeText(ChatLogInActivity.this, "프로필 이미지 저장 완료", Toast.LENGTH_SHORT).show();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference profilesRef = firebaseDatabase.getReference("profiles");
                        profilesRef.child(G.chatName).setValue(G.chatImgUrl);

                        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("NickName",G.chatName);
                        editor.putString("ProfileUrl",G.chatImgUrl);
                        editor.putBoolean("Login",G.isLogin);

                        editor.commit();

                        Intent intent = new Intent(ChatLogInActivity.this,ChatingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    void loadChatData(){
        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
        G.chatName = pref.getString("NickName",null);
        G.chatImgUrl = pref.getString("ProfileUrl",null);
        G.isLogin = pref.getBoolean("Login",false);
    }
}
