package com.ufc.reserva.ui.Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ufc.reserva.R;
import com.ufc.reserva.model.Usuario;
import com.ufc.reserva.ui.Principal;
import com.ufc.reserva.ui.Usuario.CadastroActivity;
import com.ufc.reserva.ui.base.Base;
import com.ufc.reserva.util.FirebaseUtil;
import com.ufc.reserva.util.LibraryClass;

public class LoginActivity extends Base implements View.OnClickListener {

    private FirebaseUtil firebaseUtil = new FirebaseUtil();

    private Usuario usuario;
    private Button loginButton;
    private TextView cadastrar;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        firebaseUtil.setAuthStateListener(getFirebaseAuthResultHandler());
    }

    @Override
    protected void initView() {
        email = (EditText) findViewById(R.id.username);
        senha = (EditText) findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.loading);
        cadastrar = (TextView) findViewById(R.id.txt_Cadastrar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginButton.setOnClickListener(this);
        cadastrar.setOnClickListener(this);
        toolbar.setTitleTextColor(getResources().getColor(R.color.sombra));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Login");
    }

    @Override
    protected void initUsuario() {
        usuario = new Usuario();
        usuario.setEmail(email.getText().toString());
    }

    @Override
    public void onClick(View view) {
        initUsuario();
        int id = view.getId();
        if (id == R.id.login) {
            String EMAIL = email.getText().toString();
            String SENHA = senha.getText().toString();
            boolean ok = true;
            if (EMAIL.isEmpty()) {
                email.setError("E-mail não informado!");
                ok = false;
            }
            if (SENHA.isEmpty()) {
                senha.setError("Por favor digite uma senha!");
                ok = false;
            } else if (SENHA.length() <= 5) {
                senha.setError(getString(R.string.invalid_password));
                ok = false;
            }
            if (ok) {
                loginButton.setEnabled(false);
                cadastrar.setEnabled(false);
                progressBar.setFocusable(false);
                openProgressBar();
                LOGIN();
            } else {

                closeProgressBar();
            }
        } else if (id == R.id.txt_Cadastrar) {
            open_cadastro(view);
        }
    }

    private void LOGIN() {
        if (LibraryClass.isOnline(getApplicationContext()) == true) {
            firebaseUtil.getFirebaseAuth().signInWithEmailAndPassword(usuario.getEmail(), senha.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            closeProgressBar();
                            loginButton.setEnabled(true);
                            cadastrar.setEnabled(true);
                            if (!task.isSuccessful()) {
                                Snackbar.make(loginButton, "Não foi possivel realizar o login!!", Snackbar.LENGTH_LONG).show();
                            }
                            return;
                        }
                    });
        } else {
            Snackbar.make(loginButton, "Erro de conexão -> Sem acesso a internet!!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        verifica_IF_LOGADO();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseUtil.getAuthStateListener() != null) {
            firebaseUtil.getFirebaseAuth().removeAuthStateListener(firebaseUtil.getAuthStateListener());
        }
    }

    private void verifica_IF_LOGADO() {
        if (firebaseUtil.getFirebaseAuth().getCurrentUser() != null) {
            open_principal();
        } else {
            firebaseUtil.getFirebaseAuth().addAuthStateListener(getFirebaseAuthResultHandler());
        }
    }

    private FirebaseAuth.AuthStateListener getFirebaseAuthResultHandler() {
        FirebaseAuth.AuthStateListener callback = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userFirebase = firebaseAuth.getCurrentUser();
                if (userFirebase == null) {
                    return;
                }
                open_principal();
            }
        };
        return (callback);
    }

    private void open_principal() {
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }

    public void open_cadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}
