package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.myfirstapp.MESSAGE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);

        ListView lv =findViewById(R.id.themesListView);
        lv.setAdapter(new ArrayAdapter<Themes>(this,
                android.R.layout.simple_list_item_1,
                Singleton.getInstance().getThemes()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mp.start();
                Intent nextActivity=new Intent(MainActivity.this,GameActivity.class);
                nextActivity.putExtra(EXTRA,i);
                startActivity(nextActivity);
            }
        });


    }


}