package opendatacc.hackathon.pockettour.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathonopendatacc.pockettour.R;

import java.io.InputStream;
import java.util.List;

import opendatacc.hackathon.pockettour.model.place_json_schema.MuseumJSON;
import opendatacc.hackathon.pockettour.services.MuseumService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScrollingActivity extends AppCompatActivity {

    private static final String LAYAR = "http://m.layar.com/open/caceresview";
    private static int museumId;
    private static String description;
    private TextView mMuseumDescription;
    private WebView mWebView;
    private FloatingActionButton mButton;
    private static Toolbar mToolbar;

    protected final static String KEY_PLACE_ID = "place_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mButton = (FloatingActionButton) findViewById(R.id.fab);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webPage = Uri.parse(LAYAR);
                Intent layarIntent = new Intent(Intent.ACTION_VIEW, webPage);
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(layarIntent, 0);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(layarIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Debes tener Layar instalado", Toast.LENGTH_LONG);
                }
            }
        });
        mMuseumDescription = (TextView) findViewById(R.id.museum_description);
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("file:///android_asset/pruebaIDSIG.html");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        museumId = getIntent().getIntExtra(KEY_PLACE_ID, 1);
        description = "";
        switch (museumId) {
            case 1:
                description = getResources().getString(R.string.museum_1);
                break;
            case 2:
                description = getResources().getString(R.string.museum_2);
                break;
            case 4:
                description = getResources().getString(R.string.museum_4);
                break;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://qss.unex.es:9000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MuseumService service = retrofit.create(MuseumService.class);
        Call<MuseumJSON> call = service.getMuseumByID(museumId);
        call.enqueue(new Callback<MuseumJSON>() {
            @Override
            public void onResponse(Call<MuseumJSON> call, Response<MuseumJSON> response) {
                try {
                    new DownloadImageTask((ImageView) findViewById(R.id.main_backdrop))
                            .execute(response.body().getImage());
                    mToolbar.setTitle(response.body().getNombre().getValue());
                    setSupportActionBar(mToolbar);
                    mMuseumDescription.setText(description);
                    String LatLong = response.body().getLat().getValue() + "," + response.body().getLong().getValue();
                    mWebView.loadUrl("javascript:addPoint(" + LatLong + ")");
                } catch (NullPointerException e) {
                    error();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MuseumJSON> call, Throwable t) {
                error();
            }

            public void error() {
                ((ImageView) findViewById(R.id.main_backdrop)).setImageDrawable(getDrawable(R.drawable.no_image));
                mToolbar.setTitle("Error");
                setSupportActionBar(mToolbar);
                mMuseumDescription.setText("No se pueden obtener datos");
                Toast.makeText(getApplicationContext(), "Hay un problema con tu conexión a Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        private DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                onFail();
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

        public void onFail() {
            bmImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image, null));
        }
    }


}
