package com.example.capstone;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import java.io.*;
import java.util.*;



class CSVReader {
    public List<List<String>> readCSV() {
        List<List<String>> csvList = new ArrayList<List<String>>();
        File csv = new File("C:/coffee.csv");
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(",");
                aLine = Arrays.asList(lineArr);
                csvList.add(aLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return csvList;
    }
}

class DrinkRecommender {
    private Instances data;
    private SimpleKMeans kMeans;
    CSVReader drink_data = new CSVReader();
    public DrinkRecommender() throws Exception {

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("fat"));
        attributes.add(new Attribute("sugar"));
        attributes.add(new Attribute("na"));
        attributes.add(new Attribute("protein"));
        attributes.add(new Attribute("caffeine"));
        attributes.add(new Attribute("kcal"));
        data = new Instances("drink_data", attributes, 0);

        for(int i=1 ; i<drink_data.readCSV().size() ; i++){
            List<String> list = drink_data.readCSV().get(i);
            float fat = Float.parseFloat(list.get(2));
            float sugar = Float.parseFloat(list.get(3));
            float na = Float.parseFloat(list.get(4));
            float protein = Float.parseFloat(list.get(5));
            float caffeine = Float.parseFloat(list.get(6));
            float kcal = Float.parseFloat(list.get(7));
            double[] value = {fat,sugar,na,protein,caffeine,kcal};
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
    public void setDrinkRecommeder(DrinkRecommender ans){
        this.ans = ans;
    }
    DrinkRecommender ans;
    CSVReader drink_data = new CSVReader();
    List<String> recommendlist = new ArrayList<>();
    public List<String> list_out(double[] selected_data) throws Exception {
        int selected_cluster = ans.get_cluster(selected_data);
        for(int i=1 ; i<drink_data.readCSV().size() ; i++){
            List<String> list = drink_data.readCSV().get(i);
            float fat = Float.parseFloat(list.get(2));
            float sugar = Float.parseFloat(list.get(3));
            float na = Float.parseFloat(list.get(4));
            float protein = Float.parseFloat(list.get(5));
            float caffeine = Float.parseFloat(list.get(6));
            float kcal = Float.parseFloat(list.get(7));
            double[] value = {fat,sugar,na,protein,caffeine,kcal};
            if(ans.get_cluster(value) == selected_cluster){
                recommendlist.add(list.get(0));
            }
        }
        return recommendlist;
    }
}

class Recommend_favorite{
    private Instances data;
    CSVReader drink_data = new CSVReader();
    List<String> recommend_favorite = new ArrayList<>();
    double[] favorite_centroid = new double[]{};
    double fat,sugar,na,protein,caffeine,kcal = 0 ;

    public void get_centroid(List<String> favorite_list){

        double size = favorite_list.size();
        for(int i = 0 ; i < drink_data.readCSV().size() ; i++){
            List<String> list = drink_data.readCSV().get(i);
            for(int j = 0; j<size;j++){
                if(Objects.equals(favorite_list.get(j), list.get(0))){
                    fat += Float.parseFloat(list.get(2));
                    sugar += Float.parseFloat(list.get(3));
                    na += Float.parseFloat(list.get(4));
                    protein += Float.parseFloat(list.get(5));
                    caffeine += Float.parseFloat(list.get(6));
                    kcal += Float.parseFloat(list.get(7));
                }
            }
        }
        favorite_centroid = new double[]{fat / size, sugar / size,
                na / size, protein / size, caffeine / size, kcal / size};
    }

    public List<String> list_out(){
        double[][] data = new double[drink_data.readCSV().size()]
                [favorite_centroid.length];
        for(int i = 1;i < drink_data.readCSV().size();i++){
            List<String> list = drink_data.readCSV().get(i);
            double temp = 0;
            for(int j = 0;j < favorite_centroid.length;j++){
                temp += Math.abs(Double.parseDouble(list.get(j+2))
                        -favorite_centroid[j]);
            }
            double[] tmp = {temp,i};

            data[i]=tmp;
        }
        Arrays.sort(data,(o1,o2)->Double.compare(o1[0],o2[0]));
        for(int i=1;i<11;i++){
            int num = (int) data[i][1];
            recommend_favorite.add(drink_data.readCSV().get(num).get(0));
        }
        return recommend_favorite;
    }
}

public class kmeams {
    public static void main(String[] args) throws Exception {
        DrinkRecommender data = new DrinkRecommender();
        double[] selected_data = {0, 42.3, 41.2, 1, 10, 244.5};
        List<String> favorite_list = new ArrayList<>(Arrays.asList("mg-015","mg-026","mg-002","mg-102","mg-079"));
        Recommend_selected result1 = new Recommend_selected();
        Recommend_favorite result2 = new Recommend_favorite();
        result1.setDrinkRecommeder(data);
        System.out.println(result1.list_out(selected_data));
        result2.get_centroid(favorite_list);
        System.out.println(result2.list_out());
    }
}
