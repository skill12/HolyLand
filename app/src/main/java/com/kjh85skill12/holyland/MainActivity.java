package com.kjh85skill12.holyland;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            if(networkInfo.getType()== ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "WI-FI 환경으로 연결됩니다.", Toast.LENGTH_SHORT).show();
            }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this, "DataNetwork 환경으로 연결됩니다.", Toast.LENGTH_SHORT).show();
            }
        }else{
            AlertDialog builder = new AlertDialog.Builder(this).setMessage("네트워크 연결을 찾을수 없습니다.\n앱을 종료합니다.").setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create();
            builder.show();
        }

        loadData();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }

        if(!G.isToken){
            // Get token
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG!!", "getInstanceId failed", task.getException());
                                return;
                            }else{
                                // Get new Instance ID token
                                token = task.getResult().getToken();

                                String serverUrl = "http://skill12.dothome.co.kr/HolyLand/tokenInsert.php";

                                SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        new AlertDialog.Builder(MainActivity.this).setMessage("토큰등록 성공!!\n 공지사항 알림을 받도록 설정되었습니다.").show();
                                        G.isToken = true;
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                multiPartRequest.addStringParam("token",token);

                                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                                requestQueue.add(multiPartRequest);

                            }


                        }
                    });
            /* [END retrieve_current_token] */
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 100:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "외부저장소를 사용할수 없습니다.\n게시판 이미지 업로드가 제한 됩니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void click_start(View view) {

        Intent intent = new Intent(this,SecondActivity.class);

        startActivity(intent);

        finish();
    }

    void loadData(){

        SharedPreferences pref = getSharedPreferences("saveData",MODE_PRIVATE);

        G.lastName = pref.getString("Name",null);
        G.lastSelfi = pref.getString("Selfi",null);
        G.isBgm = pref.getBoolean("Bgm",true);
        G.isToken = pref.getBoolean("Token", false);
        G.tmpLastSelfi = pref.getString("TmpSelfi",null);
    }

}
