package com.kjh85skill12.holyland;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    ViewPager pager;
    FragmentAdapter adapter;
    TabLayout tabLayout;
    DrawerLayout layoutDrawer;
    RelativeLayout layoutTop;

    ListView listNavi;
    ListAdapter listAdapter;

    ArrayList<PilgrimItem> pilgrimItems = new ArrayList<>();

    Frag02Pilgrim frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        layoutDrawer = findViewById(R.id.layout_drawer);
        layoutTop = findViewById(R.id.layout_top);
        layoutSecond = findViewById(R.id.layout_second);

        setNaviList();

        listNavi = findViewById(R.id.list_navi);
        View header = getLayoutInflater().inflate(R.layout.list_header,listNavi,false);
        listNavi.addHeaderView(header);



        LayoutInflater inflater = getLayoutInflater();
        listAdapter = new ListAdapter(pilgrimItems,inflater);
        listNavi.setAdapter(listAdapter);

        pager = findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
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

    public void clickOpen(View view) {
        layoutDrawer.openDrawer(listNavi);
    }

    public void clickMusic(View view) {
    }

    public void clickMap(View view) {
    }
    public void setNaviList(){
        pilgrimItems.add(new PilgrimItem(R.drawable.a001,"성지순례01","aaa"));
        pilgrimItems.add(new PilgrimItem(R.drawable.a001,"성지순례01","aaa"));
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
}