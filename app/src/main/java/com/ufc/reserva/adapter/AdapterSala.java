package com.ufc.reserva.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.ufc.reserva.R;
import com.ufc.reserva.model.Sala;
import com.ufc.reserva.ui.Sala.DetalhesSala;

import java.util.List;

public class AdapterSala extends ArrayAdapter<Sala> {

    private List<Sala> salas;
    private Context context;
    private List<String> ids;

    public AdapterSala(@NonNull Context context, List<Sala> sala, List<String> ids) {
        super(context, 0, sala);
        this.context = context;
        this.salas = sala;
        this.ids = ids;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Sala sala = salas.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cardview_sala, null);

        Item i = new Item(convertView);
        i.textViewBloco.setText(sala.getBloco());
        i.textViewSala.setText(sala.getNome());
        if (!sala.isArcondicionado()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.imageViewAir.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
            }
        }
        if (!sala.isProjetor()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.imageViewProjetor.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
            }
        }
        if (!sala.isWifi()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.imageViewWifi.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
            }
        }
        if (sala.getQuant_pc() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.imageViewPc.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
            }
        }
        i.materialButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getContext(), DetalhesSala.class);
                        i.putExtra("id_sala", ids.get(position));
                        i.putExtra("nome_sala", sala.getNome());
                        i.putExtra("is_air", sala.isArcondicionado());
                        i.putExtra("is_proj", sala.isProjetor());
                        i.putExtra("is_wifi", sala.isWifi());
                        i.putExtra("quant_pc", sala.getQuant_pc());
                        i.putExtra("capacidade", sala.getCapacidade());
                        i.putExtra("is_disponivel", sala.isDisponibilidade());
                        context.startActivity(i);
                    }
                }
        );
        return convertView;
    }


    public class Item {
        protected ImageView imageViewSala;
        protected ImageView imageViewProjetor;
        protected ImageView imageViewWifi;
        protected ImageView imageViewAir;
        protected ImageView imageViewPc;
        protected TextView textViewSala;
        protected TextView textViewBloco;
        protected MaterialButton materialButton;

        public Item(View view) {
            initViews(view);

        }

        private void initViews(View view) {
            imageViewSala = view.findViewById(R.id.imgview_sala);
            imageViewProjetor = view.findViewById(R.id.icon_projetor);
            imageViewWifi = view.findViewById(R.id.icon_wifi);
            imageViewAir = view.findViewById(R.id.icon_air);
            imageViewPc = view.findViewById(R.id.icon_pc);
            textViewBloco = view.findViewById(R.id.textview_bloco);
            textViewSala = view.findViewById(R.id.textview_sala);
            materialButton = view.findViewById(R.id.detalhes);
        }

    }
}
