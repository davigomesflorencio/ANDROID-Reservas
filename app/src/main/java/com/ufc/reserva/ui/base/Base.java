package com.ufc.reserva.ui.base;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

abstract public class Base extends AppCompatActivity {
    protected EditText nome;
    protected EditText email;
    protected EditText senha;
    protected ProgressBar progressBar;

    protected void showSnackBar(String msg) {
        Snackbar.make(progressBar, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    protected void openProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void closeProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    abstract protected void initView();

    abstract protected void initUsuario();

}
