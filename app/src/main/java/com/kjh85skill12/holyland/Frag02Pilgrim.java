package com.kjh85skill12.holyland;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Frag02Pilgrim extends Fragment {

    SliderLayout slideShow;
    TextView tvPilgrim;
    PagerIndicator indicator;

    ArrayList<String> tags = new ArrayList<>();

    int[] sumI = new int[]{0,0,11,25,48,56,66,86,106,109,113,119,136,148,163,178,194,209,224,237,278,296,310,319,333,341,363,371,377,385,395,414,459,470,473,480,493,512,534,538};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_pilgrim,container,false);

        slideShow = view.findViewById(R.id.layout_slider);
        tvPilgrim = view.findViewById(R.id.tv_pilgrim);
        indicator = view.findViewById(R.id.indicator);

        tvPilgrim.setMovementMethod(new ScrollingMovementMethod());

        slideShow.setCustomIndicator(indicator);

        for(int i=0;i<11;i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            if(i<10) textSliderView.description(getString(R.string.slide_001_000+i)).image("http://skill12.dothome.co.kr/HolyLand/Img/img1/00"+i+".jpg");
            else textSliderView.description(getString(R.string.slide_001_000+i)).image("http://skill12.dothome.co.kr/HolyLand/Img/img1/0"+i+".jpg");
            slideShow.addSlider(textSliderView);
        }
        tvPilgrim.setText(R.string.list_001);


        return view;
    }



    public void imgSliderSet(int position, int cnt){

        slideShow.removeAllSliders();

        for(int i=0;i<cnt;i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            if(i<10) textSliderView.description(getString(R.string.slide_001_000+(sumI[position]+i))).image("http://skill12.dothome.co.kr/HolyLand/Img/img"+position+"/00"+i+".jpg");
            else textSliderView.description(getString(R.string.slide_001_000+(sumI[position]+i))).image("http://skill12.dothome.co.kr/HolyLand/Img/img"+position+"/0"+i+".jpg");
            slideShow.addSlider(textSliderView);
        }

    }

    public void textSet(int position){

        tvPilgrim.setText(R.string.list_001+(position-1));

    }
}
