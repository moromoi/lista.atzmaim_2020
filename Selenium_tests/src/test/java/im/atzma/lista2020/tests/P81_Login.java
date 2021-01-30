package im.atzma.lista2020.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class P81_Login extends TestBase{

    @Test(priority = 1, alwaysRun = true)
    public void login() throws InterruptedException {
        app.P81_goTo().goToLoginPage();
        app.P81_goTo().closePopap();
        boolean login =app.P81_goTo().login("alex.palchisky@gmail.com", "!z8Jk4KpT9cCtd3");
        Assert.assertTrue(login);
    }

}
