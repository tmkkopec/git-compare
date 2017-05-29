package org.tai.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.tai.jsonutils.JSONCalc.getArrayLength;
import static org.tai.jsonutils.JSONReader.readJson;
import static org.tai.jsonutils.JSONReader.readJsonArray;

@RestController
public class UsersController {

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserInfo(@PathVariable String username) throws IOException {
        String mainUrl = String.format("https://api.github.com/users/%s", username);
        String reposContributedToUrl = String.format("https://api.github.com/users/%s/subscriptions", username);
        JSONObject mainJson = readJson(mainUrl);

        JSONObject result = new JSONObject();
        result.put("login", mainJson.getString("login"))
                .put("avatar_url", mainJson.getString("avatar_url"))
                .put("name", mainJson.get("name"))
                .put("email", mainJson.get("email"))
                .put("followers", mainJson.getInt("followers"))
                .put("organizations", getOrganizations(username))
                .put("own_repositories", mainJson.getInt("public_repos"))
                .put("repositories_conitrubed_to", getArrayLength(reposContributedToUrl));
        return result.toString(4);
    }

    private JSONArray getOrganizations(String user) throws IOException, JSONException {
        String organizationsUrl = String.format("https://api.github.com/users/%s/orgs?per_page=100", user);
        JSONArray orgsArray = readJsonArray(organizationsUrl);
        JSONArray result = new JSONArray();
        for (int i = 0; i < orgsArray.length(); i++){
            JSONObject organization = orgsArray.getJSONObject(i);
            JSONObject filteredOrg = new JSONObject()
                    .put("name", organization.getString("login"))
                    .put("avatar_url", organization.getString("avatar_url"))
                    .put("url", "https://github.com/" + organization.getString("login"));
            result.put(filteredOrg);
        }
        return result;
    }
}


