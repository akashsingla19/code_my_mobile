package services;

public interface Service
{
    int SERVICE_CODE_GET_ALL_POSTS = 1;
    int SERVICE_CODe_ALL_COMMENTS_BY_POST_ID = SERVICE_CODE_GET_ALL_POSTS + 1;

    int NETWORK_ERROR_CODE = 1000;
    int SERVER_ERROR_CODE = 1001;


    void execute(Object... obj);
}
