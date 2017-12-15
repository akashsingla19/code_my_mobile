package com.codemymobile.codemymobiletask.post.deatil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codemymobile.codemymobiletask.R;

import java.util.List;

import services.model.Post;

public class PostCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> posts;
    private LayoutInflater inflater;

    public PostCommentAdapter(List<Post> posts, Context context) {

        this.posts = posts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.textview_layout, parent, false);
        PostCommentViewHolder holder = new PostCommentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PostCommentViewHolder postCommentHolder = (PostCommentViewHolder) holder;
        Post post = posts.get(position);
        postCommentHolder.textView.setText(post.getBody());

    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    class PostCommentViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PostCommentViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
