package com.example.capstone.VO;

public class Coffee_Object {
    String d_id;
    String bname;
    double kal;
    double fat;
    double protein;
    double Na;
    double suger;
    double Caff;
    int ord;
    boolean check_like;

    @Override
    public String toString() {
        return "coffee{" +
                "d_id='" + d_id + '\'' +
                ", bname='" + bname + '\'' +
                ", kal=" + kal +
                ", fat=" + fat +
                ", protein=" + protein +
                ", Na=" + Na +
                ", suger=" + suger +
                ", Caff=" + Caff +
                ", ord=" + ord +
                ", check_like=" + check_like +
                '}';
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public double getKal() {
        return kal;
    }

    public void setKal(double kal) {
        this.kal = kal;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getNa() {
        return Na;
    }

    public void setNa(double na) {
        Na = na;
    }

    public double getSuger() {
        return suger;
    }

    public void setSuger(double suger) {
        this.suger = suger;
    }

    public double getCaff() {
        return Caff;
    }

    public void setCaff(double caff) {
        Caff = caff;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public boolean isCheck_like() {
        return check_like;
    }

    public void setCheck_like(boolean check_like) {
        this.check_like = check_like;
    }
}
