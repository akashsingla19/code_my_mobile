package com.codemymobile.codemymobiletask.post.summary;

import java.util.List;

import services.model.Post;

public interface PostListConract {

    interface PostLisView {
        void showProgress();

        void hideProgress();

        void refreshPosts(List<Post> posts);

        void showError(int errorType);

    }

    interface PostListPresenter {
        void getPosts(int count, int limit);
    }
}
