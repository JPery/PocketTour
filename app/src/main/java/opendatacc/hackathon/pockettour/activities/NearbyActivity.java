package opendatacc.hackathon.pockettour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.hackathonopendatacc.pockettour.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import opendatacc.hackathon.pockettour.adapters.PlaceAdapter;
import opendatacc.hackathon.pockettour.model.PlaceItem;

public class NearbyActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "NearbyActivity";

    private static final int TTL_IN_SECONDS = 3 * 60; // Three minutes.

    /**
     * Sets the time in seconds for a published message or a subscription to live. Set to three
     * minutes in this sample.
     */
    private static final Strategy PUB_SUB_STRATEGY = new Strategy.Builder()
            .setTtlSeconds(TTL_IN_SECONDS).build();

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * A {@link MessageListener} for processing messages from nearby devices.
     */
    private MessageListener messageListener;

    @BindView(R.id.activity_main_container)
    View mainContainer;
    @BindView(R.id.toolbar_nearby)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_places)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    List<PlaceItem> placeItemDataSet;
    PlaceAdapter placeAdapter;
    SparseArray<PlaceItem> temporaryDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Lugares cercanos");
        }

        placeItemDataSet = new ArrayList<>();
        temporaryDataSet = new SparseArray<>();
        placeAdapter = new PlaceAdapter(placeItemDataSet, new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaceItem placeItem) {
                Intent detailActivityIntent = new Intent(NearbyActivity.this, ScrollingActivity.class);
                detailActivityIntent.putExtra(ScrollingActivity.KEY_PLACE_ID, placeItem.getId());
                startActivity(detailActivityIntent);
            }
        });

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(placeAdapter);

        messageListener = new MessageListener() {
            @Override
            public void onFound(final Message message) {
                // Called when a new message is found.
                Log.d(TAG, "Got a onFound call");
                // Do something with the message here.
                getContentFromMessage(message);
            }

            @Override
            public void onLost(final Message message) {
                // Called when a message is no longer detectable nearby.
                Log.d(TAG, "Got a onLost call");
                removeContent(message);
            }
        };

        buildGoogleApiClient();
    }

    private void getContentFromMessage(Message message) {

        String messageContent = new String(message.getContent());
        String messageData = messageContent.substring(4);
        int placeId = Character.getNumericValue(messageContent.charAt(2));

        if (temporaryDataSet.get(placeId) == null) {
            temporaryDataSet.put(placeId, new PlaceItem(placeId));
        }

        try {
            if (messageData.contains("opendata")) {
                temporaryDataSet.get(placeId).setResourceURL(new URL(messageData));
            } else if (messageData.contains("sig")) {
                temporaryDataSet.get(placeId).setPhotoLocation(messageData);
            } else {
                temporaryDataSet.get(placeId).setName(messageData);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        PlaceItem place = temporaryDataSet.get(placeId);
        if (place.getResourceURL() != null && place.getPhotoLocation() != null && place.getName() != null) {
            Log.d(TAG, "Adding place to adapter: " + place.getName() + " - " + place.getPhotoLocation());
            placeAdapter.add(place);
        }

    }

    private void removeContent(Message message) {
        String messageContent = new String(message.getContent());
        int placeId = Character.getNumericValue(messageContent.charAt(2));
        if (temporaryDataSet.get(placeId) != null) {
            placeAdapter.remove(placeId);
            temporaryDataSet.remove(placeId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitoring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        logAndShowSnackbar("Exception while connecting to Google Play services: " +
                connectionResult.getErrorMessage());
    }

    @Override
    public void onConnectionSuspended(int i) {
        logAndShowSnackbar("Connection suspended. Error code: " + i);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
        subscribe();
    }

    /**
     * Builds {@link GoogleApiClient}
     */
    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();
    }

    /**
     * Subscribes to messages from nearby devices and updates the UI if the subscription either
     * fails or TTLs.
     */
    private void subscribe() {
        Log.i(TAG, "Subscribing");
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(PUB_SUB_STRATEGY)
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer subscribing");
                    }
                }).build();

        Nearby.Messages.subscribe(mGoogleApiClient, messageListener, options)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Subscribed successfully.");
                        } else {
                            logAndShowSnackbar("Could not subscribe, status = " + status);
                        }
                    }
                });
    }

    /**
     * Logs a message and shows a {@link Snackbar} using {@code text};
     *
     * @param text The text used in the Log message and the SnackBar.
     */
    private void logAndShowSnackbar(final String text) {
        Log.w(TAG, text);
        View container = findViewById(R.id.activity_main_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }
}
