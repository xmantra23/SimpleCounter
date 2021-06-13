package org.samir.simplecounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CounterItemAdapter.UpdateMainUI {
    private RecyclerView counterRecView;
    private CounterItemAdapter adapter;
    private ImageView imgAddButton;
    private TextView txtNoCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        ArrayList<CounterItem> counterItems = new ArrayList<>();
        counterItems = Utils.getInstance(this).getAllCounters();

        if(counterItems != null){
            if(counterItems.size() > 0){
                adapter = new CounterItemAdapter(this);
                counterRecView.setAdapter(adapter);
                counterRecView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCounterItems(counterItems);
                txtNoCounter.setVisibility(View.GONE);
                counterRecView.setVisibility(View.VISIBLE);
            }else{
                txtNoCounter.setVisibility(View.VISIBLE);
                counterRecView.setVisibility(View.GONE);
            }
        }

        imgAddButton.setOnClickListener(v->{
            Intent intent = new Intent(this,AddActivity.class);
            this.startActivity(intent);
        });

    }

    private void initViews() {
        counterRecView = findViewById(R.id.counterRecView);
        imgAddButton = findViewById(R.id.imgAddButton);
        txtNoCounter = findViewById(R.id.txtNoCounter);
    }

    @Override
    public void setEmptyVisiblity(boolean empty) {
        if(empty)
            txtNoCounter.setVisibility(View.VISIBLE);
        else
            txtNoCounter.setVisibility(View.GONE);
    }
}