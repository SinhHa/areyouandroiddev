package com.hasbrain.areyouandroiddev.model;

import android.content.Context;
import android.graphics.Color;
import android.widget.BaseAdapter;
import com.hasbrain.areyouandroiddev.R;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by sinhhx on 8/24/16.
 */
public class CustomGridAdapter extends BaseAdapter {
    private final Context context;
    private final List<RedditPost> listReddit;

    public CustomGridAdapter(Context context, List<RedditPost> listReddit) {

        this.listReddit = listReddit;
        this.context = context;
    }
    static class ViewHolder {
        TextView postid;
        TextView Author;
        TextView title;
        TextView comment;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RedditPost post = listReddit.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditems, parent, false);
            holder.postid = (TextView) convertView.findViewById(R.id.Id);
            holder.Author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);


            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }
        if(post.isStickyPost()==true){
            holder.title.setTextColor(Color.GREEN);
        }
        else {
            holder.title.setTextColor(Color.WHITE);
        }
        String commentdisplay = post.getCommentCount() + " comments * " + "reddit" + " *" + post.getCreatedUTC();
        float Titletxtsize = (holder.Author.getTextSize()) * 0.4F;
        holder.title.setTextSize(Titletxtsize);
        holder.postid.setText(" " + post.getScore());
        holder.Author.setText(post.getAuthor());
        holder.comment.setText(commentdisplay);
        holder.title.setText(post.getTitle());
        return convertView;


    }

        @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
