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

public class ArtistService extends AsyncTask<Void, Void, JSONObject> {

     private final String artista;

     public ArtistService(String artista){
         this.artista = artista;
     }

    @Override
    protected JSONObject doInBackground(Void... voids) {

        StringBuilder resposta = new StringBuilder();

        try {
            String art = this.artista.toLowerCase();
            URL url = new URL("https://www.vagalume.com.br/"+art+"/index.js");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNextLine()){
                resposta.append(scanner.nextLine());
            }

            try {
                JSONObject json = new JSONObject(String.valueOf(resposta));
                JSONObject artist = json.getJSONObject("artist");

                return artist;

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
