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

public class VegetableActivity extends AppCompatActivity {

    ListView listView;
    TextToSpeech textToSpeech;

    String vegetableNames[] = {"Carrot", "Broccoli", "Spinach", "Potato", "Tomato", "Cucumber"};
    int vegetableImageIds[] = {R.drawable.carrot, R.drawable.broccoli, R.drawable.spinach, R.drawable.potato, R.drawable.tomato, R.drawable.cucumber};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < vegetableNames.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", vegetableNames[i]);
            map.put("image", vegetableImageIds[i]);
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
                String name = vegetableNames[position];
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