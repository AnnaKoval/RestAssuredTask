package response;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class JsonSchemaTest {

    @Test
    public void main() {
        try (InputStream schema = new FileInputStream(new File("").
                getAbsolutePath() + "/resources/schema.json")) {
            when().
                    get("https://jsonplaceholder.typicode.com/posts/1").
                    then().assertThat().body(matchesJsonSchema(schema));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
