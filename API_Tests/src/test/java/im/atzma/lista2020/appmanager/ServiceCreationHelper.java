package im.atzma.lista2020.appmanager;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ServiceCreationHelper {
    String key;
    String value;
    Response response_post;
    String responseString;
    JsonPath jpath;
    int service_id;
    String service_name;

    public ServiceCreationHelper(Map<String, String> firstCookie) {
        for (Map.Entry<String, String> entry : firstCookie.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
        }
    }


    public Integer createService(int id) {
        response_post = given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                formParam("name", "TestService").
                formParam("duration", 60).
                formParam("price", 50).
                formParam("color", "#50e3c1").
                formParam("category_id", id).
                formParam("added", "2020-01-26 21:39:41").
                when().
                post("/catalog/services").then().
                assertThat().
                statusCode(201).extract().response();

        responseString = response_post.asString();
        System.out.println("NEW SERVICE ID ====== " + Integer.parseInt(responseString));

        return Integer.parseInt(responseString);
    }

    public int createCategory() {
        response_post = given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                formParam("name", "TestCategory").
                when().
                post("/catalog/services/categories").then().
                assertThat().
                statusCode(201).extract().response();

        responseString = response_post.asString();
        System.out.println("Category id = " + responseString);
        return Integer.parseInt(responseString);
    }

    public String getServiceList() {
        Response response = given().cookies(key, value).
                //  header("content-type", "application/json; charset=utf-8").
                        header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                        when().
                        get("/catalog/services/").then().extract().response();
        responseString = response.asString();
        if (!responseString.equals("[]")) {

            System.out.println("service list: " + responseString);
            JsonPath jp = new JsonPath(responseString);    //convert response String to JSON
            service_id = jp.get("id[0]");                 //get id from JSON
            service_name = jp.get("name[0]");

            int count = jp.get("array.size()");
            for (int i = 0; i < count; i++) {
                System.out.println(jp.get("id[" + i + "]").toString());
            }
            System.out.println("service id = " + service_id);
        } else {
            System.out.println("service list: " + " = null");
        }
        responseString = response.headers().toString() + "\n\n" + response.body().prettyPrint();
        responseString = responseString.replaceAll("Id.*", "#####");
        responseString = responseString.replaceAll("id.*", "#####");
        responseString = responseString.replaceAll("start.*", "#####");
        responseString = responseString.replaceAll("end.*", "#####");
        responseString = responseString.replaceAll("profile_picture.*", "#####");
        responseString = responseString.replaceAll("Cookie.*", "#####");
        responseString = responseString.replaceAll("Expires=Sat, .*", "#####");
        responseString = responseString.replaceAll("Date=.*", "#####");

        return responseString;
    }

    public void deleteService() {
        getServiceList();
        given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                header("X-Requested-With", "XMLHttpRequest").
                when().
                delete("/catalog/services/" + service_id).
                then().
                assertThat().statusCode(204);

    }

}

