package com.example.capstone.VO;

import java.util.ArrayList;

public class mysql_User {
    String emailId;
    ArrayList<String> Like_List;
    public mysql_User(){
    }

    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId){this.emailId = emailId;}

    public ArrayList<String> getLike_List() {
        return Like_List;
    }

    public void setLike_List(ArrayList like_List) {
        Like_List = like_List;
    }
    public void addLike_List(String s){
        Like_List.add(s);
    }
    public void removeLike_List(String s){
        Like_List.remove(s);
    }
}
