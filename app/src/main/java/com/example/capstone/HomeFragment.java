package com.example.capstone;

import static android.content.Context.MODE_PRIVATE;

import java.util.Random;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone.DAO.All_Data;

import com.example.capstone.DAO.Check_and_Find;
import com.example.capstone.Unit.Session_Unit;
import com.example.capstone.VO.Coffee_Object;
import com.example.capstone.VO.mysql_User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class HomeFragment extends Fragment {
    RecyclerView rvRand;
    ImageButton backButton, starbucksButton, ediyaButton, composeButton, angelButton, hollysButton, megaButton, ppascucciButton, tomButton, twsomeButton;
    static mysql_User User;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    All_Data All = new All_Data();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        rvRand = (RecyclerView)view.findViewById(R.id.rvRand);
        backButton = (ImageButton) view.findViewById(R.id.backButton);
        starbucksButton = (ImageButton) view.findViewById(R.id.starbucksButton);
        ediyaButton = (ImageButton) view.findViewById(R.id.ediyaButton);
        composeButton = (ImageButton) view.findViewById(R.id.composeButton);
        angelButton = (ImageButton) view.findViewById(R.id.angelButton);
        hollysButton = (ImageButton) view.findViewById(R.id.hollysButton);
        megaButton = (ImageButton) view.findViewById(R.id.megaButton);
        ppascucciButton = (ImageButton) view.findViewById(R.id.ppascucciButton);
        tomButton = (ImageButton) view.findViewById(R.id.tomButton);
        twsomeButton = (ImageButton) view.findViewById(R.id.twsomeButton);
        pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        FavoriteFragment.FavoriteList= User.getLike_List();

        System.out.println("Home");
        System.out.println(User.getLike_List());
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="back";
                MenuActivity.brand_Code="BB";
            }
        });

        starbucksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="starbucks";
                MenuActivity.brand_Code="SB";
            }
        });

        ediyaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="ediya";
                MenuActivity.brand_Code="ED";
            }
        });

        composeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="compose";
                MenuActivity.brand_Code="CC";
            }
        });

        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="angel";
                MenuActivity.brand_Code="AG";

            }
        });

        hollysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="hollus";
                MenuActivity.brand_Code="HA";
            }
        });

        megaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="mega";
                MenuActivity.brand_Code="MG";
            }
        });

        ppascucciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="pascucci";
                MenuActivity.brand_Code="PC";
            }
        });

        tomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="tomntoms";
                MenuActivity.brand_Code="TT";
            }
        });

        twsomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                MenuActivity.brand="twosome";
                MenuActivity.brand_Code="TW";
            }
        });
        int size = 5;
        Random rand = new Random();
        String[] rname=new String[size];
        String[] rid = new String[size];
        for(int i=0;i<size;i++){
            Coffee_Object randCoffee = All.CoffeeObject[rand.nextInt(All.CoffeeObject.length)];
            rname[i]=randCoffee.getBname();
            rid[i] = randCoffee.getD_id();
        }
        Adapter adapter = new Adapter(getContext(),rname,rid);
        rvRand.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Adapter.ViewHolder viewHolder, View view, int position) {
                Intent intent = new Intent(getActivity(), DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                DrinkActivity.name=rname[position];
                DrinkActivity.id=rid[position];
                String BrandCode = rid[position].substring(0,2);
                List<Coffee_Object> co = new ArrayList<>();
                for(Coffee_Object c : All.CoffeeObject){
                    if(BrandCode.equals(c.getD_id().substring(0,2))){
                        co.add(c);
                    }
                }
                Coffee_Object[] fd = co.toArray(new Coffee_Object[co.size()]);
                DrinkActivity.CoffeeObject = fd;
            }
        });
        rvRand.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        return view;
    }
    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements OnItemClickListener{
        private Context context;
        private String[] name;
        OnItemClickListener listener;
        String[] Id;
        public Adapter(Context context, String[] name, String[] Img){
            this.context = context;
            this.name = name;
            this.Id = Img;
        }
        @Override   // 아이템의 개수 리턴
        public int getItemCount() { return name.length; }
        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.menu_layout2, parent, false);

            return new ViewHolder(itemView,this);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setItem(name[position],Id[position]);
        }

        @Override
        public void onItemClick(ViewHolder viewHolder, View view, int position) {
            if (listener != null) {
                listener.onItemClick(viewHolder, view, position);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            ImageView Img;

            ViewHolder(View itemView,final OnItemClickListener listener) {
                super(itemView);

                textView = itemView.findViewById(R.id.menuName);
                Img = itemView.findViewById(R.id.menuImage);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onItemClick(ViewHolder.this, view, getAdapterPosition());
                        }
                    }
                });

            }
            void setItem(String name,String id) {
                textView.setText(name);
                try {
                    URL uri = new URL("http://43.201.98.166/test/"+id+".png");
                    Glide.with(context).load(uri).into(Img);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Adapter.ViewHolder viewHolder, View view, int position);
    }
    @Override
    public void onStop() {
        super.onStop();
        try{
            JSONArray jsonArray = new JSONArray();
            for(String str : User.getLike_List()){
                jsonArray.put(str);
            }
            String jsonString = jsonArray.toString();
            System.out.println(jsonString);
            System.out.println("HOMEFRAGMENT");
            editor.putString("LL",jsonString);
            editor.commit();
        }catch(Exception e){
            System.out.println("유저 오류입니당");
        }
    }

}