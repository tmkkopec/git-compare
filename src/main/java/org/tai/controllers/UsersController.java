package org.tai.controllers;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tai.jsonutils.JSONReader;

import java.io.IOException;

@RestController
public class UsersController {

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserInfo(@PathVariable String username){
        String url = "https://api.github.com/users/%s";
        try {
            JSONObject json = JSONReader.readJsonFromUrl(String.format(url, username));
            return json.toString(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject().put("error", true).toString(4);
    }
}


