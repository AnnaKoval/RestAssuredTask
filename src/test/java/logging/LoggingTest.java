package logging;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoggingTest {

    public static final String TYPICODE_COM_TODOS_1 = "https://jsonplaceholder.typicode.com/todos/1";

    @Test
    public void responseLogging() {
        given().

                get(TYPICODE_COM_TODOS_1).
                then().statusCode(200).
                log().everything();
        System.out.println("J_VARIABLE = " + System.getenv("J_VARIABLE"));
    }

    @Test
    public void requestLogging() {
        given().log().method().
                get(TYPICODE_COM_TODOS_1).
                then().statusCode(200);
    }

    @Test
    public void requestAndResponseLogging() {
        given().log().method().log().parameters().
                get("https://jsonplaceholder.typicode.com/users/1").
                then().
                statusCode(200).log().body(true);
    }

    @Test
    public void ifErrorLogs() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.BODY);
        given().
                get(TYPICODE_COM_TODOS_1).
                then().
                statusCode(404).log().ifValidationFails();
    }
}
