package com.janfranco.travelbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase database;
    ArrayAdapter arrayAdapter;
    ArrayList<Land> landArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        landArrayList = new ArrayList<>();
        listView = findViewById(R.id.placesList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                landArrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("id", landArrayList.get(position).id);
                intent.putExtra("info", "old");
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Delete");
                alert.setMessage("Are you sure?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            database = MainActivity.this.openOrCreateDatabase("Places",
                                    MODE_PRIVATE, null);
                            database.delete("places","id=?",
                                    new String[]{String.valueOf(landArrayList.get(position).id)});
                        } catch (Exception e) {

                        }
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                alert.show();

                return false;
            }
        });

        try {
            database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM places", null);
            int id = cursor.getColumnIndex("id");
            int name = cursor.getColumnIndex("name");
            int latitude = cursor.getColumnIndex("latitude");
            int longitude = cursor.getColumnIndex("longitude");

            while (cursor.moveToNext()) {
                landArrayList.add(new Land(cursor.getInt(id), cursor.getString(name),
                        cursor.getDouble(latitude), cursor.getDouble(longitude)));
            }

            arrayAdapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_place, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_place_item){
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("info", "new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
