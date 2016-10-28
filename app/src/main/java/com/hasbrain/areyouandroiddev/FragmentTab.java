package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.NetworkBasedFeedDatastore;
import com.hasbrain.areyouandroiddev.model.CustomAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.WebViewDisplay;

import java.util.List;

/**
 * Created by sinhhx on 10/25/16.
 */
public class FragmentTab extends Fragment {
    private FeedDataStore feedDataStore;
    public static final String ARG_PAGE = "ARG_PAGE";
    String url;
    String afterpost="";
    String currentpost="";
    private int mPage;

    public static FragmentTab newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentTab fragment = new FragmentTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if(mPage==1){
            url="https://www.reddit.com/r/androiddev/hot.json";
        }
        if(mPage==2){
            url="https://www.reddit.com/r/movies/hot.json";
        }
        if(mPage==3){
            url="https://www.reddit.com/r/pics/hot.json";
        }
        if(mPage==4){
            url="https://www.reddit.com/r/food/hot.json";
        }
        if(mPage==5){
          url="https://www.reddit.com/r/comics/hot.json";
        }
        final View view = inflater.inflate(R.layout.activity_post_list, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.ListContent);
        View footerView = inflater.inflate(R.layout.progress, null);
        final ProgressBar spinner;
        spinner = (ProgressBar)footerView.findViewById(R.id.progressBar1);
        listview.addFooterView(spinner);

        feedDataStore = new NetworkBasedFeedDatastore(getContext());
        final FeedDataStore.OnRedditPostsRetrievedListener listener = new FeedDataStore.OnRedditPostsRetrievedListener() {
            @Override
            public void onRedditPostsRetrieved(final List<RedditPost> postList, String after, Exception ex) {

                if(postList!=null){

                    CustomAdapter adapter = new CustomAdapter(getContext(), postList);

                    listview.setAdapter(adapter);


                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {

                            Intent intent = new Intent(getContext(), WebViewDisplay.class);
                            intent.putExtra("link", postList.get(position).getUrl());
                            startActivity(intent);
                        }
                    });


                }else{
                    Intent intent = new Intent(getContext(),RetryActivity.class);
                    startActivity(intent);
                }

                afterpost=after;
            }
        };
        feedDataStore.getPostList(url, "good", currentpost, listener);




        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount>0){

                    if(totalItemCount <=100) {
                        spinner.setVisibility(View.VISIBLE);
                        currentpost =afterpost;
                        feedDataStore.getPostList(url, "good", currentpost, listener);
                        spinner.setVisibility(View.GONE);

                    }
                }
            }
        });
        feedDataStore.getPostList(url, "good", currentpost, listener);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                       feedDataStore.getPostList(url, "good",currentpost, listener);
                    }
                },2000);

            }

        });

        return view;





    }





}
class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "ANROID DEV", "MOVIES", "PIC","FOOD","MUSIC" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentTab.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
