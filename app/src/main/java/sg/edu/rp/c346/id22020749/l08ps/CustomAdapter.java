package sg.edu.rp.c346.id22020749.l08ps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> songList;

    public CustomAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView listTitle = rowView.findViewById(R.id.listTitle);
        TextView listYear = rowView.findViewById(R.id.listYear);
        TextView listRating = rowView.findViewById(R.id.listRating);
        TextView listSingers = rowView.findViewById(R.id.listSingers);

        Song currentSong = songList.get(position);

        listTitle.setText(currentSong.getTitle());
        listYear.setText(Integer.toString(currentSong.getYear()) + ": ");
        listRating.setText(currentSong.getStarRating(currentSong.getStars()));
        listSingers.setText(currentSong.getSingers());

        return rowView;
    }

}