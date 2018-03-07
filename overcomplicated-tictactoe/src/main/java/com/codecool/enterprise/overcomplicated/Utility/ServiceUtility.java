package com.codecool.enterprise.overcomplicated.Utility;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
public class ServiceUtility {

    public void getChuckNorrisJoke(Model model) {
        try {
            URL obj = new URL("http://localhost:60000/getChuckNorrisJoke");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            Scanner scanner = new Scanner(con.getInputStream());
            model.addAttribute("funfact", "&quot;" + scanner.nextLine() + "&quot;");
        } catch (IOException e) {
            model.addAttribute("funfact", "&quot;Chuck Norris once broke the fun fact server!&quot;");
        }
    }
}
