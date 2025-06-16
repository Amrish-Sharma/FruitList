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

public class BirdActivity extends AppCompatActivity {

    ListView listView;
    TextToSpeech textToSpeech;

    String birdNames[] = {"Pigeon", "Parrot", "Peacock", "Ostrich", "Crow", "Kiwi"};
    int birdImageIds[] = {R.drawable.pigeon, R.drawable.parrot, R.drawable.peacock, R.drawable.ostrich, R.drawable.crow, R.drawable.kiwi};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < birdNames.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", birdNames[i]);
            map.put("image", birdImageIds[i]);
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
                String name = birdNames[position];
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