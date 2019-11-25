package br.com.mtzzwr.vagalume_api.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.mtzzwr.vagalume_api.R;
import br.com.mtzzwr.vagalume_api.service.ArtistService;
import br.com.mtzzwr.vagalume_api.view.DetalhesArtistaActivity;

public class ArtistaFragment extends Fragment implements View.OnClickListener {

    EditText txtPesquisa;
    Button btnPesquisa, btnDetalhes;
    TextView txtNome, txtGenero, txtViews, lblMusica, lblGenero;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artista, container, false);

        txtPesquisa = view.findViewById(R.id.txtPesquisar);
        btnPesquisa = view.findViewById(R.id.btnPesquisar);
        btnDetalhes = view.findViewById(R.id.btnDetalhes);
        txtNome = view.findViewById(R.id.txtNome);
        txtGenero = view.findViewById(R.id.txtGenero);
        txtViews = view.findViewById(R.id.txtViews);
        imageView = view.findViewById(R.id.img);
        lblMusica = view.findViewById(R.id.lblMusica);
        lblGenero = view.findViewById(R.id.lblGenero);

        btnDetalhes.setVisibility(View.INVISIBLE);
        btnDetalhes.setOnClickListener(this);

        btnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pega o que foi digitado no editText e guarda em uma String
                String require = txtPesquisa.getText().toString();

                if(txtPesquisa.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Preencha o campo", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        require = removerAcentos(require);

                        // cria um jsonobject e faz a requisição na classe responsável por acessar a api
                        JSONObject retorno = null;
                        try {
                            retorno = new ArtistService(require).execute().get();
                        } catch (ExecutionException e) {
                            Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        if(retorno != null) {

                            // cria uma string que contém o caminho da imagem do artista
                            String foto = "https://s2.vagalume.com/" + retorno.getString("pic_small");

                            // instância do Picasso para carregar a imagem no imageView
                            Picasso.with(getContext()).load(foto).into(imageView);

                            // carrega o nome do artista no textView
                            txtNome.setText(retorno.getString("desc"));

                            // cria um jsonArray para obter os dados do array dentro do jsonObject
                            JSONArray genre = retorno.getJSONArray("genre");

                            // cria um for para obter o primeiro indice do array
                            for (int j = 0; j < genre.length(); j++) {

                                // jsonObject que recebe o indice do array
                                JSONObject object = genre.getJSONObject(0);

                                // coloca os dados do array no textView
                                lblGenero.setText("Gênero");
                                txtGenero.setText(object.getString("name"));
                            }

                            btnDetalhes.setVisibility(View.VISIBLE);
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

    public static ArtistaFragment newInstance() {
        return new ArtistaFragment();
    }

    public ArrayList<String> getMusicas(JSONArray array){
        try {

            ArrayList<String> returnList = new ArrayList<String>();

            for(int j = 0; j < array.length(); j++){

                JSONObject object = array.getJSONObject(j);
                returnList.add(object.getString("desc"));
            }
            return returnList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getAlbum(JSONArray array){
        try {

            ArrayList<String> returnList = new ArrayList<String>();

            for(int j = 0; j < array.length(); j++){

                JSONObject object = array.getJSONObject(j);
                returnList.add(object.getString("desc"));
            }
            return returnList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), DetalhesArtistaActivity.class);
        String require = txtPesquisa.getText().toString();

        require = removerAcentos(require);

        if(txtPesquisa.getText().toString().equals("")){
            Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
        }else{
            try {
                JSONObject retorno = new ArtistService(require).execute().get();
                String nome = retorno.getString("desc");
                String foto = "https://s2.vagalume.com/"+retorno.getString("pic_medium");

                JSONObject rank = retorno.getJSONObject("rank");
                String views = rank.getString("views");
                String position = rank.getString("pos");

                JSONObject topMus = retorno.getJSONObject("lyrics");
                JSONArray itemMus = topMus.getJSONArray("item");
                getMusicas(itemMus);
                intent.putStringArrayListExtra("lista", getMusicas(itemMus));

                JSONObject album = retorno.getJSONObject("albums");
                JSONArray itemAlbum = album.getJSONArray("item");
                getAlbum(itemAlbum);
                intent.putStringArrayListExtra("listaAlb", getAlbum(itemAlbum));

                intent.putExtra("nome", nome);
                intent.putExtra("foto", foto);
                intent.putExtra("views", views);
                intent.putExtra("pos", position);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            startActivity(intent);
        }


    }

    public String removerAcentos(String texto) {
        String comAcentos = "ÄÅÁÂÀÃäáâàãÉÊËÈéêëèÍÎÏÌíîïìÖÓÔÒÕöóôòõÜÚÛüúûùÇç ";
        String semAcentos = "AAAAAAaaaaaEEEEeeeeIIIIiiiiOOOOOoooooUUUuuuuCc-";

        for (int i = 0; i < comAcentos.length(); i++) {
            texto = texto.replace(comAcentos.charAt(i), semAcentos.charAt(i));
        }
        return texto;

    }
}
