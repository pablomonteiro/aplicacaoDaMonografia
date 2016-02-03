package com.aplicacaodamonografia.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pablo on 31/01/16.
 */
public class Endereco {

    private String uf;
    private String cidade;
    private String bairro;
    private String logradouro;

    public Endereco() {}

    public String getUf() {
        return uf;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public static Endereco fromJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            Endereco endereco = new Endereco();
            endereco.setUf(object.getString("uf"));
            endereco.setCidade(object.getString("cidade"));
            endereco.setBairro(object.getString("bairro"));
            endereco.setLogradouro(object.getString("logradouro"));
            return endereco;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
