package sg.edu.rp.c346.id22020749.l08ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText songId, songTitle, songSingers ,songYear;
    RadioGroup songRating;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    private int selectedRadioButtonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        songId = findViewById(R.id.songIdInput);
        songTitle = findViewById(R.id.editSongTitleInput);
        songSingers = findViewById(R.id.editSongSingersInput);
        songYear = findViewById(R.id.editSongYearInput);
        songRating = findViewById(R.id.editStarRg);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);


        Intent intent = getIntent();
        data = (Song) intent.getSerializableExtra("data");

        songId.setText(Integer.toString(data.getId()));
        songTitle.setText(data.getTitle());
        songSingers.setText(data.getSingers());
        songYear.setText(Integer.toString(data.getYear()));

        switch(data.getStars()) {
            case 1:
                songRating.check(R.id.radioButton6);
                break;
            case 2:
                songRating.check(R.id.radioButton7);
                break;
            case 3:
                songRating.check(R.id.radioButton8);
                break;
            case 4:
                songRating.check(R.id.radioButton9);
                break;
            case 5:
                songRating.check(R.id.radioButton10);
                break;
        }

        songId.setEnabled(false);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int starBtn = songRating.getCheckedRadioButtonId();

                DBHelper dbh = new DBHelper(ThirdActivity.this);
                data.setTitle(songTitle.getText().toString());
                data.setSingers(songSingers.getText().toString());
                data.setYear(Integer.parseInt(songYear.getText().toString()));

                if (starBtn ==  R.id.radioButton6)
                    data.setStars(1);
                else if (starBtn == R.id.radioButton7)
                    data.setStars(2);
                else if (starBtn == R.id.radioButton8)
                    data.setStars(3);
                else if (starBtn == R.id.radioButton9)
                    data.setStars(4);
                else if (starBtn == R.id.radioButton10)
                    data.setStars(5);
                else
                    data.setStars(0);

                dbh.updateSong(data);
                dbh.close();

                Intent intent = new Intent(ThirdActivity.this, Listview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                Toast.makeText(ThirdActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());

                Intent intent = new Intent(ThirdActivity.this, Listview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                Toast.makeText(ThirdActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, Listview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        songRating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadioButtonId = checkedId;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

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
            Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.showList) {
            Intent intent = new Intent(ThirdActivity.this, Listview.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}