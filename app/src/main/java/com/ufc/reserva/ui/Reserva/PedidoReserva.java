package com.ufc.reserva.ui.Reserva;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.ufc.reserva.R;
import com.ufc.reserva.model.Reserva;
import com.ufc.reserva.util.FirebaseUtil;

import java.util.Calendar;

public class PedidoReserva extends AppCompatActivity {

    private FirebaseUtil firebaseUtil;
    private Calendar myCalendar;
    private EditText edittext;
    private Spinner spinner;
    private Toolbar toolbar;
    private MaterialButton materialButton;

    protected ImageView imageViewProjetor;
    protected ImageView imageViewWifi;
    protected ImageView imageViewAir;
    protected ImageView imageViewPc;

    private int id_sala;

    private static AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_reserva);
        firebaseUtil = new FirebaseUtil();
        initView();
        builder = new AlertDialog.Builder(PedidoReserva.this);
    }

    private void getDataActivity() {
        Intent intent = getIntent();
        if (intent != null) {
            toolbar.setTitle(intent.getStringExtra("nome_sala"));

            id_sala = Integer.parseInt(intent.getStringExtra("id_sala"));
//            Log.d("Cadastro", String.valueOf(id_sala));

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
        }
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
        myCalendar = Calendar.getInstance();
        edittext = (EditText) findViewById(R.id.data);
        spinner = findViewById(R.id.horario);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        materialButton = findViewById(R.id.fbt_reservar);

        imageViewProjetor = findViewById(R.id.icon_projetor);
        imageViewWifi = findViewById(R.id.icon_wifi);
        imageViewAir = findViewById(R.id.icon_airf);
        imageViewPc = findViewById(R.id.icon_pc);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditText(year, monthOfYear, dayOfMonth);
            }
        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PedidoReserva.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Pedido de reserva de sala");
        getDataActivity();
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (edittext.getText() != null) {
                    builder.setMessage("Tem certeza que deseja realizar o pedido de reserva desta sala?").setTitle("Pedido de reserva");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setReservasFirebase(view, id_sala, edittext.getText().toString(), spinner.getSelectedItem().toString());
                        }
                    });
                    builder.setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }

    private void updateEditText(int year, int monthOfYear, int dayOfMonth) {
        String dia = String.valueOf(dayOfMonth), mes = String.valueOf(monthOfYear);
        if (dayOfMonth < 10)
            dia = "0" + dayOfMonth;
        if (monthOfYear < 10)
            mes = "0" + monthOfYear;
        edittext.setText(dia + "/" + mes + "/" + year);
    }

    private void setReservasFirebase(final View view, final int id_sala, final String data, final String horario) {
        Reserva r = new Reserva(id_sala, data, horario);
        String key = firebaseUtil.getFirebase().child("reservas").push().getKey();
        firebaseUtil.getFirebase().child("reservas").child(key).setValue(r.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view, "Sua reserva foi adicionada!", Snackbar.LENGTH_LONG).setAction("Mensagem", null).show();
                        } else {
                            Snackbar.make(view, "Sua reserva não foi adicionada -> Ocorreu um erro inesperado!", Snackbar.LENGTH_LONG).setAction("Mensagem", null).show();
                        }
                    }
                });
        firebaseUtil.getFirebase().child("users").child(firebaseUtil.getFirebaseAuth().getCurrentUser().getUid()).child("lista_reservas").child(key).setValue(key)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }
}
