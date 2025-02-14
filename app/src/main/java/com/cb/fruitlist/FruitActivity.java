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

public class FruitActivity extends AppCompatActivity {

    ListView listView;
    TextToSpeech textToSpeech;

    String fruitNames[] = {"Banana", "Grapes", "Guava", "Mango", "Orange", "Watermelon"};
    int fruitImageIds[] = {R.drawable.banana, R.drawable.grape, R.drawable.guava, R.drawable.mango, R.drawable.orange, R.drawable.watermelon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < fruitNames.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", fruitNames[i]);
            map.put("image", fruitImageIds[i]);
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
                String name = fruitNames[position];
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