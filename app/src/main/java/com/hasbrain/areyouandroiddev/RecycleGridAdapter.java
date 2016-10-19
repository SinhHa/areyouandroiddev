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

        import java.util.List;
        import com.hasbrain.areyouandroiddev.model.RedditPost;
        import com.hasbrain.areyouandroiddev.model.WebViewDisplay;

        import org.w3c.dom.Text;
/**
 * Created by sinhhx on 8/31/16.
 */
public class RecycleGridAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
    private List<RedditPost> listpost;
    Context context;
    public RecycleGridAdapter(Context context, List<RedditPost> listpost){
        this.listpost =listpost;
        this.context= context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView postid;
        public TextView Author;
        public TextView title;
        public TextView subreddit;

        public ViewHolder(View itemView) {
            super(itemView);
            postid =(TextView) itemView.findViewById(R.id.Id);
            Author= (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            subreddit= (TextView) itemView.findViewById(R.id.comment);
        }
    }
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        v.setBackgroundColor(Color.WHITE);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(v);
       return vh;

    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {
        final RedditPost redditPost = listpost.get(position);
        holder.postid.setText(" " + redditPost.getId());
        holder.Author.setText(redditPost.getAuthor());
        holder.title.setText(redditPost.getTitle());
        holder.title.setMaxLines(3);
        if(redditPost.isStickyPost()==true){
            holder.title.setTextColor(Color.GREEN);
        } else {
            holder.title.setTextColor(Color.BLACK);
        }
        float Titletxtsize = (holder.Author.getTextSize()) * 0.4F;
        holder.title.setTextSize(Titletxtsize);
        String commentdisplay = redditPost.getCommentCount() + " comments * " + "reddit" + " *" + redditPost.getCreatedUTC();
        holder.subreddit.setText(commentdisplay);


    }

    @Override
    public int getItemCount() {
        return listpost.size();
    }
}

