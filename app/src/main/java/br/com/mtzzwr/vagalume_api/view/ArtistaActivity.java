package br.com.mtzzwr.vagalume_api.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.mtzzwr.vagalume_api.service.ArtistService;
import br.com.mtzzwr.vagalume_api.R;

public class ArtistaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtPesquisa;
    Button btnPesquisa, btnDetalhes;
    TextView txtNome, txtGenero, txtViews, lblMusica, lblGenero;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);

        txtPesquisa = findViewById(R.id.txtPesquisar);
        btnPesquisa = findViewById(R.id.btnPesquisar);
        txtNome = findViewById(R.id.txtNome);
        txtGenero = findViewById(R.id.txtGenero);
        txtViews = findViewById(R.id.txtViews);
        imageView = findViewById(R.id.img);
        lblMusica = findViewById(R.id.lblMusica);
        lblGenero = findViewById(R.id.lblGenero);
        btnDetalhes = findViewById(R.id.btnDetalhes);

        btnDetalhes.setVisibility(View.INVISIBLE);

        btnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // pega o que foi digitado no editText e guarda em uma String
                    String require = txtPesquisa.getText().toString();

                    require = removerAcentos(require);

                    // cria um jsonobject e faz a requisição na classe responsável por acessar a api
                    JSONObject retorno = new ArtistService(require).execute().get();

                    // cria uma string que contém o caminho da imagem do artista
                    String foto = "https://s2.vagalume.com/" + retorno.getString("pic_small");

                    // instância do Picasso para carregar a imagem no imageView
                    Picasso.with(ArtistaActivity.this).load(foto).into(imageView);

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

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDetalhes.setOnClickListener(this);

    }

    public ArrayList<String> getMusicas(JSONArray array) {
        try {

            ArrayList<String> returnList = new ArrayList<String>();

            for (int j = 0; j < 5; j++) {

                JSONObject object = array.getJSONObject(j);
                returnList.add(object.getString("desc"));
            }
            return returnList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getAlbum(JSONArray array) {
        try {

            ArrayList<String> returnList = new ArrayList<String>();

            for (int j = 0; j < array.length(); j++) {

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

        Intent intent = new Intent(this, DetalhesArtistaActivity.class);
        String require = txtPesquisa.getText().toString();
        try {
            JSONObject retorno = new ArtistService(require).execute().get();
            String nome = retorno.getString("desc");
            String foto = "https://s2.vagalume.com/" + retorno.getString("pic_medium");

            JSONObject rank = retorno.getJSONObject("rank");
            String views = rank.getString("views");
            String position = rank.getString("pos");

            JSONObject topMus = retorno.getJSONObject("toplyrics");
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

    public String removerAcentos(String texto) {
        String comAcentos = "ÄÅÁÂÀÃäáâàãÉÊËÈéêëèÍÎÏÌíîïìÖÓÔÒÕöóôòõÜÚÛüúûùÇç ";
        String semAcentos = "AAAAAAaaaaaEEEEeeeeIIIIiiiiOOOOOoooooUUUuuuuCc-";

        for (int i = 0; i < comAcentos.length(); i++) {
            texto = texto.replace(comAcentos.charAt(i), semAcentos.charAt(i));
        }
         return texto;
    }
}