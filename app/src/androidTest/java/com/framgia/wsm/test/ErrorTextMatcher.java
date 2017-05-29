package com.framgia.wsm.test;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by ASUS on 29/05/2017.
 */

public class ErrorTextMatcher extends TypeSafeMatcher<View> {

    private String mExpectedError;

    public ErrorTextMatcher(String expectedError) {
        mExpectedError = expectedError;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = (TextInputLayout) view;
            return mExpectedError.equals(textInputLayout.getError());
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
