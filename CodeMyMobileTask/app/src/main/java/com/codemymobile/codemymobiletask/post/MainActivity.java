package com.codemymobile.codemymobiletask.post;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.codemymobile.codemymobiletask.R;
import com.codemymobile.codemymobiletask.post.deatil.PostCommentFragment;
import com.codemymobile.codemymobiletask.post.summary.PostListFragment;

import common.BaseActivity;

public class MainActivity extends BaseActivity implements PostListFragment.OnPostListItemListener {

    private static final String KEY_POST_LIST_FRAGEMNT = "post_list_fragment";
    private static final String KEY_POST_POST_COMMENT_FRAGMENT = "post_comment_list_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    // initialization of view.
    private void init()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PostListFragment fragment = (PostListFragment) fragmentManager.findFragmentByTag(KEY_POST_LIST_FRAGEMNT);
        if (fragment == null) {
            fragment = new PostListFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.frameLayout, fragment, KEY_POST_LIST_FRAGEMNT);
            transaction.commit();
        }
    }


    @Override
    public void onPostDetail(String postId) {

        PostCommentFragment frag = (PostCommentFragment) getSupportFragmentManager().findFragmentByTag( KEY_POST_POST_COMMENT_FRAGMENT);

        if(frag == null)
        {
            frag = PostCommentFragment.newInstance(postId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, frag, KEY_POST_POST_COMMENT_FRAGMENT);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
