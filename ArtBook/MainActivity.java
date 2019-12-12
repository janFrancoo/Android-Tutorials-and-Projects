package com.janfranco.artbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Integer> idArr;
    ArrayList<String> nameArr;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idArr = new ArrayList<>();
        nameArr = new ArrayList<>();
        listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                nameArr);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("artID", idArr.get(position));
                intent.putExtra("info", "old");
                startActivity(intent);
            }
        });

        getData();
    }

    public void getData(){
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,
                    null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts", null);
            int id = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("art");

            while (cursor.moveToNext()) {
                idArr.add(cursor.getInt(id));
                nameArr.add(cursor.getString(nameIndex));
            }

            arrayAdapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_art, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_art_item){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("info", "new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
