package com.hasbrain.areyouandroiddev;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sinhhx on 10/19/16.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue queue;
    private static Context context;
    public MySingleton(Context context){
        this.context =context;
        queue = getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(queue == null){

        queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public static synchronized MySingleton getmInstance(Context context){
        if(mInstance==null ){
         mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public<T> void addRequest(Request<T> request){
        queue.add(request);
    }

}
