package com.ufc.reserva.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ufc.reserva.R;
import com.ufc.reserva.SplashScreen;
import com.ufc.reserva.adapter.AdapterReserva;
import com.ufc.reserva.model.Reserva;
import com.ufc.reserva.ui.Usuario.LoginActivity;
import com.ufc.reserva.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;


public class HistoricoReservas extends Fragment {

    private FirebaseUtil firebaseUtil;

    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;
    private AdapterReserva adapterReserva;
    private List<String> ids;

    public HistoricoReservas() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUtil = new FirebaseUtil();
        ids = get_id_reservasFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico_reservas, container, false);
        initView(view);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        getReservasFirebase();

                    }
                }, 1000
        );
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.lista_historico);
        lottieAnimationView = view.findViewById(R.id.lottie);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }


    private void getReservasFirebase() {
        firebaseUtil.getFirebase().child("reservas").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!lottieAnimationView.isAnimating()) {
                            lottieAnimationView.playAnimation();
                            lottieAnimationView.setVisibility(View.VISIBLE);
                        }
                        final List<Reserva> aux = new ArrayList<>();
                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            if (ids.contains(noteSnapshot.getKey())) {
                                Reserva r = noteSnapshot.getValue(Reserva.class);
                                aux.add(r);
                            }
                        }
                        adapterReserva = new AdapterReserva(getContext(), aux, ids);
                        if (lottieAnimationView.isAnimating()) {
                            lottieAnimationView.cancelAnimation();
                            lottieAnimationView.setVisibility(View.GONE);
                        }
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(adapterReserva);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("HistoricoReservas", databaseError.getMessage());
                    }
                }
        );
    }

    private List<String> get_id_reservasFirebase() {
        final List<String> list = new ArrayList<>();
        firebaseUtil.getFirebase().child("users").child(firebaseUtil.getFirebaseAuth().getCurrentUser().getUid()).
                child("lista_reservas").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            String s = noteSnapshot.getValue(String.class);
                            list.add(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("AdapterReserva", databaseError.getMessage());
                    }
                }
        );

        return list;
    }
}
