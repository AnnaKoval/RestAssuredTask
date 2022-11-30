package jira;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class JiraAuthExamples {

    @Test
    public void basicAuth() {
        String login = "your_name@epam.com";
        String password = "JR46ShKigrmnUebdsmFavqBE";
        given().auth().basic(login, password)
                .get("https://project.atlassian.net/rest/api/2/issue/EPAM-111?fields=status").prettyPrint();
    }
}



