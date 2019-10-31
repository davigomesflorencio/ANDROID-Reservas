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
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ufc.reserva.R;
import com.ufc.reserva.SplashScreen;
import com.ufc.reserva.adapter.AdapterSala;
import com.ufc.reserva.model.Sala;
import com.ufc.reserva.ui.Usuario.LoginActivity;
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


//    private List<Sala> initSala() {
////        List<Sala> salas = new ArrayList<Sala>();
////        salas.add(new Sala(1, "Sala 1", "Bloco 1", true, 40, false, true, true, 0));
////        salas.add(new Sala(2, "Sala 2", "Bloco 1", true, 40, false, true, false, 0));
////        salas.add(new Sala(3, "Sala 3", "Bloco 1", true, 40, false, true, true, 0));
////        salas.add(new Sala(4, "Sala 4", "Bloco 1", true, 40, false, true, false, 0));
////        salas.add(new Sala(5, "Sala 5", "Bloco 2", true, 40, false, false, false, 20));
////        salas.add(new Sala(6, "Sala 6", "Bloco 2", true, 40, false, false, false, 20));
////        salas.add(new Sala(7, "Sala 7", "Bloco 2", true, 40, false, true, true, 0));
////        salas.add(new Sala(8, "Sala 8", "Bloco 3", true, 40, false, true, false, 0));
////        salas.add(new Sala(9, "Sala 9", "Bloco 3", true, 40, false, true, true, 0));
////        salas.add(new Sala(10, "Sala 10", "Bloco 3", true, 40, false, true, false, 0));
//        return salas;
//    }

//    private void addSalasFirebase(List<Sala> salas) {
//        for (Sala s : salas) {
//            firebaseUtil.getFirebase().child("salas").child(String.valueOf(s.getId())).setValue(s.toMap())
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
////                            if (task.isSuccessful()) {
////                                Toast.makeText(getContext(), "Deu certo", Toast.LENGTH_SHORT).show();
////                            }
//                        }
//                    });
//        }
//    }
}
