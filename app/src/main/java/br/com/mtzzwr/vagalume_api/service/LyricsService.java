package br.com.mtzzwr.vagalume_api.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.mtzzwr.vagalume_api.Artista;

public class LyricsService extends AsyncTask<Void, Void, JSONObject> {

    private final String artista;
    private final String musica;

    public LyricsService(String artista, String musica){

        this.artista = artista;
        this.musica = musica;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {

        StringBuilder resposta = new StringBuilder();

        try {
            String art = this.artista.toLowerCase();
            String musica = this.musica.toLowerCase();
            URL url = new URL("http://api.vagalume.com.br/search.php?apikey=3ea6d2904bc7d2d5789ccba7a58c3828&art="+art+"&mus="+musica);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNextLine()){
                resposta.append(scanner.nextLine());
            }

            Gson gson = new Gson();
            gson.fromJson(resposta.toString(), Artista.class);

            try {
                JSONObject json = new JSONObject(String.valueOf(resposta));

                return json;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
