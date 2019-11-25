package br.com.mtzzwr.vagalume_api.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import br.com.mtzzwr.vagalume_api.R;
import br.com.mtzzwr.vagalume_api.service.LyricsService;


public class LetrasFragment extends Fragment {

    TextView txtResultado, txtMusica, txtArtista;
    EditText edtArtista, edtMusica;
    Button btnBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_letras, container, false);

        txtResultado = view.findViewById(R.id.txtResultado);
        edtArtista = view.findViewById(R.id.edtArtista);
        edtMusica = view.findViewById(R.id.edtMusica);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        txtMusica = view.findViewById(R.id.txtNomeMusica);
        txtArtista = view.findViewById(R.id.txtNomeArtista);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String require_art = edtArtista.getText().toString();
                String require_mus = edtMusica.getText().toString();

                if(require_art.equals("") || require_mus.equals("")){
                    Toast.makeText(getContext(), "Preencha os campos", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        JSONObject retorno = null;
                        try {
                            retorno = new LyricsService(require_art, require_mus).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        if(retorno != null){
                            JSONArray array = retorno.getJSONArray("mus");

                            for(int j = 0; j < array.length(); j++){

                                // jsonObject que recebe o indice do array
                                JSONObject object = array.getJSONObject(j);

                                // coloca os dados do array no textView
                                txtMusica.setText(object.getString("name"));
                                txtResultado.setText(object.getString("text"));
                            }

                            txtArtista.setText(retorno.getString("name"));
                        }else{
                            Toast.makeText(getContext(), "Nada foi encontrado :(", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

        return view;
    }
    public static LetrasFragment newInstance() {
        return new LetrasFragment();
    }
}
