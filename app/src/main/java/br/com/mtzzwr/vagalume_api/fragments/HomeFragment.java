package br.com.mtzzwr.vagalume_api.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.mtzzwr.vagalume_api.R;

public class HomeFragment extends Fragment {

    TextView txtTitulo, txtData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        return view;
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
