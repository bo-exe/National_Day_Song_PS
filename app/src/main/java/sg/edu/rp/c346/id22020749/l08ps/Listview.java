package sg.edu.rp.c346.id22020749.l08ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Listview extends AppCompatActivity {

    ListView lvSongList;
    ToggleButton btnFilterSongs;
    Spinner yearSpinner;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        lvSongList = findViewById(R.id.songsLv);
        btnFilterSongs = findViewById(R.id.btnFilterSongs);
        yearSpinner = findViewById(R.id.yearSpinner);

        DBHelper db = new DBHelper(Listview.this);

        ArrayList<Song> songList = db.getSongs();
        ArrayList<Song> defaultSongList = db.getSongs();
        ArrayList<Song> filteredSongList = db.getFilteredSongs();
        ArrayList<String> yearArr = db.getUniqueYears();
        yearArr.add("Filter by - Default");
        Collections.sort(yearArr, Collections.reverseOrder());

        db.close();

        adapter = new CustomAdapter(this, R.layout.row, songList);
        lvSongList.setAdapter(adapter);

        ArrayAdapter yearAdapter = new ArrayAdapter<>(Listview.this, android.R.layout.simple_list_item_1, yearArr);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(0, false);


        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                int year;

                try {
                    year = Integer.parseInt(selectedItem);
                    DBHelper db = new DBHelper(Listview.this);
                    ArrayList<Song> filteredSongList = db.getFilteredSongs(year);
                    adapter.clear();
                    adapter.addAll(filteredSongList);
                    adapter.notifyDataSetChanged();
                    db.close();
                } catch (NumberFormatException e) {
                    ArrayList<Song> filteredSongList = db.getSongs();
                    adapter.clear();
                    adapter.addAll(filteredSongList);
                    adapter.notifyDataSetChanged();
                    db.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvSongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data;

                if (!btnFilterSongs.isChecked())
                    data = songList.get(position);
                else
                    data = filteredSongList.get(position);

                Intent intent = new Intent(Listview.this, ThirdActivity.class);
                intent.putExtra("data", data);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnFilterSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFilterSongs.isChecked()) {
                    adapter.clear();
                    adapter.addAll(filteredSongList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.clear();
                    adapter.addAll(defaultSongList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.insert) {
            Intent intent = new Intent(Listview.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.showList) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}