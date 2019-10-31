package com.ufc.reserva.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Sala {
    private String nome;
    private String bloco;
    private boolean disponibilidade;
    private int capacidade;
    private boolean wifi;
    private boolean projetor;
    private boolean arcondicionado;
    private int quant_pc;

    public Sala(String nome, String bloco, boolean disponibilidade, int capacidade, boolean wifi, boolean projetor, boolean arcondicionado, int quant_pc) {

        this.nome = nome;
        this.bloco = bloco;
        this.disponibilidade = disponibilidade;
        this.capacidade = capacidade;
        this.wifi = wifi;
        this.projetor = projetor;
        this.arcondicionado = arcondicionado;
        this.quant_pc = quant_pc;
    }

    public Sala() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isProjetor() {
        return projetor;
    }

    public void setProjetor(boolean projetor) {
        this.projetor = projetor;
    }

    public boolean isArcondicionado() {
        return arcondicionado;
    }

    public void setArcondicionado(boolean arcondicionado) {
        this.arcondicionado = arcondicionado;
    }

    public int getQuant_pc() {
        return quant_pc;
    }

    public void setQuant_pc(int quant_pc) {
        this.quant_pc = quant_pc;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("bloco", bloco);
        result.put("disponibilidade", disponibilidade);
        result.put("capacidade", capacidade);
        result.put("wifi", wifi);
        result.put("projetor", projetor);
        result.put("arcondicionado", arcondicionado);
        result.put("quant_pc", quant_pc);
        return result;
    }
}
