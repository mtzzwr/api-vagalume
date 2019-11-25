package br.com.mtzzwr.vagalume_api.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.mtzzwr.vagalume_api.R;
import br.com.mtzzwr.vagalume_api.service.LyricsService;

public class DetalhesArtistaActivity extends AppCompatActivity {

    TextView txtNome, txtView;
    ImageView fotoArtista;
    ListView listaMusica, listaAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_artista);

        txtNome = findViewById(R.id.txtNomeDetalhes);
        fotoArtista = findViewById(R.id.imgArtista);
        txtView = findViewById(R.id.txtView);
        listaMusica = findViewById(R.id.listaMus);
        listaAlbum = findViewById(R.id.listaAlbum);

        Intent intent = getIntent();
        final String nome = intent.getStringExtra("nome");
        String foto = intent.getStringExtra("foto");
        final String views = intent.getStringExtra("views");
        ArrayList<String> listaMus = intent.getStringArrayListExtra("lista");
        txtNome.setText(nome);
        txtView.setText(views);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listaMus
        );

        listaMusica.setAdapter(adapter);

        listaMusica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String musica = (String) parent.getItemAtPosition(position);

                JSONObject retorno = null;
                try {
                    retorno = new LyricsService(nome, musica).execute().get();
                    JSONArray array = retorno.getJSONArray("mus");

                    for(int j = 0; j < array.length(); j++){

                        JSONObject object = array.getJSONObject(j);

                        String letra = object.getString("text");

                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(DetalhesArtistaActivity.this);
                        builder.setTitle(musica);
                        builder.setMessage(letra);
                        builder.show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        ArrayList<String> listaAlb = intent.getStringArrayListExtra("listaAlb");
        ArrayAdapter<String> adapterAlb = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listaAlb
        );

        listaAlbum.setAdapter(adapterAlb);

        Picasso.with(DetalhesArtistaActivity.this).load(foto).into(fotoArtista);

    }
}
