package br.com.mtzzwr.vagalume_api.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import br.com.mtzzwr.vagalume_api.service.LyricsService;
import br.com.mtzzwr.vagalume_api.R;

public class LyricsActivity extends AppCompatActivity {

    TextView txtResultado, txtMusica, txtArtista;
    EditText edtArtista, edtMusica;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        txtResultado = findViewById(R.id.txtResultado);
        edtArtista = findViewById(R.id.edtArtista);
        edtMusica = findViewById(R.id.edtMusica);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtMusica = findViewById(R.id.txtNomeMusica);
        txtArtista = findViewById(R.id.txtNomeArtista);


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String require_art = edtArtista.getText().toString();
                String require_mus = edtMusica.getText().toString();

                try {
                    JSONObject retorno = new LyricsService(require_art, require_mus).execute().get();
                    JSONArray array = retorno.getJSONArray("mus");

                    for(int j = 0; j < array.length(); j++){

                        // jsonObject que recebe o indice do array
                        JSONObject object = array.getJSONObject(j);

                        // coloca os dados do array no textView
                        txtMusica.setText(object.getString("name"));
                        txtResultado.setText(object.getString("text"));
                    }

                    txtArtista.setText(retorno.getString("name"));

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
