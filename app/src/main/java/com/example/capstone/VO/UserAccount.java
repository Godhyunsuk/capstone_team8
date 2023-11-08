package com.example.capstone.VO;

// 사용자 계정 정보 모델 클래스

public class UserAccount {

    private String idToken; //파이어베이스 고유 토큰 정보
    private String emailId; //이메일 아이디
    private String password; //비밀번호
    private String nickname; // 닉네임
    private String Liked; // 즐겨찾기
    private String[] LikeList; // 좋아요 모음.
    public UserAccount() { }

    public String getLiked(){return Liked;}

    public void setLiked(String Liked){this.Liked = Liked;}

    public String[] getLikeList(){return LikeList;}

    public void setLikeList(String[] LikeList){this.LikeList = LikeList;}

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
