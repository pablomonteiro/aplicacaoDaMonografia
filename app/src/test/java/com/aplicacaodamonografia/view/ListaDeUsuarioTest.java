package com.aplicacaodamonografia.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.AndroidTestRunner;
import android.test.suitebuilder.annotation.LargeTest;

import com.aplicacaodamonografia.activity.ListaDeUsuario;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.*;



/**
 * Created by pablo on 14/02/16.
 */
@LargeTest
public class ListaDeUsuarioTest extends ActivityInstrumentationTestCase2 {


    @Rule
    public ActivityTestRule<ListaDeUsuario> mActivityRule =
            new ActivityTestRule<>(ListaDeUsuario.class);

    public void verificaListaPreenchida() {
//        onVi
        Assert.assertTrue(true);
    }

}
