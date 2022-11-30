package jira;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import org.apache.http.HttpStatus;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class JiraAPIExamples {
    /**
     Supplying basic auth headers
     If you need to, you may construct and send basic auth headers yourself. To do this you need to perform the following steps:

     1. Generate an API token for Jira using your Atlassian Account: https://id.atlassian.com/manage/api-tokens.
     2. Build a string of the form useremail:api_token.
     3. BASE64 encode the string.
     4. Supply an Authorization header with content Basic followed by the encoded string.
     */


    RequestSpecification requestSpec;

    @Before
    public void preconditions() {
        RestAssured.baseURI = "https://project.atlassian.net";
        RestAssured.basePath =  "/rest/api/2/issue";
        String jiraToken = "xXXXaWFuYV9idWxhdmtvQGVwYW0uY29tOkpSNDZTaEtpZ3JtblVlYmRzbUR1RE0000";

        requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Authorization", "Basic " + jiraToken)
                .build();
    }

    public void getIssueStatus(String issueId) {
        given().spec(requestSpec).pathParam("issueId", issueId)
                .get("/{issueId}?fields=status").prettyPrint();
    }

    public void postComment(String issueId, String payload) {
        given().spec(requestSpec).pathParam("issueId", issueId)
                .body(payload)
                .post("//{issueId}/comment")
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void test() {
        getIssueStatus("QA-112");

        String payload = "{\"body\": \"This is a comment regarding the quality of the response.\"}";
        postComment("QA-113", payload);
    }

    @Test
    public void fileUpload() throws FileNotFoundException {
        String fileName = "google.png";
        String filePath = System.getProperty("user.dir") +"\\resources\\";
        MultiPartSpecification file = new MultiPartSpecBuilder(new File(filePath + fileName))
                .and().with().fileName(fileName)
                .and().with().controlName("file")
                .and().with().mimeType("image/png").build();

        InputStream schema = new FileInputStream(new File("").getAbsolutePath() + "/resources/ResponseSchema.json");

        given().spec(requestSpec)
                .contentType("multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
                .multiPart(file)
                .when()
                .post("/QA-113/attachments")
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body(matchesJsonSchema(schema));
    }
}
