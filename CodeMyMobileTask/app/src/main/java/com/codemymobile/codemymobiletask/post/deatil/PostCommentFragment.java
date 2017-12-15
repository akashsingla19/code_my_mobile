package com.codemymobile.codemymobiletask.post.deatil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codemymobile.codemymobiletask.R;

import java.lang.ref.WeakReference;
import java.util.List;

import services.Service;
import services.ServiceExecuter;
import services.model.Post;


public class PostCommentFragment extends Fragment implements PostCommentContract.PostCommnentView {

    private static final String POST_ID = "post_id";
    private RecyclerView recyclerViewPostList;
    private TextView tvMsg;
    private PostCommentAdapter postCommentAdapter;
    private ProgressBar progressBar;

    private PostCommentContract.POstCommmentPresenter presenter;

    public PostCommentFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PostCommentFragment newInstance(String postId) {
        PostCommentFragment fragment = new PostCommentFragment();
        Bundle args = new Bundle();
        args.putString(POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_list_fragment_layout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerViewPostList = view.findViewById(R.id.recyclerViewPostList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerViewPostList.setLayoutManager(manager);
        progressBar = view.findViewById(R.id.progressBar);
        tvMsg = view.findViewById(R.id.tvMsg);
        tvMsg.setVisibility(View.GONE);
        WeakReference<PostCommentContract.PostCommnentView> viewContract = new WeakReference<PostCommentContract.PostCommnentView>(this);
        presenter = new PostCommentPresenter(viewContract, ServiceExecuter.getInstance(getActivity().getApplicationContext()));
        presenter.getCommentsByPostId(this.getArguments().getString(POST_ID));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerViewPostList.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerViewPostList.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshComments(List<Post> posts) {

        if (posts == null || posts.size() == 0) {
            recyclerViewPostList.setVisibility(View.GONE);
            handleError(getString(R.string.msg_no_comment));
        } else {
            recyclerViewPostList.setVisibility(View.VISIBLE);
            tvMsg.setVisibility(View.GONE);
            if (postCommentAdapter == null) {
                postCommentAdapter = new PostCommentAdapter(posts, getActivity().getApplication());
                recyclerViewPostList.setAdapter(postCommentAdapter);
            } else {
                postCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showError(int errorType) {

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

}

