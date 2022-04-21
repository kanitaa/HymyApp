package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv =findViewById(R.id.teematListView);
        lv.setAdapter(new ArrayAdapter<Teemat>(this,
                android.R.layout.simple_list_item_1,
                Singleton.getInstance().getTeemat()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent nextActivity=new Intent(MainActivity.this,GameActivity.class);
                startActivity(nextActivity);
            }
        });


    }


}