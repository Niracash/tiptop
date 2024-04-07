package com.example.tiptop;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    RecyclerView contentList;
    ContentAdapter adapter;
    List<Content> allContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        allContent = new ArrayList<>();

        contentList = findViewById(R.id.contentList);
        contentList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContentAdapter(this,allContent);
        contentList.setAdapter(adapter);

        getJsonData();


    }

    private void getJsonData() {
        String url = "https://raw.githubusercontent.com/Niracash/DATA/main/data.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try{
                    JSONArray categories = jsonObject.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                    for(int i = 0; i<videos.length(); i++){
                        JSONObject video = videos.getJSONObject(i);
                        //Log.d(TAG, "onResponse: " + video.getString("title"));

                        Content content = new Content();
                        content.setTitle(video.getString("title"));
                        content.setDescription(video.getString("description"));
                        content.setAuthor(video.getString("subtitle"));
                        content.setThumbnailUrl(video.getString("thumb"));

                        JSONArray videoUrl = video.getJSONArray("sources");
                        content.setVideoUrl(videoUrl.getString(0));

                        //Log.d(TAG, "onResponse: " + content.getVideoUrl());

                        allContent.add(content);
                        adapter.notifyDataSetChanged();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "onErrorResponse: " + volleyError.getMessage());

            }
        });
        requestQueue.add(objectRequest);
    }
}