package com.example.capstone.Unit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Session_Unit {
    String emailId;
    Claims claims = Jwts.claims();
    public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String jws;

    public Session_Unit(String emailId){
        this.emailId = emailId;
        claims.put("emailId",emailId);
        long curTime = System.currentTimeMillis();
        jws = Jwts.builder().setHeaderParam("typ","JWT")// 토큰 유형
                .setIssuer("What_Coffee")
                .setClaims(claims)
                .setExpiration(new Date(curTime + 3600)) // 나중에 시간되면 만료되는것도 만들 예정.
                .setIssuedAt(new Date(curTime))
                .signWith(key).compact(); // 생성
    }
    public static String getMemberEmail() {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws)
                .getBody().get("emailId",String.class);
    }
    public static String getMemberEmail(String jws) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws)
                .getBody().get("emailId",String.class);
    }

    public static void setJws(String jws) {
        Session_Unit.jws = jws;
    }
}
