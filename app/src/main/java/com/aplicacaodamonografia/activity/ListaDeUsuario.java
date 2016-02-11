package com.aplicacaodamonografia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aplicacaodamonografia.R;
import com.aplicacaodamonografia.adapter.UsuarioAdapter;
import com.aplicacaodamonografia.model.Endereco;
import com.aplicacaodamonografia.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaDeUsuario extends AppCompatActivity {

    ListView listaDeDados;
    List<Usuario> listaDeUsuario;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_usuario);
        init();
        preencheLista();
        preparaToolbar();
        preparaFloatingButton();
    }

    private void init() {
        listaDeDados = (ListView) findViewById(R.id.lista_de_dados);
        listaDeUsuario = new ArrayList<Usuario>();
    }

    private void preparaToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void preparaFloatingButton() {
        FloatingActionButton adicionaItem = (FloatingActionButton) findViewById(R.id.adicionaItem);
        adicionaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaDeUsuario.this, FormularioActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Usuario usuario = new Usuario();
            usuario.setNome(data.getStringExtra("nomeDoUsuario"));
            usuario.setTelefone(data.getStringExtra("telefoneDoUsuario"));
            Endereco endereco = new Endereco();
            endereco.setCidade(data.getStringExtra("cidadeDoUsuario"));
            endereco.setUf(data.getStringExtra("ufDoUsuario"));
            endereco.setBairro(data.getStringExtra("bairroDoUsuario"));
            usuario.setEndereco(endereco);
            listaDeUsuario.add(usuario);
            preencheLista();
            Toast.makeText(getApplicationContext(), "Usuário incluído com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Problema ao incluir usuário.", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencheLista() {
        ArrayAdapter<Usuario> adapter = new UsuarioAdapter(this,
                                            R.layout.usuario_item,
                                            listaDeUsuario);
        listaDeDados.setAdapter(adapter);
    }

}
