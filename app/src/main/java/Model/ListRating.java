package Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ratingreview.R;

import java.util.List;

public class ListRating extends ArrayAdapter<Rating> {

    private Activity context;
    List<Rating> artists;

    public ListRating(Activity context, List<Rating> artists) {
        super(context, R.layout.layout_ratinglist, artists);
        this.context = context;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_ratinglist, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        RatingBar ratelist = (RatingBar) listViewItem.findViewById(R.id.listrate);

        Rating artist = artists.get(position);
        textViewName.setText(artist.getId());
        textViewGenre.setText(artist.getComment());
        ratelist.setRating(Float.parseFloat(artist.getRatestar()));

        return listViewItem;
    }
}
