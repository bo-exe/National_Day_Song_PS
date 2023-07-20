package sg.edu.rp.c346.id22020749.l08ps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText songTitleInput, songSingerInput, songYearInput;
    RadioGroup starRg;
    Button btnInsertSong, btnShowList;
    private int selectedRadioButtonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songTitleInput = findViewById(R.id.songTitleInput);
        songSingerInput = findViewById(R.id.songSingerInput);
        songYearInput = findViewById(R.id.songYearInput);

        starRg = findViewById(R.id.starRg);

        btnInsertSong = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        ArrayList<String> songArr = new ArrayList<>();

        btnInsertSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isEmpty;
                starRg.check(R.id.radioButton6);

                String songTitle = songTitleInput.getText().toString();
                String songSingers = songSingerInput.getText().toString();
                String songYearString = songYearInput.getText().toString();

                if (songTitle.isEmpty() || songSingers.isEmpty() || songYearString.isEmpty())
                    isEmpty = true;
                else {
                    isEmpty = false;
                }

                int starBtn = starRg.getCheckedRadioButtonId();
                int starValue;

                if (starBtn ==  R.id.radioButton6)
                    starValue = 1;
                else if (starBtn == R.id.radioButton7)
                    starValue = 2;
                else if (starBtn == R.id.radioButton8)
                    starValue = 3;
                else if (starBtn == R.id.radioButton9)
                    starValue = 4;
                else if (starBtn == R.id.radioButton10)
                    starValue = 5;
                else
                    starValue = 0;

                DBHelper db = new DBHelper(MainActivity.this);

                if (!isEmpty) {
                    int songYear = Integer.parseInt(songYearString);
                    db.insertSong(songTitle, songSingers, songYear, starValue);
                    Toast.makeText(MainActivity.this, "Song added!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Error. Please enter input on all fields.", Toast.LENGTH_SHORT).show();
                songTitleInput.setText("");
                songSingerInput.setText("");
                songYearInput.setText("");
                starRg.check(R.id.radioButton6);

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                songArr.clear();

                ArrayList<Song> songList = db.getSongs();
                db.close();

                for (int i = 0; i < songList.size(); i++) {
                    Log.d("Database Content", i + ". " + songList.get(i));
                    songArr.add(songList.get(i).toString());
                }

                Intent intent = new Intent(MainActivity.this, Listview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        starRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadioButtonId = checkedId;
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
            return true;

        } else if (id == R.id.showList) {
            Intent intent = new Intent(MainActivity.this, Listview.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String title = prefs.getString("title", "");
        String singers = prefs.getString("singers", "");
        String year = prefs.getString("year", "");

        int selectedRadioButton = prefs.getInt("selectedRadioButton", -1);
        if (selectedRadioButton != -1) {
            RadioButton radioButton = findViewById(selectedRadioButton);
            radioButton.setChecked(true);
        }

        songTitleInput.setText(title);
        songSingerInput.setText(singers);
        songYearInput.setText(year);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();

        RadioGroup radioGroup = findViewById(R.id.starRg);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        prefEdit.putInt("selectedRadioButton", selectedRadioButtonId);
        prefEdit.putString("title", songTitleInput.getText().toString());
        prefEdit.putString("singers", songSingerInput.getText().toString());
        prefEdit.putString("year", songYearInput.getText().toString());
        prefEdit.commit();
    }

}