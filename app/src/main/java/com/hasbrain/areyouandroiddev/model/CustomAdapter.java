package com.hasbrain.areyouandroiddev.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<RedditPost> {
    private final Context context;
    private final List<RedditPost> listReddit;

    public CustomAdapter(Context context, List<RedditPost> listReddit) {
        super(context, R.layout.activity_post_in_section, listReddit);
        this.listReddit = listReddit;
        this.context = context;
    }

    static class ViewHolder {
        TextView postid;
        TextView Author;
        TextView site;
        TextView title;
        TextView subreddit;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RedditPost post = listReddit.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem, parent, false);
            holder.postid = (TextView) convertView.findViewById(R.id.ContentId);
            holder.Author = (TextView) convertView.findViewById(R.id.Author);
            holder.site = (TextView) convertView.findViewById(R.id.site);
            holder.title = (TextView) convertView.findViewById(R.id.Tittle);
            holder.subreddit = (TextView) convertView.findViewById(R.id.Subreddit);


            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }


        holder.postid.setText(" " + post.getScore());
        holder.Author.setText(post.getAuthor());
        holder.site.setText(post.getSubreddit());
        holder.title.setText(post.getTitle());
        if(post.isStickyPost()==true){
            holder.title.setTextColor(Color.GREEN);
        }
        else {
            holder.title.setTextColor(Color.BLACK);
        }
        float Titletxtsize = (holder.Author.getTextSize()) * 0.4F;
        holder.title.setTextSize(Titletxtsize);
        String commentdisplay = post.getCommentCount() + " comments * " + "reddit" + " *" + post.getCreatedUTC();
        holder.subreddit.setText(commentdisplay);
        convertView.setBackgroundColor(Color.WHITE);
        return convertView;
    }
}
