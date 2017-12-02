package com.larsen.magnus.guessthenumber;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class HallActivity extends Activity {

    SQLiteDatabase db;
    DBhelper hallOfFameDB;
    private RecyclerView mRecyclerView;
    private HallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        try {
            hallOfFameDB = new DBhelper(this);
            db = hallOfFameDB.getReadableDatabase();

            ArrayList<String> names = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM scoreTable ORDER BY score DESC", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name;
                    name = cursor.getString(1) + " - " + cursor.getString(4) + "\n" + cursor.getString(3) + " points";
                    names.add(name);
                } while (cursor.moveToNext());
            }

            String[] hallNames = new String[names.size()];
            for (int i=0; i<hallNames.length;i++) {
                hallNames[i]=names.get(i);
            }

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(manager);

            adapter = new HallAdapter(hallNames);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
