package com.codemymobile.codemymobiletask.post.summary;

import java.lang.ref.WeakReference;
import java.util.List;

import exception.NetworkError;
import services.Service;
import services.ServiceExecuter;
import services.ServiceListener;
import services.model.Post;

public class PostListPresenter implements PostListConract.PostListPresenter, ServiceListener<List<Post>> {

    private WeakReference<PostListConract.PostLisView> viewRef;
    private ServiceExecuter serviceExecuter;


    public PostListPresenter(WeakReference<PostListConract.PostLisView> viewRef, ServiceExecuter serviceExecuter) {
        this.viewRef = viewRef;
        this.serviceExecuter = serviceExecuter;
    }

    @Override
    public void getPosts(int count, int limit) {
        Service service = serviceExecuter.getService(Service.SERVICE_CODE_GET_ALL_POSTS, this);
        service.execute(count, limit);
    }

    @Override
    public void onSuccessServiceResponse(int serviceCode, List<Post> posts) {

        PostListConract.PostLisView view = viewRef.get();
        if (view != null) {
            view.hideProgress();
            view.refreshPosts(posts);
        }

    }

    @Override
    public void onServiceError(int serviceCode, NetworkError error) {

        PostListConract.PostLisView view = viewRef.get();
        if (view != null) {
            view.hideProgress();
            view.showError(error.getCode());
        }
    }

    @Override
    public void onPreService(int serviceCode) {
        PostListConract.PostLisView view = viewRef.get();
        if (view != null) {
            view.showProgress();
        }
    }
}
