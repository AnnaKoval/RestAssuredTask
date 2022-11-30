package serialization;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

    String userId;
    String title;
    String body;

    @JsonIgnore
    String systemProperty;

    @JsonProperty("id")
    String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSystemProperty() {
        return systemProperty;
    }

    public void setSystemProperty(String systemProperty) {
        this.systemProperty = systemProperty;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
