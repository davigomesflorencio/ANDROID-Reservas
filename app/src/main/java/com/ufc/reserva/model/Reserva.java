package com.ufc.reserva.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Reserva {
    private int idsala;
    private String data;
    private String horario;

    public Reserva(int idsala, String data, String horario) {
        this.idsala = idsala;
        this.data = data;
        this.horario = horario;
    }

    public Reserva() {
    }

    public int getIdsala() {
        return idsala;
    }

    public void setIdsala(int idsala) {
        this.idsala = idsala;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idsala", idsala);
        result.put("data", data);
        result.put("horario", horario);
        return result;
    }
}
