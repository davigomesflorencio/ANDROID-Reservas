package com.ufc.reserva.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ufc.reserva.R;

public class ItemReserva extends RecyclerView.ViewHolder {
    CardView cardView;
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
        cardView = view.findViewById(R.id.cardviewreserva);
    }
}