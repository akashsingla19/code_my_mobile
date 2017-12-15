package network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class NetworkCall {
    private RequestQueue requestQueue;
    private static NetworkCall networkCall;
    private final int REQUEST_TIME_OUT = 10000;

    private NetworkCall(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static NetworkCall getInstance(Context context) {
        if (networkCall == null) {
            synchronized (NetworkCall.class) {
                if (networkCall == null) {
                    networkCall = new NetworkCall(context);
                }
            }
        }
        return networkCall;
    }


    public void addRequestinQueue(GsonRequest request, int requestTimeOut)
    {
        if(request != null)
        {
            if(requestTimeOut == 0)
            {
                requestTimeOut = REQUEST_TIME_OUT;
            }

            request.setRetryPolicy(new DefaultRetryPolicy(
                    requestTimeOut,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }

    }

}
