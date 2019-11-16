package com.ufc.reserva.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ufc.reserva.R;
import com.ufc.reserva.adapter.AdapterSala;
import com.ufc.reserva.model.Sala;
import com.ufc.reserva.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

public class ListarSalas extends Fragment {

    private GridView gridview;
    private AdapterSala adapterSala;
    private LottieAnimationView lottieAnimationView;
    private FirebaseUtil firebaseUtil;

    public ListarSalas() {

    }

    public static ListarSalas newInstance() {
        ListarSalas fragment = new ListarSalas();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUtil = new FirebaseUtil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_salas, container, false);
        initView(view);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        getSalasFirebase();
                    }
                }, 1000
        );
        return view;
    }

    private void initView(View view) {
        lottieAnimationView = view.findViewById(R.id.lottie);
        gridview = view.findViewById(R.id.gridview);
    }

    private void getSalasFirebase() {

        firebaseUtil.getFirebase().child("salas").addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!lottieAnimationView.isAnimating()) {
                            lottieAnimationView.playAnimation();
                            lottieAnimationView.setVisibility(View.VISIBLE);
                        }
                        final List<Sala> aux = new ArrayList<Sala>();
                        final List<String> ids = new ArrayList<>();
                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            Sala s = noteSnapshot.getValue(Sala.class);
                            aux.add(s);
                            ids.add(noteSnapshot.getKey());
                        }

                        adapterSala = new AdapterSala(getContext(), aux, ids);
                        if (lottieAnimationView.isAnimating()) {
                            lottieAnimationView.cancelAnimation();
                            lottieAnimationView.setVisibility(View.GONE);
                        }
                        gridview.setAdapter(adapterSala);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("ListarSalas", databaseError.getMessage());
                    }
                }
        );
    }

}
