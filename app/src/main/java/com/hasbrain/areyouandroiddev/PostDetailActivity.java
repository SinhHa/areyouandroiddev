package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.NetworkBasedFeedDatastore;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sinhhx on 11/1/16.
 */
public class PostDetailActivity extends AppCompatActivity {

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras =getIntent().getExtras();
        setContentView(R.layout.postdetail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetailActivity.this,PostListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent

                );
            }
        });
        final TextView displayselftxt = (TextView)findViewById(R.id.displayselftext);

            List<RedditPost> list = (List<RedditPost>) extras.getSerializable("listpost");
        int position = extras.getInt("position");

                getSupportActionBar().setTitle(list.get(position).getTitle());
                getSupportActionBar().setSubtitle(list.get(position).getAuthor());
                if(list.get(position).getSelftxt().trim().length()==0){
                    displayselftxt.setText(list.get(position).getUrl());

                }
                else {

                    displayselftxt.setText(list.get(position).getSelftxt());
                }
            }



    }

