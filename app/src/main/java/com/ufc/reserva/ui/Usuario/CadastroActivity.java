package com.ufc.reserva.ui.Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.ufc.reserva.R;
import com.ufc.reserva.model.Usuario;
import com.ufc.reserva.ui.base.Base;
import com.ufc.reserva.util.FirebaseUtil;

public class CadastroActivity extends Base implements View.OnClickListener {

    private FirebaseUtil firebaseUtil;

    private Usuario usuario;
    private FloatingActionButton FabCadastrar;
    private Toolbar toolbar;
    private EditText senha_repetida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        firebaseUtil = new FirebaseUtil();
        initView();
    }

    @Override
    public void onBackPressed() {
        finish();
        return;
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

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nome = (EditText) findViewById(R.id.textInputNome);
        email = (EditText) findViewById(R.id.textInputEmail);
        senha = (EditText) findViewById(R.id.textInputSenha);
        senha_repetida = (EditText) findViewById(R.id.textInputSenhaRepetida);
        progressBar = findViewById(R.id.sign_up_progress);
        FabCadastrar = findViewById(R.id.bt_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro");
        FabCadastrar.setOnClickListener(this);
        toolbar.setTitleTextColor(getResources().getColor(R.color.sombra));
    }

    @Override
    protected void initUsuario() {
        usuario = new Usuario();
        usuario.setNome(nome.getText().toString());
        usuario.setEmail(email.getText().toString());
    }

    @Override
    public void onClick(View view) {
        initUsuario();
        String NOME = nome.getText().toString();
        String EMAIL = email.getText().toString();
        String SENHA = senha.getText().toString();
        String senha_r = senha_repetida.getText().toString();
        boolean ok = true;
        if (NOME.isEmpty()) {
            nome.setError(getString(R.string.invalid_username));
            ok = false;
        }
        if (EMAIL.isEmpty()) {
            email.setError(getString(R.string.invalid_email));
            ok = false;
        }
        if (SENHA.isEmpty()) {
            senha.setError("Digite algo, senha vazia é invalida");
            ok = false;
        } else if (SENHA.length() <= 5) {
            senha.setError(getString(R.string.invalid_password));
            ok = false;
        } else if (!SENHA.equals(senha_r)) {
            senha_repetida.setError("As senhas devem ser iguais");
            ok = false;
        }
        if (ok) {
            FabCadastrar.setEnabled(false);
            progressBar.setFocusable(false);
            openProgressBar();
            salvarUsuario();
        } else {
            closeProgressBar();
        }
    }

    private void salvarUsuario() {
        firebaseUtil.getFirebaseAuth().createUserWithEmailAndPassword(usuario.getEmail(), senha.getText().toString())
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                closeProgressBar();
                                if (task.isSuccessful()) {
                                    Snackbar.make(toolbar, "Sucesso!! Sua conta foi cadastrada! Você será direcionado(a) para a tela de login", Snackbar.LENGTH_LONG).show();
//                                    finish();
                                } else {
                                    Snackbar.make(toolbar, "Não foi possivel realizar cadastro de sua conta!!!", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }
                );
    }

}
