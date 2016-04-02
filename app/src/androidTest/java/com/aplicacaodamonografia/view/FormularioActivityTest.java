package com.aplicacaodamonografia.view;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;

import com.aplicacaodamonografia.R;
import com.aplicacaodamonografia.activity.FormularioActivity;
import com.aplicacaodamonografia.activity.ListaDeUsuarioActivity;
import com.aplicacaodamonografia.util.Util;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by pablo on 24/02/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FormularioActivityTest {

    @Rule
    public ActivityTestRule<FormularioActivity> mActivityRule = new ActivityTestRule<>(FormularioActivity.class);

    @Test
    public void deveriaBuscarCep() {
        onView(withId(R.id.cep)).perform(typeText("60830005"), closeSoftKeyboard());
        onView(withId(R.id.btn_pesquisar)).perform(scrollTo(), click());
        assertPreencimentoDoEndereco();
    }

    @Test
    public void deveriaIncluirNovoUsuario() {

        try {
            onView(withId(R.id.nome)).perform(typeText("Usuario 3"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.telefone)).perform(typeText("999998855"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.cep)).perform(typeText("60830005"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.btn_pesquisar)).perform(scrollTo(), click());
            Thread.sleep(1000);
            assertPreencimentoDoEndereco();
            onView(withId(R.id.btn_confirmar)).perform(scrollTo(), clickButton());
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static ViewAction clickButton() {
        return actionWithAssertions(new ButtonGeneralClickAction(GeneralLocation.CENTER, Tap.SINGLE, Press.FINGER, null));
    }

    private void assertPreencimentoDoEndereco() {
        onView(withId(R.id.logradouro)).check(matches(withText("Washington Soare")));
        onView(withId(R.id.bairro)).check(matches(withText("Alagadiço Novo")));
        onView(withId(R.id.uf)).check(matches(withText("CE")));
        onView(withId(R.id.cidade)).check(matches(withText("Fortaleza")));
    }
}
