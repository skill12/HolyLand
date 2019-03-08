package com.kjh85skill12.holyland;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    LinearLayout layoutSecond;
    LinearLayout btnMap;

    ViewPager pager;
    FragmentAdapter adapter;
    TabLayout tabLayout;
    DrawerLayout layoutDrawer;
    RelativeLayout layoutTop;

    ListView listNavi;
    ListAdapter listAdapter;

    ArrayList<PilgrimItem> pilgrimItems = new ArrayList<>();

    Frag02Pilgrim frag2;
    ToggleButton btnMusic;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mp = MediaPlayer.create(this,Uri.parse(G.musicUrl));
        mp.setLooping(true);

        btnMusic = findViewById(R.id.btn_music);
        btnMusic.setChecked(G.isBgm);
        btnMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                G.isBgm = isChecked;
                if(G.isBgm) mp.setVolume(0.5f,0.5f);
                else mp.setVolume(0,0);
            }
        });
        layoutDrawer = findViewById(R.id.layout_drawer);
        layoutTop = findViewById(R.id.layout_top);
        layoutSecond = findViewById(R.id.layout_second);
        btnMap = findViewById(R.id.btn_map);

        setNaviList();

        listNavi = findViewById(R.id.list_navi);
        View header = getLayoutInflater().inflate(R.layout.list_header,listNavi,false);
        listNavi.addHeaderView(header);



        LayoutInflater inflater = getLayoutInflater();
        listAdapter = new ListAdapter(pilgrimItems,inflater);
        listNavi.setAdapter(listAdapter);

        pager = findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                
            }

            @Override
            public void onPageSelected(int i) {

                switch (i){
                    case 0:
                        layoutSecond.setBackgroundResource(R.drawable.back02);
                        btnMap.setVisibility(View.GONE);
                        break;
                    case 1:
                        layoutSecond.setBackgroundResource(R.drawable.back01);
                        btnMap.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        layoutSecond.setBackgroundResource(R.drawable.back04);
                        btnMap.setVisibility(View.GONE);
                        break;

                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        pager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(pager);
        frag2 = (Frag02Pilgrim) adapter.frags[1];

        listNavi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if(position<1) return;

                int index = pager.getCurrentItem();

                imgCount(position);

                switch (index){
                    case 0:
                        pager.setCurrentItem(index+1);
                        break;
                    case 2:
                        pager.setCurrentItem(index-1);
                        break;
                }

                layoutDrawer.closeDrawer(listNavi);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(G.isBgm) mp.setVolume(0.5f,0.5f);
        else mp.setVolume(0,0);
        mp.start();
    }

    @Override
    protected void onDestroy() {
        if(mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }
        super.onDestroy();
    }

    public void clickOpen(View view) {
        layoutDrawer.openDrawer(listNavi);
    }

    public void setNaviList(){

        for( int i =0; i<39;i++){
            pilgrimItems.add(new PilgrimItem(R.drawable.a001+i,(i+1)+"",getString(R.string.maintext_001+i)));
        }


    }
    public void imgCount(final int position){

        new Thread(){

        @Override
        public void run() {

            String index = position+"";

            String serverUrl = "http://skill12.dothome.co.kr/HolyLand/imgCount.php";

            String getUrl = serverUrl+"?index="+index;

            try {
                URL url = new URL(getUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                String counter = reader.readLine();
                final int cnt = Integer.parseInt(counter);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        frag2.imgSliderSet(position,cnt);
                        frag2.textSet(position);

                    }
                });



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }.start();

    }

    void saveData(){
        SharedPreferences pref = getSharedPreferences("saveData",MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putString("Name",G.lastName);
        editor.putString("Selfi",G.lastSelfi);
        editor.putBoolean("Bgm", G.isBgm);
        editor.putBoolean("Token", G.isToken);

        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void clickChat(View view) {
        new AlertDialog.Builder(SecondActivity.this).setMessage("채팅기능을 준비중입니다.!\nComming Soon!!").show();
    }
}
