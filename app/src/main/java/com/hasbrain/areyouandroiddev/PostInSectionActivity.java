package com.hasbrain.areyouandroiddev;

import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.WebViewDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import java.util.concurrent.locks.ReentrantLock;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends PostListActivity {
    List<String> dataheader = new ArrayList<String>();
    HashMap<String,List<RedditPost>> listdata = new HashMap<String, List<RedditPost>>();
    ExpandableListAdapter explistAdapter;
    ExpandableListView expandableList;

    @Override
    protected void displayPostList(final List<RedditPost> postList) {




        preparedata(postList);
        explistAdapter = new ExpandableListAdapter(this,listdata,dataheader);

        expandableList.setAdapter(explistAdapter);
        expandableList.setDividerHeight(40);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(PostInSectionActivity.this, WebViewDisplay.class);
                intent.putExtra("link", postList.get(i).getUrl());
                startActivity(intent);

                return false;
            }
        });

    }

public void preparedata(final List<RedditPost> postList){
    dataheader.add(0,"Sticky Posts");
    dataheader.add(1,"Normal Posts");
    List<RedditPost> stickypost= new ArrayList<RedditPost>();
    List<RedditPost> normalpost=new ArrayList<RedditPost>();
    for(int i=0;i<postList.size();i++){
        if(postList.get(i).isStickyPost()==true){
            stickypost.add(postList.get(i));
        }
        if(postList.get(i).isStickyPost()==false){
            normalpost.add(postList.get(i));
        }
    }
    listdata.put(dataheader.get(0),stickypost);
    listdata.put(dataheader.get(1),normalpost);
    expandableList = (ExpandableListView)findViewById(R.id.explist);

        }





    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }
}
