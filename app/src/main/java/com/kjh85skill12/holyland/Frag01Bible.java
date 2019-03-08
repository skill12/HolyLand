package com.kjh85skill12.holyland;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class Frag01Bible extends Fragment {

    TextView tvBible;
    Random rnd = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_bible,container,false);

        tvBible = view.findViewById(R.id.tv_bible);
        tvBible.setMovementMethod(new ScrollingMovementMethod());

        int r = rnd.nextInt(95);
        tvBible.setText(R.string.bible_001+r);


        return view;
    }


}
