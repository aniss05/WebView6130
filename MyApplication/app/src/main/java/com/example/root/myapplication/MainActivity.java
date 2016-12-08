package com.example.root.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            String html = Jsoup.connect("http://172.31.41.108:8080/aniss").get().html();

            System.out.println(html);

        }catch(Exception ex){

            ex.printStackTrace();
        }

    }
}
