package com.volleyobject.vollject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // creating variables for our textview,
    // imageview,cardview and progressbar.
    private TextView courseNameTV, courseTracksTV, courseBatchTV;
    private ImageView courseIV;
    private ProgressBar loadingPB;
    private CardView courseCV;

    // below line is the variable for url from
    // where we will be querying our data.
    String url = "https://jsonkeeper.com/b/63OHaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // in below line we are initializing our all views.
        loadingPB = findViewById(R.id.idLoadingPB);
        courseCV = findViewById(R.id.idCVCourse);
        courseNameTV = findViewById(R.id.idTVCourseName);
        courseTracksTV = findViewById(R.id.idTVTracks);
        courseBatchTV = findViewById(R.id.idTVBatch);
        courseIV = findViewById(R.id.idIVCourse);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // as our data is in json object format so we are using
        // json object request to make data request from our url.
        // in below line we are making a json object
        // request and creating a new json object request.
        // inside our json object request we are calling a
        // method to get the data, "url" from where we are
        // calling our data,"null" as we are not passing any data.
        // later on we are calling response listener method
        // to get the response from our API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside the on response method.
                // we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // in below line we are making our card
                // view visible after we get all the data.
                courseCV.setVisibility(View.VISIBLE);
                try {
                    // now we get our response from API in json object format.
                    // in below line we are extracting a string with its key
                    // value from our json object.
                    // similarly we are extracting all the strings from our json object.
                    String courseName = response.getString("courseName");
                    String courseTracks = response.getString("courseTracks");
                    String courseMode = response.getString("courseMode");
                    String courseImageURL = response.getString("courseimg");

                    // after extracting all the data we are
                    // setting that data to all our views.
                    courseNameTV.setText(courseName);
                    courseTracksTV.setText(courseTracks);
                    courseBatchTV.setText(courseMode);

                    // we are using picasso to load the image from url.
                    Picasso.get().load(courseImageURL).into(courseIV);
                } catch (JSONException e) {
                    // if we do not extract data from json object properly.
                    // below line of code is use to handle json exception
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                // below line is use to display a toast message along with our error.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);
    }
}
