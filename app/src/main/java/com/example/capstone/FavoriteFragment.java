package com.example.capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.capstone.DAO.All_Data;
import com.example.capstone.VO.Coffee_Object;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {
    static List<String> FavoriteList;
    String[] favoriteName;
    int[] favoriteImage;
    String[] favoriteId;
    ListView listView;
    All_Data All = new All_Data();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        listView = (ListView) view.findViewById(R.id.flistView);

        if(!FavoriteList.isEmpty()) {
            listView.setVisibility(View.VISIBLE);
        }
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        favoriteName = new String[FavoriteList.size()];
        favoriteImage = new int[FavoriteList.size()];
        favoriteId = new String[FavoriteList.size()];

        for(Coffee_Object c : All.CoffeeObject){
            for(int i=0; i<favoriteName.length; i++){
                if(c.getD_id().equals(FavoriteList.get(i))){
                    favoriteName[i] = c.getBname();
                    favoriteId[i] = c.getD_id();
                }
            }

        }


        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(FavoriteFragment.this,favoriteName,favoriteId);
        listView.setAdapter(favoriteAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), DrinkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                DrinkActivity.name=favoriteName[position];
                DrinkActivity.id=favoriteId[position];
                DrinkActivity.CoffeeObject = All.CoffeeObject;
            }
        });

        return view;
    }

    public class FavoriteAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        String[] arrBackName;
        String[] arrBackImage;

        public FavoriteAdapter(Fragment fragment, String[] arrBackName, String[] arrBackImage) {
            // Use fragment's context
            this.context = fragment.getActivity();
            this.arrBackName = arrBackName;
            this.arrBackImage = arrBackImage;
        }

        @Override
        public int getCount() {
            return arrBackName.length;
        }

        @Override
        public Object getItem(int position) {
            return arrBackName[position];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if (view == null) {
                view = inflater.inflate(R.layout.menu_layout3, null);
            }

            ImageView backImage = view.findViewById(R.id.menuImage);
            TextView backName = view.findViewById(R.id.menuName);
            try {
                URL uri = new URL("http://43.201.98.166/test/"+arrBackImage[position]+".png");
                Glide.with(context).load(uri).into(backImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            backName.setText(arrBackName[position]);

            return view;
        }
    }

}