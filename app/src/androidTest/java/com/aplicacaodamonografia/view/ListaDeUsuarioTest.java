package com.aplicacaodamonografia.view;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ListView;

import com.aplicacaodamonografia.R;
import com.aplicacaodamonografia.activity.ListaDeUsuario;
import com.aplicacaodamonografia.adapter.UsuarioAdapter;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by pablo on 20/02/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListaDeUsuarioTest {

    @Rule
    public ActivityTestRule<ListaDeUsuario> mActivityRule = new ActivityTestRule<>(
            ListaDeUsuario.class);

    @Test
    public void deveriaVerificarTipoDaLista() {
        onData(AllOf.allOf(Is.is(IsInstanceOf.instanceOf(UsuarioAdapter.class))));
    }

    @Test
    public void deveriaVerificarQuantidadeDeItensNaLista() {
        onView(withId(R.id.lista_de_dados)).check(matches(Matchers.withListSize(2)));
    }

    @Test
    public void deveriaVerificarConteudoDaPrimeiraLinha() {
        verificaLinhaDaLista(0, "Usuário 1", "988369900");
    }

    @Test
    public void deveriaVerificarConteudoDaSegundaLinha() {
        verificaLinhaDaLista(1, "Usuário 2", "988369901");
    }

    private void verificaLinhaDaLista(int posicao, String nome, String telefone) {
        onData(CoreMatchers.anything())
                .inAdapterView(withId(R.id.lista_de_dados))
                .atPosition(posicao)
                .check(matches(hasDescendant(withText(nome))))
                .check(matches(hasDescendant(withText(telefone))))
                .check(matches(hasDescendant(withText("Patos"))))
                .check(matches(hasDescendant(withText("PB"))))
                .check(matches(hasDescendant(withText("Centro"))))
                .check(matches(hasDescendant(withText("Rua 1"))));
    }

    static class Matchers {
        public static Matcher<View> withListSize (final int size) {
            return new TypeSafeMatcher<View>() {
                @Override public boolean matchesSafely (final View view) {
                    return ((ListView) view).getChildCount () == size;
                }

                @Override public void describeTo (final Description description) {
                    description.appendText ("ListView deveria ter " + size + " items");
                }
            };
        }
    }

}
