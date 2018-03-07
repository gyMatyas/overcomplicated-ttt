package com.codecool.enterprise.funfact.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
public class FunFactController {

    @GetMapping(value = "/getChuckNorrisJoke")
    public String getChuckNorrisFact() throws IOException {
        URL obj = new URL("https://api.chucknorris.io/jokes/random");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        try (Scanner scanner = new Scanner(con.getInputStream())) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            try {
                Map<String, String> out = gson.fromJson(responseBody, type);
                return out.get("value");
            } catch (IllegalStateException e) {
                return "Chuck Norris crashed the fun fact server! He is totally able to this!";
            }
        }
    }
}
