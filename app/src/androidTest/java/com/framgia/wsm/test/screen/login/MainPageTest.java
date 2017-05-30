package com.framgia.wsm.test.screen.login;

import android.test.ActivityInstrumentationTestCase2;
import com.framgia.wsm.screen.main.MainActivity;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;

/**
 * Created by tri on 29/05/2017.
 */

@CucumberOptions(features = "features/main.feature")
public class MainPageTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainPageTest(MainActivity activityClass) {
        super(MainActivity.class);
    }

    @Given("^I have a MainScreen")
    public void i_have_a_MainScreen() {
        assertNotNull(getActivity());
    }
}
