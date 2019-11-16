package com.ufc.reserva.ui.Sala;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ufc.reserva.R;
import com.ufc.reserva.ui.Reserva.PedidoReserva;

public class DetalhesSala extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private Button bt_click_reserva;
    private Toolbar toolbar;

    private ImageView imageViewProjetor;
    private ImageView imageViewWifi;
    private ImageView imageViewAir;
    private ImageView imageViewPc;
    private ImageView imageViewDisponivel;

    private TextView textview_capacidade;
    private TextView textview_disponivel;
    private TextView textView_quantpc;
    private int id_sala;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_sala);
        initView();
        getDataActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void initView() {
        floatingActionButton = findViewById(R.id.fbt_localizacao);
        bt_click_reserva = findViewById(R.id.bt_reservar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Detalhes da Sala");

        imageViewProjetor = findViewById(R.id.icon_projetor);
        imageViewWifi = findViewById(R.id.icon_wifi);
        imageViewAir = findViewById(R.id.icon_airf);
        imageViewPc = findViewById(R.id.icon_pc);
        imageViewDisponivel = findViewById(R.id.imgview_disponivel);

        textview_capacidade = findViewById(R.id.textview_capacidade);
        textview_disponivel = findViewById(R.id.textview_disponibilidade);
        textView_quantpc = findViewById(R.id.textview_quantpc);

        floatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DetalhesSala.this, LocalizacaoSala.class);
                        startActivity(i);
                    }
                }
        );
        bt_click_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetalhesSala.this, PedidoReserva.class);
                final Intent intent = getIntent();

                i.putExtra("id_sala", intent.getStringExtra("id_sala"));
                i.putExtra("nome_sala", intent.getStringExtra("nome_sala"));
                i.putExtra("is_air", intent.getBooleanExtra("is_air", true));
                i.putExtra("is_proj", intent.getBooleanExtra("is_proj", true));
                i.putExtra("is_wifi", intent.getBooleanExtra("is_wifi", true));
                i.putExtra("quant_pc", intent.getIntExtra("quant_pc", 0));
                startActivity(i);
            }
        });
    }

    private void getDataActivity() {
        Intent intent = getIntent();
        if (intent != null) {
            toolbar.setTitle(intent.getStringExtra("nome_sala"));
            id_sala = Integer.parseInt(intent.getStringExtra("id_sala"));
            if (!intent.getBooleanExtra("is_air", true)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewAir.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
                }
            }
            if (!intent.getBooleanExtra("is_proj", true)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewProjetor.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
                }
            }
            if (!intent.getBooleanExtra("is_wifi", true)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewWifi.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
                }
            }
            if (intent.getIntExtra("quant_pc", 0) == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewPc.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
                }
            }
            textview_capacidade.setText("Capacidade : " + intent.getIntExtra("capacidade", 0));
            textview_disponivel.setText(intent.getBooleanExtra("is_disponivel", false) ? "Disponível para reserva" : "Indisponível para reserva");
            textView_quantpc.setText("Quantidade de pc's disponíveis :" + intent.getIntExtra("quant_pc", 0));
            if (!intent.getBooleanExtra("is_disponivel", false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewDisponivel.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
                }
            }
        }
    }
}
