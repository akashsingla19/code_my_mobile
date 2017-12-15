package com.codemymobile.codemymobiletask.post.deatil;

import java.util.List;

import services.model.Post;

public interface PostCommentContract
{
    interface PostCommnentView
    {
        void showProgress();

        void hideProgress();

        void refreshComments(List<Post> posts);

        void showError(int errorType);
    }

    interface POstCommmentPresenter
    {
        void getCommentsByPostId(String postId);
    }

}
