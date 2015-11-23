package com.ac.alan.connectedshelters;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainActivity extends AppCompatActivity {

    String url ="http://opendata.paris.fr/api/records/1.0/search?dataset=abri-voyageurs-ecrans-tactiles-connectes&rows=78";

    private ListView listViewShelters;
    private ListAdapter listAdapter;
    private String shelterAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);

        //final TextView mTextView = (TextView) findViewById(R.id.tv1);
        listViewShelters = (ListView) findViewById(R.id.lv1);


        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                //if its works
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //We put all the data from the api in shelter
                            final Shelters shelter = objectMapper.readValue(response, new TypeReference<Shelters>(){});
                            Storage.all_shelter = shelter;
                            //mTextView.setText(""+shelter); //show our data from our api.

                            //here the adapter will get our record and put it in our listview
                            listAdapter = new com.ac.alan.connectedshelters.ListAdapter(MainActivity.this, shelter.getRecords());
                            listViewShelters.setAdapter(listAdapter);
                            listViewShelters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                    Record currentRecord = (Record) listAdapter.getItem(position);
                                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                                    intent.putExtra(Tag.TAG_SHELTER_ADRESS,currentRecord.getFields().getAdresse());
//                                    intent.putExtra(Tag.TAG_SHELTER_X, (currentRecord.getFields().getXy())[0]);
//                                    intent.putExtra(Tag.TAG_SHELTER_Y, (currentRecord.getFields().getXy())[1]);
                                    startActivity(intent);





                                }

                                });
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            /* TODO */
                        //  mTextView.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
