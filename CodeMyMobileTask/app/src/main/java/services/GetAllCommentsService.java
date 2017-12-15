package services;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import network.GsonRequest;
import network.NetworkCall;
import services.model.Post;
import services.model.PostResponse;

public class GetAllCommentsService implements Service, Response.Listener<PostResponse[]>, Response.ErrorListener {

    private static final int LIMIT = 10;
    private int serviceCode;
    private ServiceListener serviceListener;
    private Context context;

    protected GetAllCommentsService(int serviceCode, ServiceListener serviceListener, Context context) {
        this.serviceCode = serviceCode;
        this.serviceListener = serviceListener;
        this.context = context;
    }

    @Override
    public void execute(Object... obj) {

        NetworkCall networkCall = NetworkCall.getInstance(context);
        String postId = (String) obj[0];
        String url = String.format(ApiEndpoint.ALL_COMMNENTS_BY_POST_ID, postId);

        if (serviceListener != null) {
            serviceListener.onPreService(serviceCode);
        }

        networkCall.addRequestinQueue(new GsonRequest(url, PostResponse[].class, null, this, this), 0);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (serviceListener != null) {
            exception.NetworkError networkError = null;
            if (error instanceof NetworkError || error instanceof TimeoutError) {
                networkError = new exception.NetworkError(Service.NETWORK_ERROR_CODE, "");
            } else {
                networkError = new exception.NetworkError(Service.SERVER_ERROR_CODE, "");
            }
            serviceListener.onServiceError(serviceCode, networkError);
        }
    }

    @Override
    public void onResponse(PostResponse[] response) {

        if (serviceListener != null) {

            if (response == null || response.length < 2 || response[1].getData() == null
                    || response[1].getData().getChildren() == null) {
                serviceListener.onSuccessServiceResponse(serviceCode, null);
            } else {

                /*
                We can save this response in local database or get back to the view.
                 it is depend upon bussiness requirment.
                */

                List<Post> posts = new ArrayList<>();

                List<PostResponse.Data.Child> postComments = response[1].getData().getChildren();
                for (PostResponse.Data.Child child : postComments) {
                    posts.add(child.getData());
                }

                serviceListener.onSuccessServiceResponse(serviceCode, posts);
            }

        }
    }
}