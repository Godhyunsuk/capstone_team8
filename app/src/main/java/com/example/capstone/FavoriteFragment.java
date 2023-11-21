package com.example.capstone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.Fragment;

import com.example.capstone.DAO.All_Data;
import com.example.capstone.VO.Coffee_Object;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {
    static List<String> FavoriteList;
    String[] favoriteName;
    int[] favoriteImage;
    ListView listView;
    All_Data All = new All_Data();
    Map<String ,double[]> data = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        listView = (ListView) view.findViewById(R.id.flistView);
        if(!HomeFragment.User.getLike_List().isEmpty()) {
            listView.setVisibility(View.VISIBLE);
        }
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        favoriteName = new String[FavoriteList.size()];
        favoriteImage = new int[FavoriteList.size()];

        for(Coffee_Object c : All.CoffeeObject){
            for(int i=0; i<favoriteName.length; i++){
                if(c.getD_id().equals(FavoriteList.get(i))){
                    favoriteName[i] = c.getBname();
                    System.out.println(favoriteName[i]);
                }
            }

        }

        for(int i=0; i< FavoriteList.size(); i++){
            favoriteImage[i] = R.drawable.back;
        }

        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(FavoriteFragment.this,favoriteName,favoriteImage);
        listView.setAdapter(favoriteAdapter);

        return view;
    }

    public class FavoriteAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        String[] arrBackName;
        int[] arrBackImage;

        public FavoriteAdapter(Fragment fragment, String[] arrBackName, int[] arrBackImage) {
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
                view = inflater.inflate(R.layout.menu_layout, null);
            }

            ImageView backImage = view.findViewById(R.id.menuImage);
            TextView backName = view.findViewById(R.id.menuName);

            backImage.setImageResource(arrBackImage[position]);
            backName.setText(arrBackName[position]);

            return view;
        }
    }

}