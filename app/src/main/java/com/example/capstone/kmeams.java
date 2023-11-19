package com.example.capstone;

import android.content.res.AssetManager;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import org.apache.commons.collections.map.HashedMap;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class DrinkRecommender {
    private Instances data;
    private SimpleKMeans kMeans;
    public DrinkRecommender(Map<String,double[]> drink_data) throws Exception {

        Iterator<String> keys = drink_data.keySet().iterator();

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("kcal"));
        attributes.add(new Attribute("fat"));
        attributes.add(new Attribute("protein"));
        attributes.add(new Attribute("na"));
        attributes.add(new Attribute("sugar"));
        attributes.add(new Attribute("caffein"));
        data = new Instances("drink_data", attributes, 0);

        while(keys.hasNext()){
            String key = keys.next();
            double[] value = drink_data.get(key);
            data.add(new DenseInstance(1.0, value));
        }
        kMeans = new SimpleKMeans();
        kMeans.setNumClusters(5);
        kMeans.buildClusterer(data);
    }
    public int get_cluster(double[] userAttributes) throws Exception {

        DenseInstance userInstance = new DenseInstance(1.0, userAttributes);
        userInstance.setDataset(data);
        int cluster = kMeans.clusterInstance(userInstance);

        return cluster;
    }
}
class Recommend_selected {
    List<String> recommendlist;
    public  Recommend_selected(DrinkRecommender ans,Map<String,double []> drink_data,String selected) throws Exception {
        Iterator<String> keys = drink_data.keySet().iterator();
        recommendlist = new ArrayList<>();
        int selected_cluster = ans.get_cluster(drink_data.get(selected));
        while(keys.hasNext()){
            String key = keys.next();
            if(ans.get_cluster(drink_data.get(key)) == selected_cluster){
                recommendlist.add(key);
            }
        }
        Collections.shuffle(recommendlist);
    }

    public List<String> list_out(){
        return recommendlist;
    }

}

class Recommend_favorite{
    List<String> recommend_favorite = new ArrayList<>();
    double[] favorite_centroid = new double[6];
    public Recommend_favorite(List<String> favorite_list,Map<String,double[]> drink_data){
        int size = favorite_list.size();
        for(int i = 0 ; i < favorite_list.size() ; i++){
            double[] values = drink_data.get(favorite_list.get(i));
            for(int j=0;j<6;j++){
                favorite_centroid[j]+=values[j]/size;
            }
        }
    }
    public Recommend_favorite(double[] centroid,Map<String,double[]> drink_data){
        Iterator<String> keys = drink_data.keySet().iterator();
        Map<String, Double> data = new HashMap<>();
        while(keys.hasNext()){
            String key = keys.next();
            double[] list = drink_data.get(key).clone();
            Double temp = 0.0;
            for(int i = 0;i < centroid.length;i++){
                temp += Math.abs(list[i] - centroid[i]);
            }
            data.put(key,temp);

        }
        List<Map.Entry<String, Double>> entryList = new LinkedList<>(data.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        int cnt=0;
        for(Map.Entry<String, Double> entry : entryList){
            recommend_favorite.add(entry.getKey());
            cnt++;
            if(cnt>=6){break;}
        }
    }
    public double[] getC() {return favorite_centroid;}
    public List<String> list_out(){
        return recommend_favorite;
    }
}
