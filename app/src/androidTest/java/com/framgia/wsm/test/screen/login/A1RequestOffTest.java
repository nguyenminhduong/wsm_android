package com.framgia.wsm.test.screen.login;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import com.framgia.wsm.R;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

/**
 * Created by Duong on 6/21/2017.
 */
@CucumberOptions(features = "features/requestoff.feature")
public class A1RequestOffTest extends ActivityInstrumentationTestCase2<RequestOffActivity> {

    public A1RequestOffTest(RequestOffActivity requestOffActivity) {
        super(RequestOffActivity.class);
    }

    @Given("^I have a Create request off Screen")
    public void I_have_a_request_off_screen() {
        assertNotNull(getActivity());
    }

    @When("^I input project name (\\S+)$")
    public void iInputProjectNameProject(final String project) {
        Espresso.onView(ViewMatchers.withId(R.id.edit_project))
                .perform(ViewActions.typeText(project));
    }

    @And("^I input position name (\\S+)$")
    public void iInputPositionNamePosition(final String position) {
        Espresso.onView(ViewMatchers.withId(R.id.edit_position))
                .perform(ViewActions.typeText(position));
    }

    @When("^I input project name <project>$")
    public void iInputProjectNameProject() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^I choose Off have salary - Company pay (\\S+)$")
    public void iChooseOffHaveSalaryCompanyPayOffType(String abc) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
