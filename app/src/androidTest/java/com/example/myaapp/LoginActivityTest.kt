package com.example.myaapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myaapp.others.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLoginActivityWithWrongCredentials() {
        onView(withId(R.id.editTextUsername)).perform(typeText("User"))
        onView(withId(R.id.editTextPassword)).perform(typeText("1234"))
        onView(withId(R.id.loginButton)).perform(click())

        onView(withText("Invalid credentials")).check(matches(isDisplayed()))
    }

//    @Test
//    fun checkLoginActivityWithValidCredentials() {
//        onView(withId(R.id.editTextUsername)).perform(typeText("Username"))
//        onView(withId(R.id.editTextPassword)).perform(typeText("1234567890"))
//        onView(withId(R.id.loginButton)).perform(click())
//
//
//        onView(withId(R.id.listView)).check(matches(isDisplayed()))
//    }

    @Test
    fun checkLoginActivityWithValidCredentialsAndLogoutCheck() {
        onView(withId(R.id.editTextUsername)).perform(typeText("Username"))
        onView(withId(R.id.editTextPassword)).perform(typeText("1234567890"))
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.logoutButton)).perform(click())

        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
    }
}