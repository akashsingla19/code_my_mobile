package services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PerryGarg on 13-12-2017.
 */

public class PostResponse {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("children")
        @Expose
        private List<Child> children = null;
        @SerializedName("after")
        @Expose
        private Object after;
        @SerializedName("before")
        @Expose
        private Object before;

        public List<Child> getChildren() {
            return children;
        }

        public void setChildren(List<Child> children) {
            this.children = children;
        }

        public Object getAfter() {
            return after;
        }

        public void setAfter(Object after) {
            this.after = after;
        }

        public Object getBefore() {
            return before;
        }

        public void setBefore(Object before) {
            this.before = before;
        }

        public static class Child {

            @SerializedName("kind")
            @Expose
            private String kind;
            @SerializedName("data")
            @Expose
            private Post data;

            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public Post getData() {
                return data;
            }

            public void setData(Post data) {
                this.data = data;
            }

            public Post childData;
        }
    }
}
