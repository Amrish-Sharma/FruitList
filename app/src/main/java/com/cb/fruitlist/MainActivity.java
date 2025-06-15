package com.cb.fruitlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    ImageView fruitIcon, vegetableIcon, animalIcon,vehicleIcon,birdIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fruitIcon = findViewById(R.id.fruitIcon);
        vegetableIcon = findViewById(R.id.vegetableIcon);
        animalIcon = findViewById(R.id.animalIcon);
        vehicleIcon = findViewById(R.id.vehicleIcon);
        birdIcon = findViewById(R.id.birdIcon);

        fruitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FruitActivity.class);
                startActivity(intent);
            }
        });

        vegetableIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VegetableActivity.class);
                startActivity(intent);
            }
        });

        animalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimalActivity.class);
                startActivity(intent);
            }
        });

        vehicleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransportActivity.class);
                startActivity(intent);
            }
        });

        birdIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BirdActivity.class);
                startActivity(intent);
            }
        });
    }
}