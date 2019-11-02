package com.ufc.reserva.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ufc.reserva.R;
import com.ufc.reserva.asynctask.AsyncTaskGetReservas;
import com.ufc.reserva.model.Reserva;
import com.ufc.reserva.util.FirebaseUtil;

import java.util.List;

public class AdapterReserva extends RecyclerView.Adapter<ItemReserva> {

    private FirebaseUtil firebaseUtil;
    private Context context;
    private List<Reserva> reservas;
    private List<String> ids;

    public AdapterReserva(Context context, List<Reserva> reservas, List<String> ids) {
        this.context = context;
        this.reservas = reservas;
        this.ids = ids;
        firebaseUtil = new FirebaseUtil();
    }

    @NonNull
    @Override
    public ItemReserva onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_reserva, parent, false);
        ItemReserva itemReserva = new ItemReserva(view);
        return itemReserva;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemReserva holder, final int position) {
        Reserva r = reservas.get(position);
        AsyncTaskGetReservas asyncTaskGetReservas = new AsyncTaskGetReservas(r, holder.nomesala, holder.bloco, holder.horario, holder.data);
        asyncTaskGetReservas.execute();
        holder.cardView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Deseja excluir esta reserva?").setTitle("Excluir Reserva");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                excluir_reserva(ids.get(position));
                                Snackbar.make(holder.itemView, "Reserva excluida", Snackbar.LENGTH_LONG).setAction("Mensagem", null).show();
                            }
                        });
                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return true;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    private void excluir_reserva(String id_reserva) {
        firebaseUtil.getFirebase().child("reservas").child(id_reserva).removeValue();
        firebaseUtil.getFirebase().child("users").child(firebaseUtil.getFirebaseAuth().getCurrentUser().getUid()).child("lista_reservas").child(id_reserva).removeValue();
    }

}
