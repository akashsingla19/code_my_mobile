package services;

import exception.NetworkError;

public interface ServiceListener<T> {

    void onSuccessServiceResponse(int serviceCode, T ob);

    void onServiceError(int serviceCode, NetworkError ex);

    void onPreService(int serviceCode);

}
