package com.example.capstone;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.DAO.All_Data;
import com.example.capstone.DAO.DrinkData;
import com.example.capstone.DAO.SelectData;
import com.example.capstone.VO.Coffee_Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends Fragment {
    RecyclerView rvBack,rvAngel,rvPascucci,rvCompose,rvTomntoms,rvEdiya,rvStarbucks,rvHollys,rvMega,rvTwosome;
    double[] fcentroid;
    Map<String ,double[]> data = new HashMap<>();
    All_Data All = new All_Data();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,container,false);
        rvBack = (RecyclerView) view.findViewById(R.id.rvBack);
        rvStarbucks = (RecyclerView) view.findViewById(R.id.rvStarbucks);
        rvEdiya = (RecyclerView) view.findViewById(R.id.rvEdiya);
        rvCompose = (RecyclerView) view.findViewById(R.id.rvCompose);
        rvAngel = (RecyclerView) view.findViewById(R.id.rvAngel);
        rvHollys = (RecyclerView) view.findViewById(R.id.rvHollys);
        rvMega = (RecyclerView) view.findViewById(R.id.rvMega);
        rvPascucci = (RecyclerView) view.findViewById(R.id.rvPascucci);
        rvTomntoms = (RecyclerView) view.findViewById(R.id.rvTomntoms);
        rvTwosome = (RecyclerView) view.findViewById(R.id.rvTwosome);
        RecyclerView[] rvs = {rvBack,rvStarbucks,rvEdiya,rvCompose,rvAngel,
                rvHollys,rvMega,rvPascucci,rvTomntoms,rvTwosome};
        String[] brands = {"back","starbucks","ediya","compose","angel",
                "hollus","mega","pascucci","tomntoms","twosome"};
        String[] brand_Codes = {"BB","SB","ED","CC","AG","HA","MG","PC","tt","TW"};
        List<String> favoriteList = FavoriteFragment.FavoriteList;
        if(favoriteList.get(0).isEmpty()){
            favoriteList=favoriteList.subList(1,favoriteList.size());
        }
        System.out.println(favoriteList);
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        for(Coffee_Object c : All.CoffeeObject){
            double[] s = {c.getKcal(),c.getFat(),c.getProtein(),c.getNa(),c.getSuger(),c.getCaff()};
            data.put(c.getD_id(),s);

        }
        Recommend_favorite rf = new Recommend_favorite(favoriteList,data);
        fcentroid = rf.getC();

        for(int i=0;i<10;i++){
            ContentValues fvalues = new ContentValues();
            List<String> flist = new ArrayList<>();
            Map<String,double[]> fdata = new HashMap<String,double[]>();
            Map<String,String> id_name = new HashMap<String,String>();
            String[] names = new String[6];
            String[] ids = new String[6];
            int[] Imgs = new int[6];

            fvalues.put("brand_query", brands[i]);
            fvalues.put("brand_where", brand_Codes[i]);
            List<Coffee_Object> fdd=new ArrayList<>();
            for(Coffee_Object c : All.CoffeeObject){
                String code = String.valueOf(c.getD_id().charAt(0))+String.valueOf(c.getD_id().charAt(1));
                if(code.equals(brand_Codes[i])){
                    fdd.add(c);
                }
            }
            Coffee_Object[] fd = fdd.toArray(new Coffee_Object[fdd.size()]);

            for(Coffee_Object c : fd){
                double[] s = {c.getKcal(),c.getFat(),c.getProtein(),c.getNa(),c.getSuger(),c.getCaff()};
                fdata.put(c.getD_id(),s);
                id_name.put(c.getD_id(),c.getBname());
            }
            String img = "@drawable/"+brands[i];
            int iResId = getResources().getIdentifier( img, "drawable","com.example.capstone");

            for(int j=0;j<6;j++){
                Imgs[j] = iResId;
            }

            Recommend_favorite fr = new Recommend_favorite(fcentroid,fdata);
            flist = fr.list_out();

            int cnt = 0;
            for(String f : flist){
                names[cnt] = id_name.get(f);
                ids[cnt] = f;
                cnt++;
            }

            Adapter adapter = new Adapter(getContext(),names,Imgs);
            int finalI = i;
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(Adapter.ViewHolder viewHolder, View view, int position) {
                    Intent intent = new Intent(getActivity(), DrinkActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    MenuActivity.brand=brands[finalI];
                    DrinkActivity.name=names[position];
                    DrinkActivity.id=ids[position];
                    DrinkActivity.CoffeeObject = fd;
                }
            });
            rvs[i].setAdapter(adapter);
            rvs[i].setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
            if (favoriteList.isEmpty()) {
                rvs[i].setVisibility(View.INVISIBLE);
            }else{
                rvs[i].setVisibility(View.VISIBLE);
            }
        }


        return view;
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements OnItemClickListener{
        private Context context;
        private String[] name;
        OnItemClickListener listener;
        int[] Img;
        public Adapter(Context context, String[] name, int[] Img){
            this.context = context;
            this.name = name;
            this.Img = Img;
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
            holder.setItem(name[position],Img[position]);
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
            void setItem(String name,int img) {
                textView.setText(name);
                Img.setImageResource(img);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Adapter.ViewHolder viewHolder, View view, int position);
    }
}