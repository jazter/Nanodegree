package com.jazter.myappportfolio.spotify.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jazter.myappportfolio.R;
import com.jazter.myappportfolio.spotify.adapters.ArtistListAdapter;
import com.jazter.myappportfolio.spotify.utils.ArtistItem;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchActivity extends ActionBarActivity {
    SpotifyApi api = new SpotifyApi();
    ArrayList<ArtistItem> artistItems = new ArrayList<ArtistItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_search);

        final TextView search = (TextView)findViewById(R.id.editText);

        final ListView listView = (ListView) findViewById(R.id.artists_list_view);

        final ArtistListAdapter artistListAdapter = new ArtistListAdapter(this, R.layout.artist_layout_item, artistItems);

        listView.setAdapter(artistListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArtistItem artistItem = (ArtistItem)adapterView.getAdapter().getItem(i);
                if (artistItem != null) {
                    Intent intent = new Intent(SearchActivity.this, TracksActivity.class);
                    Bundle b = new Bundle();
                    b.putString("artist", artistItem.getArtistId());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                artistItems.clear();
                SpotifyService spotify = api.getService();
                spotify.searchArtists(charSequence.toString(), new Callback<ArtistsPager>() {
                    @Override
                    public void success(ArtistsPager artistsPager, Response response) {
                        for (int i = 0; i < artistsPager.artists.items.size(); i++){
                            Artist artist = artistsPager.artists.items.get(i);

                            String url = "http://animalosis.com/wp-content/uploads/2008/06/gato.jpg";
                            if (artist.images.size() > 0){
                                url = artist.images.get(0).url;
                            }
                            ArtistItem item = new ArtistItem(artist.id, artist.name, url);
                            artistItems.add(item);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                artistListAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spotify_search, menu);
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
}
