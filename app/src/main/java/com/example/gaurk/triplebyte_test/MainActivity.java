package com.example.gaurk.triplebyte_test;

import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.AbsListView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String restResponseString;
    ArrayList<CatListObject> catListObjectsList;
    private CustomListAdapter customListAdapter;
    private ListView cat_list_view;
    private SimpleDateFormat format;
    private SimpleDateFormat newDateFormat;
    private String new_date;
    String fetch_profile_url = "https://chex-triplebyte.herokuapp.com/api/cats?page=";
    int scroll_count = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        newDateFormat = new SimpleDateFormat("MM/dd/YY");


        cat_list_view = (ListView) findViewById(R.id.catlistview);
        catListObjectsList = new ArrayList<>();

        fetch_profile_url = "https://chex-triplebyte.herokuapp.com/api/cats?page=" + scroll_count;

        new Rest_call().execute();

        cat_list_view.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (cat_list_view.getLastVisiblePosition() - cat_list_view.getHeaderViewsCount() -
                        cat_list_view.getFooterViewsCount()) >= (cat_list_view.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    Log.d("HIT BOTTOM ", "> ");
                    scroll_count++;
                    fetch_profile_url = "https://chex-triplebyte.herokuapp.com/api/cats?page=" + scroll_count;

                    new Rest_call().execute();

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    private class Rest_call extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


//            String fetch_profile_url = "https://api.instagram.com/v1/users/512588310/media/recent/?access_token=" + accessToken;
            Log.d("fetch_profile_url: ", "> " + fetch_profile_url);

            ServiceHandler sh = new ServiceHandler();
            restResponseString = sh.makeServiceCall(fetch_profile_url, ServiceHandler.GET);
            Log.d("Response: ", "> " + restResponseString);

            restResponseString = "{\"data\":" + restResponseString + "}";
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            parse_json(restResponseString);

        }
    }

    private void parse_json(String restResponseString) {

        System.out.println(" IN PARSE JASON");

        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(restResponseString);
            JSONArray jsonArray = mainObject.getJSONArray("data");

//            System.out.println(" jsonArray jsonArray " + jsonArray);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject cat_object = jsonArray.getJSONObject(j);
//                System.out.println(" posts_object  " + cat_object);

                String title = cat_object.getString("title");
                System.out.println(" title title " + title);
                String timestamp = cat_object.getString("timestamp");
                System.out.println(" timestamp timestamp " + timestamp);
                String url = cat_object.getString("image_url");
                System.out.println(" url url " + url);
                String description = cat_object.getString("description");
                System.out.println(" description description " + description);


                try {
                    Date date = format.parse(timestamp);
                    System.out.println(date);

                    new_date = newDateFormat.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                catListObjectsList.add(new CatListObject(url, description, new_date, title));

            }
            System.out.println(" catListObjectsListn " + catListObjectsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        populate_list();
    }

    private void populate_list() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        customListAdapter = new CustomListAdapter(catListObjectsList, this, width * 90 / 100);
        cat_list_view.setAdapter(customListAdapter);

        cat_list_view.setSelection(scroll_count*10);

    }

}
