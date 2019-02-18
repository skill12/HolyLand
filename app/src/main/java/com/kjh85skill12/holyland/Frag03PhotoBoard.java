package com.kjh85skill12.holyland;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class Frag03PhotoBoard extends Fragment {

    ArrayList<BoardItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    BoardAdapter adapter;
    StaggeredGridLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_photoboard,container,false);

        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                                 "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","asdkfjadskjflsadkjfsjfjsadlkfjlakdsjflksadjflksdajflaksdjflksadjfksadjkfjasdkfjasdlkfjakdsjflakdsjflkasdjflakdsjfkdsjfkj\najdsfkasjdfkjsadlkfjasdlkfjlksadjfl;sajdflakdsjflkasdjfl\naskdjflkasdjflasdjfjasdlkfjalsdk\nakdfjlkasdjflaksdjfl","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","\n\n\najsdkfjasdlkfjalsdkiufioaewflkasdjfioasdjnckljadslfjasdjoiejflkasdnflads\nasdlkfjasldkjflkadsjflaskdj\nanfasdlkfjlksadjflsdaj","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));
        items.add(new BoardItem("http://skill12.dothome.co.kr/HolyLand/Img/img1/000.jpg",
                "http://skill12.dothome.co.kr/HolyLand/Img/img1/010.jpg","김주형","ㅁ넝라ㅣㅁㄴ어리ㅏㅁㄴ;ㅣㅓ","ㅁㄴㅇㄹㅇㄴㅁㄹㄴㅁㅇㄹ"));


        recyclerView = view.findViewById(R.id.recyclerview);
        adapter= new BoardAdapter(items,getActivity());
        layoutManager = new StaggeredGridLayoutManager(2,1);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
