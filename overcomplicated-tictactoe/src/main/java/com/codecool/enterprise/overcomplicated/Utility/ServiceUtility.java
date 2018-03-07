package com.codecool.enterprise.overcomplicated.Utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
public class ServiceUtility {

    public void getChuckNorrisJoke(Model model) {
        try {
            URL obj = new URL("http://localhost:60000/getChuckNorrisJoke");
            Map<String, String> response = getResponse(obj);
            model.addAttribute("funfact", "&quot;" + response.get("quote") + "&quot;");
        } catch (IOException e) {
            model.addAttribute("funfact", "&quot;Chuck Norris once broke the fun fact server!&quot;");
        }
    }

    public void getComic(Model model) {
        try {
            URL obj = new URL("http://localhost:60001/getComic");
            Map<String, String> response = getResponse(obj);
            model.addAttribute("comic_uri",  response.get("img"));
            model.addAttribute("comic_title", response.get("title"));
        } catch (IOException e) {
            model.addAttribute("comic_uri", "https://imgs.xkcd.com/comics/bad_code.png");
            model.addAttribute("comic_title", "Bad code");
        }
    }

    private Map<String, String> getResponse(URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        Scanner scanner = new Scanner(con.getInputStream());
        String responseBody = scanner.useDelimiter("\\A").next();
        Gson gson = new Gson();
        return gson.fromJson(responseBody, new TypeToken<Map<String, Object>>() {}.getType());
    }
}
