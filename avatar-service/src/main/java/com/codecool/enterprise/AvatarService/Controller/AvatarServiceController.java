package com.codecool.enterprise.AvatarService.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AvatarServiceController {

    @GetMapping(value = "/getAvatar")
    public String getAvatar() {
        Gson gson = new Gson();
        Map<String, String> response = new HashMap<>();
        response.put("avatar_uri", "https://robohash.org/" + RandomStringUtils.randomAlphanumeric(20).toUpperCase());
        return gson.toJson(response, new TypeToken<Map<String, Object>>() {}.getType());
    }
}
