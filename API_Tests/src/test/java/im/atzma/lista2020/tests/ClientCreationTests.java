package im.atzma.lista2020.tests;

import org.testng.annotations.Test;

import java.io.IOException;

public class ClientCreationTests extends TestBase {

    @Test
    public void createClient() throws IOException {
       // app.clientCreationHelper().createClient();
        app.clientCreationHelper().getClientList();
        app.clientCreationHelper().deleteClient();
        app.clientCreationHelper().getClientList();
    }
}
