package services;

public interface ApiEndpoint
{
    String ALL_POST_URL = "https://www.reddit.com/r/all/new.json?count=%s&limit=%s";
    String ALL_COMMNENTS_BY_POST_ID = "https://www.reddit.com/r/all/comments/%s.json";
}
