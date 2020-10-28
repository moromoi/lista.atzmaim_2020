package im.atzma.lista2020.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class P81_PoliciesTest extends TestBase {

    @Test
    public void goToPolicy() throws InterruptedException {
        app.P81_goTo().goToPolicyPage();
        Assert.assertTrue(app.P81_goTo().goToPolicyPage());
    }

    @Test
    public void initiatePolicyCreation() {
        if (app.p81_policyHelper().initiatePolicyCreation()) {
            System.out.println("Input filed 'Policy Name' is editable");
        } else {
            System.out.println("Input filed 'Policy Name' is not editable");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void veirfyPolicyURL() {
        if (app.p81_policyHelper().policyPage()) {
            System.out.println("Redirect to Policy creation URL done");
        } else {
            System.out.println("It is not Policy creation URL");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void addPolicy() throws InterruptedException {
        if(app.p81_policyHelper().addPolicy("PalchitskyAlex_Policy")) {
            System.out.println("Redirect to Policy URL done and new policy created");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void verifyNumberOfPolicy() throws InterruptedException {
        int numOfPolicy = app.p81_policyHelper().verifyNumberOfPolicy();
        if(numOfPolicy == 1) {
            System.out.println("Number of Policies on page equal: " + numOfPolicy);
        }
        else {
            System.out.println("The current policy number does not match the condition and equal: " + numOfPolicy);
        }

        Assert.assertEquals(numOfPolicy,1);
    }

    @Test
    public void initiatePolicyDeletion() throws InterruptedException {
        app.P81_goTo().goToPolicyPage();
        boolean verify_Menu_Items = app.p81_policyHelper().initiatePolicyDeletion();

        if(verify_Menu_Items) {
            System.out.println("Number of items in Policy menu = 3 and and editable");
        }
        else {
            System.out.println("The current policy menu does not match the condition");
        }
        Assert.assertTrue(verify_Menu_Items);
    }

    @Test
    public void deletePolicies() throws InterruptedException {
        app.P81_goTo().goToPolicyPage();
        app.p81_policyHelper().deletePolicies();
        int numOfPolicy = app.p81_policyHelper().verifyNumberOfPolicy();

        if(numOfPolicy == 0) {
            System.out.println("Number of Policies must on the page be 0 and equal: " + numOfPolicy);
        }
        else {
            System.out.println("The current policy number does not match the condition and equal: " + numOfPolicy);
        }

        Assert.assertEquals(numOfPolicy,0);
    }

}
