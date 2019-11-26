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
                .load("https://i.pinimg.com/originals/8e/aa/8f/8eaa8ffb622d3b229abc15d6879bc74c.gif")
                .into(gif);

        return view;
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
