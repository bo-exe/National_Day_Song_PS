package sg.edu.rp.c346.id22020749.l08ps;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private EditText etTitle, etSinger, etYear, etStars;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        etStars = findViewById(R.id.etStars);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Song clickedSong = getIntent().getParcelableExtra("clickedSong");

        etTitle.setText(clickedSong.getTitle());
        etSinger.setText(clickedSong.getSingers());
        etYear.setText(String.valueOf(clickedSong.getYear()));
        etStars.setText(String.valueOf(clickedSong.getStars()));

        Button btnShowFiveStarSongs = findViewById(R.id.btnShowStar);
        ListView listView = findViewById(R.id.listView);

        btnShowFiveStarSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(ThirdActivity.this);
                ArrayList<Song> fiveStarSongs = dbHelper.getFiveStarSongs();
                dbHelper.close();

                ArrayAdapter<Song> adapter = new ArrayAdapter<>(ThirdActivity.this, android.R.layout.simple_list_item_1, fiveStarSongs);
                listView.setAdapter(adapter);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = etTitle.getText().toString();
                String updatedSinger = etSinger.getText().toString();
                int updatedYear = Integer.parseInt(etYear.getText().toString());
                int updatedStars = Integer.parseInt(etStars.getText().toString());

                DBHelper db = new DBHelper(ThirdActivity.this);
                db.updateSong(clickedSong.getId(), updatedTitle, updatedSinger, updatedYear, updatedStars);
                db.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ThirdActivity.this);
                db.deleteSong(clickedSong.getId());
                db.close();
                finish();
            }
        });
    }
}
