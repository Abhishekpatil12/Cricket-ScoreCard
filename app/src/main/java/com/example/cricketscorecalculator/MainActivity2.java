package com.example.cricketscorecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CricketAdapter dataAdapter;
    private ArrayList<Cricket> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<Cricket> arr = (ArrayList<Cricket>) getIntent().getSerializableExtra("score");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();

        for(int i=0;i<arr.size();i++)
        {
            dataList.add(arr.get(i));
        }

        dataAdapter = new CricketAdapter(this,dataList);
        recyclerView.setAdapter(dataAdapter);

    }
}