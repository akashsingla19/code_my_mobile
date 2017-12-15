package services;

import android.content.Context;

public class ServiceExecuter {

    private Context context;

    public static ServiceExecuter serviceExecuter;

    private ServiceExecuter(Context context) {
        this.context = context;
    }

    public static ServiceExecuter getInstance(Context context) {
        if (serviceExecuter == null) {
            synchronized (ServiceExecuter.class) {
                if (serviceExecuter == null) {
                    serviceExecuter = new ServiceExecuter(context);
                }
            }
        }

        return serviceExecuter;
    }

    public Service getService(int serviceCode, ServiceListener serviceListener) {
        switch (serviceCode) {
            case Service.SERVICE_CODE_GET_ALL_POSTS:
                return new GetAllPostService(serviceCode, serviceListener, context);

            case Service.SERVICE_CODe_ALL_COMMENTS_BY_POST_ID:
                return new GetAllCommentsService(serviceCode, serviceListener, context);

            default:
                return null;
        }
    }

}
