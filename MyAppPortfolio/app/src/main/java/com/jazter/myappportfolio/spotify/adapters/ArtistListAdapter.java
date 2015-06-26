package com.jazter.myappportfolio.spotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jazter.myappportfolio.R;
import com.jazter.myappportfolio.spotify.utils.ArtistItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jazter on 23/06/2015.
 */
public class ArtistListAdapter extends ArrayAdapter<ArtistItem> {

    public ArtistListAdapter(Context context, int resource, List<ArtistItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.artist_layout_item, null);
        }

        ArtistItem item = getItem(position);

        if (item != null){
            ImageView artistImg = (ImageView) view.findViewById(R.id.artist_img);
            TextView artistName = (TextView) view.findViewById(R.id.artist_name);

            if (artistImg != null){
                Picasso.with(getContext()).load(item.getArtistImageURL()).into(artistImg);
            }
            if (artistName !=  null){
                artistName.setText(item.getArtistName());
            }
        }

        return view;
    }
}
