package com.ufc.reserva.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ufc.reserva.R;


public class NoInternet extends Fragment {

    public NoInternet() {
    }

    public static NoInternet newInstance() {
        NoInternet fragment = new NoInternet();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_internet, container, false);
    }

}
