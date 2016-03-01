package com.aplicacaodamonografia.view;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

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
        onView(withId(R.id.cep))
                .perform(typeText("60830005"), closeSoftKeyboard());
        onView(withId(R.id.btn_pesquisar)).perform(click());
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
            onView(withId(R.id.btn_pesquisar)).perform(click());
            assertPreencimentoDoEndereco();
            onView(withId(R.id.btn_confirmar)).perform(click());

            // Verificar nesse artigo pra tentar resolver problema no travis ci
            // http://baiduhix.blogspot.com.br/2015/07/android-espresso-test-with-viewlist.html

            //        onView(isAssignableFrom(Toolbar.class))
            //                .check(matches(Util.withToolbarTitle(
            //                        Is.<CharSequence>is("Inclusão de Usuário"))));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void assertPreencimentoDoEndereco() {
        onView(withId(R.id.logradouro)).check(matches(withText("Washington Soares")));
        onView(withId(R.id.bairro)).check(matches(withText("Alagadiço Novo")));
        onView(withId(R.id.uf)).check(matches(withText("CE")));
        onView(withId(R.id.cidade)).check(matches(withText("Fortaleza")));
    }
}
