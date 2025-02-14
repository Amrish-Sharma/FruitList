package com.cb.fruitlist;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AnimalActivity extends AppCompatActivity {

    ListView listView;
    TextToSpeech textToSpeech;

    String animalNames[] = {"Cat", "Dog", "Elephant", "Lion", "Tiger", "Zebra"};
    int animalImageIds[] = {R.drawable.cat, R.drawable.dog, R.drawable.elephant, R.drawable.lion, R.drawable.tiger, R.drawable.zebra};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", animalNames[i]);
            map.put("image", animalImageIds[i]);
            list.add(map);
        }

        String[] from = {"name", "image"};
        int[] to = {R.id.textView, R.id.imageView};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.list_row_items, from, to);
        listView.setAdapter(simpleAdapter);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "IN"));
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = animalNames[position];
                textToSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}