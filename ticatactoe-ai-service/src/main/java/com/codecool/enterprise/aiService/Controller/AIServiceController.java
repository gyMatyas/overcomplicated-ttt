package com.codecool.enterprise.aiService.Controller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
public class AIServiceController {

    @RequestMapping(value = "/getAIMove", method = RequestMethod.POST)
    public String getAiMove(@RequestHeader("boardData") String boardData) {
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<>();
        try {
            URL obj = new URL("http://tttapi.herokuapp.com/api/v1/" + boardData + "/O");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            Scanner scanner = new Scanner(con.getInputStream());
            String responseBody = scanner.useDelimiter("\\A").next();
            Map<String, Object> AIresponse = gson.fromJson(responseBody, new TypeToken<Map<String, Object>>() {}.getType());
            Double recommendation = (Double) AIresponse.get("recommendation");
            response.put("recommendation", recommendation);
        } catch (IOException e) {
            response.put("recommendation", new Random().nextInt(9));

        }
        return gson.toJson(response);
    }
}
