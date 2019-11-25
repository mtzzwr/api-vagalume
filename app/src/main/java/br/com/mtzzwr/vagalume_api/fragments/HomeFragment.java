package br.com.mtzzwr.vagalume_api.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.mtzzwr.vagalume_api.R;

public class HomeFragment extends Fragment {

    TextView txtTitulo, txtData;
    ImageView gif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        gif = view.findViewById(R.id.gifHome);


        Glide.with(this)
                .load("https://media1.tenor.com/images/15a09f187de5da5ec503a09a9980c37c/tenor.gif?itemid=5538913")
                .into(gif);

        return view;
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
