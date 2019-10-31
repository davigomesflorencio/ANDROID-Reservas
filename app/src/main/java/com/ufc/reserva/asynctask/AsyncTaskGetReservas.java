package com.ufc.reserva.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ufc.reserva.model.Reserva;
import com.ufc.reserva.model.Sala;
import com.ufc.reserva.util.FirebaseUtil;

public class AsyncTaskGetReservas extends AsyncTask<String, Void, Void> {

    //parametros/progreso/retorno
    private FirebaseUtil firebaseUtil;
    private int id_sala;
    private Reserva reserva;
    private TextView txtViewnome_sala;
    private TextView txtViewbloco;
    private TextView txtViewhorario;
    private TextView txtViewdata;

    public AsyncTaskGetReservas(Reserva reserva, TextView txtViewnome_sala, TextView txtViewbloco, TextView txtViewhorario, TextView txtViewdata) {
        this.id_sala = reserva.getIdsala();
        this.reserva = reserva;
        this.txtViewnome_sala = txtViewnome_sala;
        this.txtViewbloco = txtViewbloco;
        this.txtViewhorario = txtViewhorario;
        this.txtViewdata = txtViewdata;
    }

    @Override
    protected void onPreExecute() {
        firebaseUtil = new FirebaseUtil();
    }

    @Override
    protected Void doInBackground(String... strings) {
        firebaseUtil.getFirebase().child("salas").child(String.valueOf(id_sala)).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Sala s = dataSnapshot.getValue(Sala.class);
                        txtViewnome_sala.setText("Sala : " + s.getNome());
                        txtViewbloco.setText("Bloco : " + s.getBloco());
                        txtViewhorario.setText("Horario : " + reserva.getHorario());
                        txtViewdata.setText("Data : " + reserva.getData());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("AsyncTaskReserva", databaseError.getMessage());
                    }

                }
        );
        return null;
    }

}
