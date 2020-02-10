package im.atzma.lista2020.appmanager;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class ServiceCreationHelper {
    String key;
    String value;
    String responseString;
    JsonPath jpath;
    int service_id;

    public ServiceCreationHelper(Map<String, String> firstCookie) {
        for (Map.Entry<String, String> entry : firstCookie.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
        }
    }


    public void createService() {
        given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                formParam("name", "TestCategory2").
                formParam("duration", 300).
                formParam("price", 50).
                formParam("color", "#50e3c1").
                formParam("category_id", 1).
                formParam("added", "2020-01-26 21:39:41").
                when().
                post("/catalog/services").
                then().
                assertThat().
                statusCode(201).and().contentType("text/html; charset=UTF-8");
        getServiceList();

    }

    public void getServiceList() {
        Response response = given().cookies(key, value).
              //  header("content-type", "application/json; charset=utf-8").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                when().
                get("/catalog/services/").then().extract().response();
        responseString = response.asString();
        if(!responseString.equals("[]")) {

            System.out.println("service list: " + responseString);
            JsonPath jp = new JsonPath(responseString);    //convert response String to JSON
            service_id = jp.get("id[0]");                 //get id from JSON
            System.out.println("service id = " + service_id);

        }
        else {
            System.out.println("service list: " + " = null");
        }
    }

    public void deleteService() {
        getServiceList();
        given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                when().
                delete("/catalog/services/" + service_id).
                then().
                assertThat().statusCode(204);
        getServiceList();
    }

}
