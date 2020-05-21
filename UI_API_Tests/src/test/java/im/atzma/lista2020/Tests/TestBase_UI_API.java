package im.atzma.lista2020.Tests;


import im.atzma.lista2020.appmanager.ApplicationRest_UI_API_Manager;
import io.qameta.allure.Attachment;
import io.qameta.allure.attachment.DefaultAttachmentContent;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.PrintStream;
import java.util.List;

import static im.atzma.lista2020.Tests.testdata.TestData.randomHttpRequestAttachment;


public class TestBase_UI_API {
   // Logger logger = LoggerFactory.getLogger(TestBase_UI_API.class);
    static final ApplicationRest_UI_API_Manager app = new ApplicationRest_UI_API_Manager();

    @Attachment
    public String logOutput(List<String> outputList) {
        String output = "";
        for (String o : outputList)
            output += o + " ";
        return output;
    }

    @BeforeMethod(alwaysRun = true)
    public void printStart() {
        System.out.println("<<<< START TEST >>>>");
        System.out.println("____________________");
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        RestAssured.filters(
                ResponseLoggingFilter.responseLogger(),
                new RequestLoggingFilter());
        app.init();


    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @AfterMethod(alwaysRun = true)
    public void printEnd() {
        System.out.println("__________________");
        System.out.println(">>>> END TEST <<<<");
    }
}
