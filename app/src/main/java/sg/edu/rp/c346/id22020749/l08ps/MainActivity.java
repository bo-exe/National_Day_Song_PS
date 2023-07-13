package sg.edu.rp.c346.id22020749.l08ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText InputTitle, InputSinger, InputYear;
    RadioGroup rgRating;
    Button btnInsertSong, btnShowList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputTitle = findViewById(R.id.InputTitle);
        InputSinger = findViewById(R.id.InputSinger);
        InputYear = findViewById(R.id.InputYear);

        rgRating = findViewById(R.id.rgRating);

        btnInsertSong = findViewById(R.id.btnInsertSong);
        btnShowList = findViewById(R.id.btnShowList);

        lv = findViewById(R.id.lv);

        ArrayList<String> songArray = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songArray);
        lv.setAdapter(adapter);

        btnInsertSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String songTitle = InputTitle.getText().toString();
                String songSingers = InputSinger.getText().toString();
                int songYear = Integer.parseInt(InputYear.getText().toString());

                int starBtn = rgRating.getCheckedRadioButtonId();
                int starValue;

                if (starBtn ==  R.id.radioButton)
                    starValue = 1;
                else if (starBtn == R.id.radioButton2)
                    starValue = 2;
                else if (starBtn == R.id.radioButton3)
                    starValue = 3;
                else if (starBtn == R.id.radioButton4)
                    starValue = 4;
                else if (starBtn == R.id.radioButton5)
                    starValue = 5;
                else
                    starValue = 0;

                DBHelper db = new DBHelper(MainActivity.this);
                db.insertSong(songTitle, songSingers, songYear, starValue);

                InputTitle.setText("");
                InputSinger.setText("");
                InputYear.setText("");
                rgRating.clearCheck();

                Toast.makeText(MainActivity.this, "Song added.", Toast.LENGTH_SHORT).show();

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                songArray.clear();

                ArrayList<Song> songList = db.getSongs();
                db.close();

                for (int i = 0; i < songList.size(); i++) {
                    Log.d("Database Content", i + ". " + songList.get(i));
                    songArray.add(songList.get(i).toString());
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Song clickedSong = songList.get(position);
                        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                        intent.putExtra("clickedSong", (CharSequence) clickedSong);
                        startActivity(intent);
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });




    }


}