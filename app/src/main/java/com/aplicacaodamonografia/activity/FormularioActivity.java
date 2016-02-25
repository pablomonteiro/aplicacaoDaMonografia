package com.aplicacaodamonografia.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplicacaodamonografia.R;
import com.aplicacaodamonografia.exception.ConnectionException;
import com.aplicacaodamonografia.exception.EnderecoLoadException;
import com.aplicacaodamonografia.model.Endereco;
import com.aplicacaodamonografia.webconnection.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        preparaAcaoDoBotaoPesquisaDeCep();
        preparaAcaoDoBotaoLimpar();
        preparaAcaoDoBotaoConfirmar();
    }

    private void preparaAcaoDoBotaoPesquisaDeCep() {
        Button btnPesquisaCep = (Button) findViewById(R.id.btn_pesquisar);
        btnPesquisaCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = ((EditText) findViewById(R.id.cep)).getText().toString();
                new ConsultaDeCep().execute(cep);
            }
        });
    }

    private void preparaAcaoDoBotaoConfirmar() {
        Button btnConfirmar = (Button) findViewById(R.id.btn_confirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResult = new Intent();
                intentResult.putExtra("nomeDoUsuario", ((TextView) findViewById(R.id.nome)).getText().toString());
                intentResult.putExtra("telefoneDoUsuario", ((TextView) findViewById(R.id.telefone)).getText().toString());
                intentResult.putExtra("cidadeDoUsuario", ((TextView) findViewById(R.id.cidade)).getText().toString());
                intentResult.putExtra("ufDoUsuario", ((TextView) findViewById(R.id.uf)).getText().toString());
                intentResult.putExtra("bairroDoUsuario", ((TextView) findViewById(R.id.bairro)).getText().toString());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });
    }

    private void preparaAcaoDoBotaoLimpar() {
        Button btnLimpar = (Button) findViewById(R.id.limpar);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.nome)).setText("");
                ((TextView) findViewById(R.id.telefone)).setText("");
                ((TextView) findViewById(R.id.cep)).setText("");
                preencheInformacoesDoEndereco(new Endereco());
            }
        });
    }

    private void preencheInformacoesDoEndereco(Endereco endereco) {
        ((TextView) findViewById(R.id.uf)).setText(endereco.getUf());
        ((TextView) findViewById(R.id.cidade)).setText(endereco.getCidade());
        ((TextView) findViewById(R.id.bairro)).setText(endereco.getBairro());
        ((TextView) findViewById(R.id.logradouro)).setText(endereco.getLogradouro());
    }

    public class ConsultaDeCep extends AsyncTask<String, Void, String> {

        private static final String SITE_CONSULTA_CEP = "http://cep.republicavirtual.com.br/web_cep.php?formato=json&cep=";
        private static final String MSG_EXCEPTION = "Unable to retrieve data. URL may be invalid.";
        private static final String MSG_ERROR_CONNECT_FAIL = "Erro de conexão.";
        private static final String MSG_ERROR_INVALID_CEP = "CEP Inválido!";

        @Override
        protected String doInBackground(String... params) {
            try {
                return buscaDadosDoEndereco(params[0]);
            } catch (ConnectionException e) {
                return MSG_EXCEPTION;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(MSG_EXCEPTION.equals(result)) {
                Toast.makeText(FormularioActivity.this, MSG_ERROR_CONNECT_FAIL, Toast.LENGTH_LONG).show();
            } else {
                try {
                    Endereco endereco = Endereco.fromJson(result);
                    preencheInformacoesDoEndereco(endereco);
                } catch(EnderecoLoadException e) {
                    Toast.makeText(FormularioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        private String buscaDadosDoEndereco(String cep) throws ConnectionException {
            try {
                String url = SITE_CONSULTA_CEP.concat(cep);
                HttpURLConnection connection = Connection.createConnection(url);
                InputStream inputStream = connection.getInputStream();
                return convertInputStreamToString(inputStream);
            } catch(Exception e) {
                throw new ConnectionException("Problema na conexão!");
            }
        }

        private String convertInputStreamToString(InputStream stream) throws IOException, UnsupportedEncodingException {
            int length = 500; //Tamanho do buffer de resposta
            Reader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[length];
            reader.read(buffer);
            return new String(buffer);
        }

        private void preencheInformacoesDoEndereco(Endereco endereco) {
            if(endereco != null) {
                ((TextView) findViewById(R.id.uf)).setText(endereco.getUf());
                ((TextView) findViewById(R.id.cidade)).setText(endereco.getCidade());
                ((TextView) findViewById(R.id.bairro)).setText(endereco.getBairro());
                ((TextView) findViewById(R.id.logradouro)).setText(endereco.getLogradouro());
            } else {
                Toast.makeText(FormularioActivity.this, MSG_ERROR_INVALID_CEP, Toast.LENGTH_LONG).show();
            }
        }
    }

}
