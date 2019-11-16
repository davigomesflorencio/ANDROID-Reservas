package com.ufc.reserva.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ufc.reserva.R;

public class ItemReserva extends RecyclerView.ViewHolder {
    MaterialButton materialButton;
    TextView nomesala;
    TextView bloco;
    TextView horario;
    TextView data;

    public ItemReserva(@NonNull View itemView) {
        super(itemView);
        init(itemView);
    }

    protected void init(View view) {
        nomesala = view.findViewById(R.id.nome_sala);
        bloco = view.findViewById(R.id.bloco);
        horario = view.findViewById(R.id.horario);
        data = view.findViewById(R.id.dia);
        materialButton = view.findViewById(R.id.bt_excluir);
    }
}