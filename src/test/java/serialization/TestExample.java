package serialization;


import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestExample {

    @Test
    public void main(){
        Post newPost = new Post();
        newPost.setUserId("1");
        newPost.setPostId("000345");
        newPost.setTitle("Serialization example");
        newPost.setBody("and deserialization.");

        RequestSpecification spec = given().contentType(ContentType.JSON).baseUri("https://jsonplaceholder.typicode.com/");

        given().spec(spec).log().body().body(newPost).
                post("/posts").
                then().extract().path("id");

        Post deserializedPost = given().spec(spec).get("/posts/{userId}", 1).as(Post.class);
    }
}
