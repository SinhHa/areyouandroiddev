package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.List;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.WebViewDisplay;

import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by sinhhx on 8/30/16.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
        private List<RedditPost> listpost;
    Context context;
    public RecyclerAdapter(Context context,List<RedditPost> listpost){
    this.listpost =listpost;
    this.context =context;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
       public TextView postid;
        public TextView Author;
        public TextView site;
        public TextView title;
        public TextView subreddit;

        public ViewHolder(View itemView) {
            super(itemView);
            postid =(TextView) itemView.findViewById(R.id.ContentId);
            Author= (TextView) itemView.findViewById(R.id.Author);
            site= (TextView) itemView.findViewById(R.id.site);
            title = (TextView) itemView.findViewById(R.id.Tittle);
            subreddit= (TextView) itemView.findViewById(R.id.Subreddit);

        }
  }
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        v.setBackgroundColor(Color.WHITE);

        ViewHolder vh = new ViewHolder(v);
        return vh;
       }



    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {

        holder.postid.setText(" " + listpost.get(position).getId());
        holder.Author.setText(listpost.get(position).getAuthor());
        holder.site.setText(listpost.get(position).getSubreddit());
        holder.title.setText(listpost.get(position).getTitle());
        if(listpost.get(position).isStickyPost()==true){
            holder.title.setTextColor(Color.GREEN);
        }
        else {
            holder.title.setTextColor(Color.BLACK);
        }
        float Titletxtsize = (holder.Author.getTextSize()) * 0.4F;
        holder.title.setTextSize(Titletxtsize);
        String commentdisplay = listpost.get(position).getCommentCount() + " comments * " + "reddit" + " *" + listpost.get(position).getCreatedUTC();
        holder.subreddit.setText(commentdisplay);

    }


    @Override
    public int getItemCount() {
       return listpost.size();
    }


}
