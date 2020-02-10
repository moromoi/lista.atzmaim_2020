package im.atzma.lista2020.appmanager;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AppointmentHelper {
    String key;
    String value;
    Response response;
    String responseString;
    JsonPath jpath;
    int appointment_id;

    public AppointmentHelper(Map<String, String> firstCookie) {
        for (Map.Entry<String, String> entry : firstCookie.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
        }
    }


    public void createAppointment() {
        getAppointmentList();
        given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                formParam("start", "2020-02-11T09:00:00").
                formParam("client_id", 5).
                formParam("worker_id", 1).
                formParam("total_price", 50).
                formParam("duration", 30).
                formParam("services", "[{\"id\":5,\"name\":\"TestService\",\"price\":\"50\",\"duration\":30,\"color\":\"#50e3c1\",\"category\":{\"name\":\"Common\",\"id\":1},\"count\":1}]").
                formParam("note", "null").
                formParam("is_reminders_set", "false").
                formParam("address", "null").
                formParam("added", "2020-02-10T09:00:00").

                when().
                post("/calendar").
                then().
                assertThat().
                statusCode(201).and().contentType("text/html; charset=UTF-8");
        getAppointmentList();
    }

    public void getAppointmentList() {
        response = given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                when().
                get("/calendar?start=2020-02-10T00:00:00&end=2020-02-12T23:59:59&worker_id=1").then().extract().response();

        responseString = response.asString();   //convert response (RAW) to String

        if(!responseString.equals("[]")) {

            System.out.println("appointment id : " + responseString);
            JsonPath jp = new JsonPath(responseString);    //convert response String to JSON
            appointment_id = jp.get("id[0]");                 //get id from JSON
            System.out.println("appointment id = " + appointment_id);

        }
        else {
            System.out.println("appointment id: " + " = null");
        }
    }

    public void deleteAppointment() {
        getAppointmentList();
        given().cookies(key, value).
                header("content-type", "application/x-www-form-urlencoded").
                header("user-agent", "alpalch-qpEzhaOvY0Ecb4e0").
                when().
                delete("/calendar/" + appointment_id).
                then().
                assertThat().statusCode(204);
        getAppointmentList();
    }
}
