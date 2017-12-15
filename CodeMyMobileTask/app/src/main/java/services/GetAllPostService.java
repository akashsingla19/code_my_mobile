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


public class GetAllPostService implements Service, Response.Listener<PostResponse>, Response.ErrorListener {

    private static final int LIMIT = 10;
    private int serviceCode;
    private ServiceListener serviceListener;
    private Context context;

    protected GetAllPostService(int serviceCode, ServiceListener serviceListener, Context context) {
        this.serviceCode = serviceCode;
        this.serviceListener = serviceListener;
        this.context = context;
    }

    @Override
    public void execute(Object... obj) {

        NetworkCall networkCall = NetworkCall.getInstance(context);
        Integer count = (Integer) obj[0];
        Integer limit = (Integer) obj[1];

        count = count == null ? 0 : count;
        limit = limit == null ? LIMIT : limit;

        if (serviceListener != null) {
            serviceListener.onPreService(serviceCode);
        }

        networkCall.addRequestinQueue(new GsonRequest(String.format(ApiEndpoint.ALL_POST_URL, count, limit), PostResponse.class, null, this, this), 0);
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
    public void onResponse(PostResponse response) {

        if (serviceListener != null) {
            if (response == null || response.getData() == null) {
                serviceListener.onSuccessServiceResponse(serviceCode, null);
            } else {

                /*
                We can save this response in local database or get back to the view.
                 it is depend upon bussiness requirment.
                */

                List<Post> posts = new ArrayList<>();

                for (PostResponse.Data.Child child : response.getData().getChildren()) {
                    posts.add(child.getData());
                }

                serviceListener.onSuccessServiceResponse(serviceCode, posts);
            }
        }

    }
}
