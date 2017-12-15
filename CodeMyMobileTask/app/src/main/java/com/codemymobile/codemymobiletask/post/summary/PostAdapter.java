package com.codemymobile.codemymobiletask.post.summary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemymobile.codemymobiletask.R;

import java.util.List;

import services.model.Post;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> posts;
    private LayoutInflater inflater;
    private PostAdapterListener listener;

    private static final int LOAD_MORE = 1;
    private static final int ERROR_VIEW = 2;
    private static final int OTHER_VIEW = 3;

    private boolean isLoadMoreRequired = Boolean.TRUE;
    private HandleClickListener handleClickListener;

    public PostAdapter(List<Post> posts, Context context, PostAdapterListener listener) {
        this.posts = posts;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
        handleClickListener = new HandleClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        if (OTHER_VIEW == viewType) {
            View view = inflater.inflate(R.layout.post_item_layout, parent, false);
            viewHolder = new PostViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.progress_bar_layout, parent, false);
            viewHolder = new LoadMoreViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case OTHER_VIEW:
                PostViewHolder postViewHolder = (PostViewHolder) holder;
                handleOtherView(postViewHolder, posts.get(position));
                break;

            default:
                LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
                handleLoadMore(loadMoreViewHolder);
        }

    }

    private void handleLoadMore(LoadMoreViewHolder loadMoreViewHolder) {

        loadMoreViewHolder.view.setVisibility(Boolean.TRUE == isLoadMoreRequired ? View.VISIBLE : View.INVISIBLE);
        if (listener != null) {
            listener.loadMore();
        }
    }

    private void handleOtherView(PostViewHolder postViewHolder, Post post) {
        postViewHolder.view.setTag(post.getId());
        postViewHolder.tvTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == posts.size() ? LOAD_MORE : OTHER_VIEW;
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProfilePic;
        TextView tvTitle;
        View view;

        public PostViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imvProfilePic = itemView.findViewById(R.id.imvProfilePic);
            tvTitle = itemView.findViewById(R.id.tvPostTitle);
            itemView.setOnClickListener(handleClickListener);
        }
    }

    private class HandleClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            if(listener != null)
            {
                listener.onPostSelected((String)view.getTag());
            }
        }
    }

    public void setLoadMore(boolean isLoadMoreRequired) {
        this.isLoadMoreRequired = isLoadMoreRequired;
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        View view;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    public interface PostAdapterListener {
        void loadMore();
        void onPostSelected(String postId);
    }
}
