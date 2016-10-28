package com.hasbrain.areyouandroiddev.datastore;

import android.content.Context;
import android.os.AsyncTask;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hasbrain.areyouandroiddev.MySingleton;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinhhx on 10/19/16.
 */
public class NetworkBasedFeedDatastore implements FeedDataStore {
    private String baseUrl;
    Context context;
    List<RedditPost> postlist = new ArrayList<RedditPost>();
    JSONArray data;
    String url;


    public NetworkBasedFeedDatastore(Context context) {
        this.context = context;
    }

    @Override
    public void getPostList(String topic, String before, String after,
                            final OnRedditPostsRetrievedListener onRedditPostsRetrievedListener) {
        //TODO: Implement network calls.

    url=topic+"?after="+after+"&count=100";
     //  new getPost(onRedditPostsRetrievedListener).execute(url);

      JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    List<RedditPost> temp;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            data = response.getJSONObject("data").getJSONArray("children");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
                            Gson gson = gsonBuilder.create();
                            Type type = new TypeToken<List<RedditPost>>() {
                            }.getType();


                            try {
                                temp = gson.fromJson(data.toString(4), type);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    postlist.addAll(temp);



                        onRedditPostsRetrievedListener.onRedditPostsRetrieved(postlist,postlist.get(postlist.size()-1).getName(), null);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onRedditPostsRetrievedListener.onRedditPostsRetrieved(null,"", null);

                    }
                });

        MySingleton.getmInstance(context).addRequest(jsObjRequest);

    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    private class getPost extends AsyncTask<String, Integer, List<RedditPost>> {
        OnRedditPostsRetrievedListener listener;

        private getPost(OnRedditPostsRetrievedListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<RedditPost> doInBackground(String... strings) {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        List<RedditPost> temp;

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                data = response.getJSONObject("data").getJSONArray("children");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
                            Gson gson = gsonBuilder.create();
                            Type type = new TypeToken<List<RedditPost>>() {
                            }.getType();


                            try {
                                temp = gson.fromJson(data.toString(4), type);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            postlist.addAll(temp);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
                    });
            MySingleton.getmInstance(context).addRequest(jsObjRequest);
            return postlist;
        }

        protected void onPostExecute(List<RedditPost> postlist) {
            if(postlist.size()>0){
            listener.onRedditPostsRetrieved(postlist,postlist.get(postlist.size()-1).getName(), null);
        }}
    }
}
