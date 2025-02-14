package com.cb.fruitlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView fruitIcon, vegetableIcon, animalIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruitIcon = findViewById(R.id.fruitIcon);
        vegetableIcon = findViewById(R.id.vegetableIcon);
        animalIcon = findViewById(R.id.animalIcon);

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
    }
}