package com.ufc.reserva.model;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Usuario {

    private String nome;
    private String email;

    public Usuario() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Você usa @Exclude nos métodos getter e setter quando
    // o campo subjacente é privado e todo o acesso deve passar por esses acessadores
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("email", email);
        return result;
    }
}
