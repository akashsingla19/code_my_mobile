package com.codemymobile.codemymobiletask.post.summary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codemymobile.codemymobiletask.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import services.Service;
import services.ServiceExecuter;
import services.model.Post;

public class PostListFragment extends Fragment implements PostListConract.PostLisView {

    private OnPostListItemListener mListener;
    private RecyclerView recyclerViewPostList;
    private PostAdapter postAdapter;
    private ProgressBar progressBar;
    private TextView tvMsg;

    private static final int limit = 10;
    private static final int INITIAL_COUNT = 0;
    private int count = INITIAL_COUNT;

    private HandlePostAdapterListener handlePostAdapterListener;
    private PostListConract.PostListPresenter presenter;
    private List<Post> posts;

    // status to check the previous web service completed or not.
    private boolean isApiComplete = false;

    public PostListFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PostListFragment newInstance(String param1, String param2) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_list_fragment_layout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        posts = new ArrayList<>();
        recyclerViewPostList = view.findViewById(R.id.recyclerViewPostList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerViewPostList.setLayoutManager(manager);
        progressBar = view.findViewById(R.id.progressBar);
        tvMsg = view.findViewById(R.id.tvMsg);
        tvMsg.setVisibility(View.GONE);
        handlePostAdapterListener = new HandlePostAdapterListener();
        WeakReference<PostListConract.PostLisView> viewContract = new WeakReference<PostListConract.PostLisView>(this);
        presenter = new PostListPresenter(viewContract, ServiceExecuter.getInstance(getActivity().getApplicationContext()));
        presenter.getPosts(count, limit);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostListItemListener) {
            mListener = (OnPostListItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPostListItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress() {

        isApiComplete = true;

        // failure for first web service then show full screen error.
        if (INITIAL_COUNT == count) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerViewPostList.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerViewPostList.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerViewPostList.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshPosts(List<Post> posts) {

        isApiComplete = false;
        if (posts == null || posts.isEmpty()) {

            if (this.posts.size() == 0) {
                recyclerViewPostList.setVisibility(View.GONE);
                tvMsg.setVisibility(View.VISIBLE);
                tvMsg.setText(getString(R.string.msg_no_post));
            }

            if (postAdapter != null) {
                postAdapter.setLoadMore(false);
            }

        } else {
            if (View.VISIBLE == tvMsg.getVisibility()) {
                tvMsg.setVisibility(View.GONE);
            }
            if (View.GONE == recyclerViewPostList.getVisibility()) {
                recyclerViewPostList.setVisibility(View.VISIBLE);
            }

            this.posts.addAll(posts);
            count = this.posts.size();
            if (postAdapter == null) {
                postAdapter = new PostAdapter(this.posts, getActivity().getApplication(), handlePostAdapterListener);
                recyclerViewPostList.setAdapter(postAdapter);
            } else {
                postAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showError(int errorType) {

        isApiComplete = false;

        switch (errorType) {
            case Service.NETWORK_ERROR_CODE:
                handleError(getString(R.string.error_network));
                break;

            default:
                handleError(getString(R.string.error_server));
        }
    }

    private void handleError(String msg) {
        tvMsg.setVisibility(View.VISIBLE);
        recyclerViewPostList.setVisibility(View.GONE);
        tvMsg.setText(msg);
    }

    public interface OnPostListItemListener {
        void onPostDetail(String postId);
    }

    private class HandlePostAdapterListener implements PostAdapter.PostAdapterListener {
        @Override
        public void loadMore() {

            // hit web service
            if (!isApiComplete) {
                isApiComplete = true;
                presenter.getPosts(count, limit);
            }
        }

        @Override
        public void onPostSelected(String postId) {

            // Navigate to post comment screen.
            if (mListener != null) {
                mListener.onPostDetail(postId);
            }

        }
    }
}
