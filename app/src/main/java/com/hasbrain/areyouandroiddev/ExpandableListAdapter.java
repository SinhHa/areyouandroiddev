package com.hasbrain.areyouandroiddev;

import android.app.ExpandableListActivity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.widget.TextView;
import android.graphics.Typeface;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sinhhx on 8/30/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private HashMap<String,List<RedditPost>> postlist;
    private List<String> header;
public ExpandableListAdapter(Context context,HashMap<String,List<RedditPost>> postlist,List<String>header){
    this.context = context;
    this.postlist = postlist;
    this.header= header;
}

    static class ViewHolder {
        TextView postid;
        TextView Author;
        TextView site;
        TextView title;
        TextView subreddit;


    }
    @Override
    public int getGroupCount() {
        return this.header.size();
    }

    @Override
    public int getChildrenCount(int groupsize) {
        return this.postlist.get(header.get(groupsize)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.header.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.postlist.get(this.header.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupposition, int childposition) {
        return childposition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandableheader, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListItem);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupposition, int childposition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        RedditPost post = postlist.get(header.get(groupposition)).get(childposition);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem, null, false);
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



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
