package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ImageButton backButton, starbucksButton, ediyaButton, composeButton, angelButton, hollysButton, megaButton, ppascucciButton, tomButton, twsomeButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

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
                MenuActivity.brand_Code="tt";
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
        return view;
    }
}