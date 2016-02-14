package com.aplicacaodamonografia.unit;

import android.test.AndroidTestRunner;
import android.test.suitebuilder.annotation.SmallTest;

import com.aplicacaodamonografia.BuildConfig;
import com.aplicacaodamonografia.exception.EnderecoLoadException;
import com.aplicacaodamonografia.model.Endereco;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by pablo on 13/02/16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EnderecoTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    @Config(manifest = Config.NONE)
    public void deveriaConverterJsonParaEndereco() {
        String jsonDoEndereco = "{\"uf\":\"PB\",\"cidade\":\"Patos\", \"bairro\":\"Centro\", \"logradouro\":\"Rua 1\"}";
        Endereco endereco = Endereco.fromJson(jsonDoEndereco);
        assertEquals("UF:", "PB", endereco.getUf());
        assertEquals("Cidade:", "Patos", endereco.getCidade());
        assertEquals("Bairro:", "Centro", endereco.getBairro());
        assertEquals("Logradouro:", "Rua 1", endereco.getLogradouro());
    }

    @Test
    @Config(manifest = Config.NONE)
    public void deveriaLancarExcecaoAoConverterJsonParaEndereco() {
        thrown.expect(EnderecoLoadException.class);
        thrown.expectMessage("Erro ao converter endereço.");

        String jsonDoEndereco = "";
        Endereco endereco = Endereco.fromJson(jsonDoEndereco);
    }

}
