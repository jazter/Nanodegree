package com.jazter.myappportfolio.spotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jazter.myappportfolio.R;
import com.jazter.myappportfolio.spotify.utils.TrackItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jazter on 23/06/2015.
 */
public class TrackListAdapter extends ArrayAdapter<TrackItem> {

    public TrackListAdapter(Context context, int resource, List<TrackItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.track_layout_item, null);
        }

        TrackItem item = getItem(position);

        if (item != null){
            ImageView albumImg = (ImageView) view.findViewById(R.id.album_img);
            TextView trackName = (TextView) view.findViewById(R.id.track_name);
            TextView albumName = (TextView) view.findViewById(R.id.album_name);

            if (albumImg != null){
                Picasso.with(getContext()).load(item.getAlbumImageURL()).into(albumImg);
            }
            if (trackName !=  null){
                trackName.setText(item.getTrackName());
            }
            if (albumName !=  null){
                albumName.setText(item.getAlbumName());
            }
        }

        return view;
    }
}
