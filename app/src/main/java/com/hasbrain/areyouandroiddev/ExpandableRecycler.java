package com.hasbrain.areyouandroiddev;
import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by sinhhx on 9/6/16.
 *
 */
public class ExpandableRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;


    public ExpandableRecycler(List<Item> data) {

        this.data = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandableheader, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflaterer = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterer.inflate(R.layout.listitem, parent, false);
                ListHeaderViewHolder item = new ListHeaderViewHolder(view);
                return item;
                };

        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
        switch (item.type) {
            case HEADER:

                itemController.refferalItem = item;
                itemController.header_title.setText(item.Header);
                itemController.header_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);

                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);

                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                itemController.postid.setText(item.post.get().getId());
                itemController.Author.setText(item.post.get().getAuthor());
                itemController.site.setText(item.post.get().getSubreddit());
                itemController.title.setText(item.post.get().getTitle());
                String commentdisplay = item.post.get().getCommentCount() + " comments * " + "reddit" + " *" + item.post.get().getCreatedUTC();
                itemController.subreddit.setText(commentdisplay);
                    if(item.post.get().isStickyPost()==true) {
                        itemController.title.setTextColor(Color.GREEN);
                    }
                else{
                        itemController.title.setTextColor(Color.BLACK);
                    }
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public TextView postid;
        public TextView Author;
        public TextView site;
        public TextView title;
        public TextView subreddit;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.lblListItem);
            postid = (TextView) itemView.findViewById(R.id.ContentId);
            Author = (TextView) itemView.findViewById(R.id.Author);
            site = (TextView) itemView.findViewById(R.id.site);
            title = (TextView) itemView.findViewById(R.id.Tittle);
            subreddit = (TextView) itemView.findViewById(R.id.Subreddit);
        }
    }

    public static class Item {
        public int type;
        String Header;
       Box <RedditPost> post = new Box<RedditPost>();
        public List<Item> invisibleChildren;
        RedditPost Listpost;
        public Item(int type, RedditPost Listpost,String Header) {
            this.type = type;
            this.Header= Header;
            post.add(Listpost);
        }


    }
    public static class Box<T> {
        private T t;

        public void add(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }
    }
}
