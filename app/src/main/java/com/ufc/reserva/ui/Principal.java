package com.ufc.reserva.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ufc.reserva.R;
import com.ufc.reserva.fragment.HistoricoReservas;
import com.ufc.reserva.fragment.ListarSalas;
import com.ufc.reserva.fragment.NoInternet;
import com.ufc.reserva.model.Usuario;
import com.ufc.reserva.ui.Usuario.LoginActivity;
import com.ufc.reserva.util.FirebaseUtil;
import com.ufc.reserva.util.LibraryClass;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FirebaseUtil
    private FirebaseUtil firebaseUtil = new FirebaseUtil();

    //Navigation View + DrawerLayour
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //Bottom Navigation
    private BottomNavigationView bottomNavigationView;

    //FragmentManager
    private FragmentManager fm;

    private Usuario usuario;

    private TextView txtview_name_user;

    private static AlertDialog.Builder builder;

    //Verifica se foi clicado
    private boolean[] ver_click = {false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        initFirebase();
        inicializarViews();
        builder = new AlertDialog.Builder(Principal.this);
    }

    private void initUsuario() {
        usuario = new Usuario();
        usuario.setEmail(firebaseUtil.getFirebaseAuth().getCurrentUser().getEmail());
    }

    private void initFirebase() {
        firebaseUtil.getFirebaseAuth().addAuthStateListener(
                new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null) {
                            Intent intent = new Intent(Principal.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            initUsuario();
                            firebaseUtil.getFirebase().child("users").child(firebaseUtil.getFirebaseAuth().getCurrentUser().getUid()).child("email").setValue(usuario.getEmail())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                            return;
                        }
                    }
                });

    }

    private void inicializarViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom);
        txtview_name_user = findViewById(R.id.name_user);
        fm = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Minhas reservas");
        toolbar.setTitleTextColor(getResources().getColor(R.color.sombra));
        //Drawer Layout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_draw, R.string.close_draw);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        //Bottom NavigationView
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        if (LibraryClass.isOnline(getApplicationContext()) == false) {
            open_error_internet();
        } else {
            open_historico_reservas();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (LibraryClass.isOnline(getApplicationContext()) == false) {
                    open_error_internet();
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_bottom_item_historico: {
                            if (ver_click[0] == false) {
                                ver_click[0] = true;
                                builder.setMessage("Ao realizar um clique longo será possivel excluir ou não sua reserva").setTitle("Informação");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            toolbar.setTitle("Historico de minhas reservas");
                            new Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            open_historico_reservas();
                                        }
                                    }, 1000
                            );
                            break;
                        }
                        case R.id.nav_bottom_item_my_reservas: {
                            if (ver_click[1] == false) {
                                ver_click[1] = true;
                                builder.setMessage("Ao realizar um clique longo será possivel excluir ou não sua reserva").setTitle("Informação");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            toolbar.setTitle("Minhas Reservas");
                            new Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            open_historico_reservas();

                                        }
                                    }, 1000
                            );
                            break;
                        }
                        case R.id.nav_bottom_item_salas: {
                            if (ver_click[2] == false) {
                                ver_click[2] = true;
                                builder.setMessage("Ao clicar em uma sala será possivel ver seus detalhes." +
                                        " Os icones em rosas claros indicam que a sala não possui o item, já o azul indica que a sala possui.").setTitle("Informação");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            toolbar.setTitle("Salas e laboratorios");
                            new Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            open_salas();

                                        }
                                    }, 1000
                            );
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firebaseUtil.getAuthStateListener() != null) {
            firebaseUtil.getFirebaseAuth().removeAuthStateListener(firebaseUtil.getAuthStateListener());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_sobre:
                menuItem.setCheckable(!menuItem.isChecked());
                break;
            case R.id.nav_item_config:
                menuItem.setCheckable(!menuItem.isChecked());
                break;
            case R.id.nav_item_logout:
                firebaseUtil.getFirebaseAuth().signOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void open_historico_reservas() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new HistoricoReservas());
        ft.commit();
    }

    private void open_salas() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, ListarSalas.newInstance());
        ft.commit();
    }

    private void open_error_internet() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, NoInternet.newInstance());
        ft.commit();
    }
}
