package com.codemymobile.codemymobiletask.post.deatil;

import java.lang.ref.WeakReference;
import java.util.List;

import exception.NetworkError;
import services.Service;
import services.ServiceExecuter;
import services.ServiceListener;
import services.model.Post;

public class PostCommentPresenter implements PostCommentContract.POstCommmentPresenter, ServiceListener<List<Post>> {

    private ServiceExecuter serviceExecuter;
    private WeakReference<PostCommentContract.PostCommnentView> viewRef;

    public PostCommentPresenter(WeakReference<PostCommentContract.PostCommnentView> viewRef, ServiceExecuter serviceExecuter) {
        this.serviceExecuter = serviceExecuter;
        this.viewRef = viewRef;
    }

    @Override
    public void getCommentsByPostId(String postId) {
        PostCommentContract.PostCommnentView view = viewRef.get();
        if (view != null) {
            view.showProgress();
        }

        Service service = serviceExecuter.getService(Service.SERVICE_CODe_ALL_COMMENTS_BY_POST_ID, this);
        service.execute(postId);
    }

    @Override
    public void onSuccessServiceResponse(int serviceCode, List<Post> ob) {

        PostCommentContract.PostCommnentView view = viewRef.get();
        if (view != null) {
            view.hideProgress();
            view.refreshComments(ob);
        }
    }

    @Override
    public void onServiceError(int serviceCode, NetworkError ex) {
        PostCommentContract.PostCommnentView view = viewRef.get();
        if (view != null) {
            view.hideProgress();
            view.showError(ex.getCode());
        }
    }

    @Override
    public void onPreService(int serviceCode) {
        PostCommentContract.PostCommnentView view = viewRef.get();
        if (view != null) {
            view.showProgress();
        }
    }
}
