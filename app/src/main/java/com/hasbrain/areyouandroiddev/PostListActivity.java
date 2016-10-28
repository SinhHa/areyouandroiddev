package com.hasbrain.areyouandroiddev;




import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;

import com.hasbrain.areyouandroiddev.datastore.NetworkBasedFeedDatastore;
import com.hasbrain.areyouandroiddev.model.CustomAdapter;
import com.hasbrain.areyouandroiddev.model.CustomGridAdapter;

import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.WebViewDisplay;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.content.Intent;

import java.util.List;

import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

public class PostListActivity extends AppCompatActivity {
    private ListView listview;
    private View footerview ;
    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;
    private RecyclerView mRecyclerView;
    private RecyclerView mGridRecycleView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar spinner;
    private RecyclerView.LayoutManager mLayoutManager;
    String after ="";

    public PostListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());


        feedDataStore = new NetworkBasedFeedDatastore(this);
        footerview= getLayoutInflater().inflate(R.layout.progress,null );
        displaydata();


        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
            swipeLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        displaydata();
                    }
                },2000);

            }

        });


}

    protected void displayPostList(final List<RedditPost> postList) {
        //TODO: Display post list.



    listview = (ListView) findViewById(R.id.ListContent);
    spinner = (ProgressBar)footerview.findViewById(R.id.progressBar1);



        final CustomAdapter adapter = new CustomAdapter(this, postList);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                intent.putExtra("link", postList.get(position).getUrl());
                startActivity(intent);
            }
        });

    listview.setOnScrollListener(new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(firstVisibleItem + visibleItemCount >= totalItemCount){


                    if(listview.getFooterViewsCount()==0){
                        listview.addFooterView(spinner);}

                    spinner.setVisibility(View.VISIBLE);

                     displaydata();
                    listview.smoothScrollToPosition(totalItemCount-3);



            }
        }
    });

    }


    public void displayGrid(final List<RedditPost> post) {
        final GridView gridView = (GridView) findViewById(R.id.gridview);
        CustomGridAdapter adapter = new CustomGridAdapter(this, post);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                intent.putExtra("link", post.get(i).getUrl());
                startActivity(intent);
            }
        });
    }

    public void displayRecycleList(final List<RedditPost> listpost) {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecycleGridAdapter(PostListActivity.this, listpost);
        mRecyclerView.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(PostListActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childview = rv.findChildViewUnder(e.getX(), e.getY());
                if (childview != null && mGestureDetector.onTouchEvent(e))  {

                    Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                    intent.putExtra("link", listpost.get(mRecyclerView.getChildAdapterPosition(childview)).getUrl());
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });



    }

    public void displayGridRecycler(final List<RedditPost> listpost) {

        mGridRecycleView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mGridRecycleView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new GridLayoutManager(this, 3);
        mGridRecycleView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecycleGridAdapter(PostListActivity.this, listpost);
        mGridRecycleView.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(PostListActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }
        });
        mGridRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childview = rv.findChildViewUnder(e.getX(), e.getY());


                if (childview != null && mGestureDetector.onTouchEvent(e))  {

                    Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                    intent.putExtra("link", listpost.get(mGridRecycleView.getChildAdapterPosition(childview)).getUrl());
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void expand(final List<RedditPost> post) {




        List<ExpandableRecycler.Item> data = new ArrayList<>();


        data.add(new ExpandableRecycler.Item(ExpandableRecycler.HEADER,post.get(0), "Sticky Post"));
        for (int i = 0; i < post.size(); i++) {
            if (post.get(i).isStickyPost() == true) {
                data.add(new ExpandableRecycler.Item(ExpandableRecycler.CHILD, post.get(i), ""));
            }
        }
        ExpandableRecycler.Item places = new ExpandableRecycler.Item(ExpandableRecycler.HEADER, post.get(0), "Normal Post");
        places.invisibleChildren = new ArrayList<>();
        for (int i = 0; i < post.size(); i++) {
            if (post.get(i).isStickyPost() == false) {
                places.invisibleChildren.add(new ExpandableRecycler.Item(ExpandableRecycler.CHILD, post.get(i), ""));
            }
        }


        data.add(places);

        mRecyclerView.setAdapter(new ExpandableRecycler(data));
        final GestureDetector mGestureDetector = new GestureDetector(PostListActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childview = rv.findChildViewUnder(e.getX(), e.getY());

                int pos = mRecyclerView.getChildAdapterPosition(childview);
               // childview.getT
                if(mRecyclerView.getChildCount() >2){
                if (childview != null && mGestureDetector.onTouchEvent(e) && pos < 3 && pos >0) {


                    Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                    intent.putExtra("link", post.get(pos - 1).getUrl());
                    startActivity(intent);
                }
                if (childview != null && mGestureDetector.onTouchEvent(e) && pos >3 ) {
                    int x = 0;

                    Intent intent = new Intent(PostListActivity.this, WebViewDisplay.class);
                    intent.putExtra("link", post.get(pos - 2).getUrl());
                    startActivity(intent);
                }
                }


                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    public void displaydata(){

        feedDataStore.getPostList("https://www.reddit.com/r/androiddev/new.json", "good", after, new FeedDataStore.OnRedditPostsRetrievedListener() {
            @Override
            public void onRedditPostsRetrieved(List<RedditPost> postList, String After, Exception ex) {
                if(postList!=null){
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    displayGrid(postList);
                    after = postList.get(postList.size()-1).getName();
                    }
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                    displayPostList(postList);
                    after = postList.get(postList.size()-1).getName();

                }
            } else{
                    Intent intent = new Intent(PostListActivity.this,RetryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }}
        });

    }
    protected int getLayoutResource() {

        return R.layout.activity_post_list;
    }
}

