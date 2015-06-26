package com.jazter.myappportfolio.spotify.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.jazter.myappportfolio.R;
import com.jazter.myappportfolio.spotify.adapters.ArtistListAdapter;
import com.jazter.myappportfolio.spotify.adapters.TrackListAdapter;
import com.jazter.myappportfolio.spotify.utils.ArtistItem;
import com.jazter.myappportfolio.spotify.utils.TrackItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TracksActivity extends ActionBarActivity {
    SpotifyApi api = new SpotifyApi();
    ArrayList<TrackItem> trackItems = new ArrayList<TrackItem>();
    TrackListAdapter trackListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_tracks);

        Bundle b = this.getIntent().getExtras();

        final ListView listView = (ListView) findViewById(R.id.tracks_list_view);

        trackListAdapter = new TrackListAdapter(this, R.layout.track_layout_item, trackItems);

        listView.setAdapter(trackListAdapter);

        if (b != null){
            String artistId = b.getString("artist");
            showArtist(artistId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spotify_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showArtist(String artistId){
        trackItems.clear();
        SpotifyService spotify = api.getService();
        Map<String, Object> map = new HashMap<>();
        map.put("country", Locale.getDefault().getCountry());
        spotify.getArtistTopTrack(artistId, map, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {

                if (tracks.tracks.size() > 0) {
                    for (int i = 0; i < tracks.tracks.size(); i++) {
                        Track track = tracks.tracks.get(i);

                        String url = "http://animalosis.com/wp-content/uploads/2008/06/gato.jpg";
                        if (track.album.images.size() > 0) {
                            url = track.album.images.get(0).url;
                        }
                        TrackItem item = new TrackItem(track.id, track.name, track.album.name, url);
                        trackItems.add(item);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trackListAdapter.notifyDataSetChanged();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CharSequence msg = "No songs found for this Artist";
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, msg, duration);
                            toast.show();
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String msg = error.getResponse().getReason();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, msg, duration);
                toast.show();
            }
        });
    }
}
