package com.example.kyancafe.foodorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.kyancafe.foodorder.SettingActivity.sharedPref;

public class DrinkListActivity extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    String response;
    ListView listView;
    Context context=this;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DrinkItemDataModel> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_list);



        recyclerView = (RecyclerView) findViewById(R.id.menulistrecycler2);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DrinkListActivity.AsyncGet obj=new DrinkListActivity.AsyncGet();
        obj.execute();






/*        Log.i("a123456789","qwertyui");
// Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.list);


        //new AsyncGet().execute();
        AsyncGet obj=new AsyncGet();
        obj.execute();
        //response=obj.getjsonString();
        Log.i("a123456789","12");
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(response);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ArrayList<customer_menu_data> itemslist = customer_menu_data.fromJson(jsonArray);
//        UsersAdapter adapter = new UsersAdapter(this, itemslist);
//        adapter.addAll(itemslist);
//        listView.setAdapter(adapter);

*/
       /* FloatingActionButton m_fab=(FloatingActionButton)findViewById(R.id.m_fab);
        m_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuListActivity.this,MainActivity.class);
                startActivity(i);
            }


        });*/
        ImageView gotoCart = findViewById(R.id.gotocart);
        ImageView backIcon = findViewById(R.id.backButton);
        TextView judul = findViewById(R.id.judul);

        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DrinkListActivity.this,CartActivity.class);
                startActivity(i);
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DrinkListActivity.this,MenuListActivity.class);
                startActivity(i);
            }
        });
        judul.setText("Drinks");

        Button foodsButton = (Button)findViewById(R.id.foodButton);

        foodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrinkListActivity.this, MenuListActivity.class));
            }
        });

        Button dessertsButton = (Button)findViewById(R.id.dessertButton);

        dessertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrinkListActivity.this, DessertListActivity.class));
            }
        });

    }






    private class AsyncGet extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(DrinkListActivity.this);
        HttpURLConnection conn;
        URL url = null;
        String results="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://apikyancafe.flaviary.online/drink.php");
                //url = new URL(LoginActivity.weburl+"drink.php");

                Log.i("url1",url.toString());

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                //Uri.Builder builder = new Uri.Builder();

                //String query = builder.build().getEncodedQuery();
                Log.i("url2",url.toString());

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                //               writer.write(query);
                writer.write(url.toString());

                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    results=result.toString();
                    response=results;



                    Log.i("result",results);
                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*ArrayList<customer_menu_data> itemslist = customer_menu_data.fromJson(jsonArray);
            UsersAdapter adapter = new UsersAdapter(MenuListActivity.this, itemslist);
            //adapter.addAll(itemslist);
            listView.setAdapter(adapter);*/

            data=DrinkItemDataModel.fromJson(jsonArray);
            adapter=new MenuItemAdapter2(context,data);
            recyclerView.setAdapter(adapter);


            if(result!=null)
            {

                Toast.makeText(DrinkListActivity.this, "Items Fetched successfully", Toast.LENGTH_LONG).show();

            }else{

                // If username and password does not match display a error message
                Toast.makeText(DrinkListActivity.this, "Fetching Failed.", Toast.LENGTH_LONG).show();

            }
        }
        String getjsonString()
        {
            return results;
        }

    }

}
