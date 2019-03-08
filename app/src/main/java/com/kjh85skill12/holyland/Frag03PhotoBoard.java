package com.kjh85skill12.holyland;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Frag03PhotoBoard extends Fragment {

    ArrayList<BoardItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    BoardAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    SpeedDialView btnAdd;
    TextView tvSuper;

    CircleImageView dialogIvSelfi;
    ImageView dialogIvAddSelfi;
    TextView dialogTvAddSelfi;

    EditText dialogEtName;
    EditText dialogEtPass;
    EditText dialogEtMsg;

    ImageView dialogIvShowpic;
    ImageView dialogIvTakepic;

    String mainImgPath;
    String selfiImgPath;

    SwipeRefreshLayout layoutRefresh;
    String tmpSelfiUri;
    String noti;

    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getActivity(), uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_photoboard,container,false);

        tvSuper = view.findViewById(R.id.tv_super);
        tvSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(noti).show();
            }
        });
        loadNoti();
        btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_add_list,R.drawable.ic_create_white_24dp).setFabBackgroundColor(getResources().getColor(R.color.fab_add)).setLabel("글작성").setLabelColor(Color.BLACK).create());
        btnAdd.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()){
                    case R.id.fab_add_list:

                        AddSelfi addSelfi = new AddSelfi();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater1 = getLayoutInflater();
                        View layout = inflater1.inflate(R.layout.dialog_addlist,null);

                        dialogIvSelfi = layout.findViewById(R.id.dialog_iv_selfi);
                        if(G.lastSelfi!=null) Picasso.with(getActivity()).load(G.lastSelfi).into(dialogIvSelfi);
                        dialogIvSelfi.setOnClickListener(addSelfi);
                        dialogIvAddSelfi = layout.findViewById(R.id.dialog_iv_addselfi);
                        dialogIvAddSelfi.setOnClickListener(addSelfi);
                        dialogTvAddSelfi = layout.findViewById(R.id.dialog_tv_addselfi);
                        dialogTvAddSelfi.setOnClickListener(addSelfi);
                        dialogEtName = layout.findViewById(R.id.dialog_et_name);
                        if(G.lastName!=null) dialogEtName.setText(G.lastName);
                        dialogEtPass = layout.findViewById(R.id.dialog_et_pass);
                        dialogEtMsg = layout.findViewById(R.id.dialog_et_msg);
                        dialogIvShowpic = layout.findViewById(R.id.dialog_iv_showpic);
                        dialogIvTakepic = layout.findViewById(R.id.dialog_iv_takepic);
                        dialogIvTakepic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent,10);
                            }
                        });

                        builder.setView(layout);
                        builder.setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                G.lastSelfi = tmpSelfiUri;

                                String serverUrl = "http://skill12.dothome.co.kr/HolyLand/DBInsert.php";

                                String name = dialogEtName.getText().toString();
                                G.lastName = name;
                                String pass = dialogEtPass.getText().toString();
                                String msg = dialogEtMsg.getText().toString();

                                SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        new AlertDialog.Builder(getActivity()).setMessage(response).show();
                                        loadDataToJson();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                multiPartRequest.addStringParam("name",name);
                                multiPartRequest.addStringParam("pass",pass);
                                multiPartRequest.addStringParam("msg",msg);
                                multiPartRequest.addFile("selfi",selfiImgPath);
                                multiPartRequest.addFile("main",mainImgPath);

                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                requestQueue.add(multiPartRequest);
                            }
                        });
                        builder.setNegativeButton("취소",null);
                        builder.show();

                        break;
                }
                return false;
            }
        });

        layoutRefresh = view.findViewById(R.id.layout_refresh);
        layoutRefresh.setColorSchemeColors(Color.BLUE,Color.CYAN,Color.RED,Color.MAGENTA);
        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataToJson();
                layoutRefresh.setRefreshing(false);
            }
        });
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter= new BoardAdapter(items,getActivity());

        layoutManager = new StaggeredGridLayoutManager(2,1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadDataToJson();


        return view;
    }

    void loadDataToJson(){

        String serverUrl = "http://skill12.dothome.co.kr/HolyLand/loadDBToJson.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,serverUrl,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    items.clear();
                    adapter.notifyDataSetChanged();

                    String apendPath = "http://skill12.dothome.co.kr/HolyLand/";

                    for (int i = 0; i < response.length(); i++) {
                        Log.i("response",""+response.length());

                        JSONObject jsonObject = response.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String pass = jsonObject.getString("password");
                        String msg = jsonObject.getString("message");
                        String mainFilePath = jsonObject.getString("mainfilepath");
                        String selfiFilePath = jsonObject.getString("selfifilepath");
                        String date = jsonObject.getString("date");

                        mainFilePath = apendPath+mainFilePath;
                        selfiFilePath = apendPath+selfiFilePath;

                        items.add(0,new BoardItem(mainFilePath,selfiFilePath,name,msg,date,pass));
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("jsonerror",error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode == getActivity().RESULT_OK){
                    Uri uri = data.getData();
                    if(uri != null){
                        Picasso.with(getActivity()).load(uri).into(dialogIvShowpic);

                        mainImgPath = getRealPathFromUri(uri);
                        Toast.makeText(getActivity(), mainImgPath, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 11:
                if(resultCode == getActivity().RESULT_OK){
                    Uri uri = data.getData();
                    if(uri != null){
                        Picasso.with(getActivity()).load(uri).into(dialogIvSelfi);
                        tmpSelfiUri = uri.toString();
                        selfiImgPath = getRealPathFromUri(uri);
                        Toast.makeText(getActivity(), selfiImgPath, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    class AddSelfi implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,11);
        }
    }

    void loadNoti(){

        String serverUrl = "http://skill12.dothome.co.kr/HolyLand/loadNoti.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvSuper.setText("공지사항 : "+response);
                noti = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                tvSuper.setText("공지사항 로드 실패");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}


