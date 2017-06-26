package org.tai.controllers;

import javafx.util.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static org.tai.jsonutils.JSONReader.readJson;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserInfo(@PathVariable String username) throws InterruptedException, ExecutionException {

        String mainUrl = String.format("https://api.github.com/users/%s", username);
        JSONObject result = new JSONObject();

        final ExecutorService pool = Executors.newFixedThreadPool(9);
        final ExecutorCompletionService<Pair<String, Object>> completionService = new ExecutorCompletionService<>(pool);
        List<Future<Pair<String, Object>>> futures = new LinkedList<>();
        futures.add(completionService.submit(() -> new Pair<>("main_json", readJson(mainUrl))));
        futures.add(completionService.submit(() -> new Pair<>("commits", usersService.getCommits(username))));
        futures.add(completionService.submit(() -> new Pair<>("commits_per_day",
                usersService.getCommitsPerDay(username))));
        futures.add(completionService.submit(() -> new Pair<>("issues", usersService.getIssues(username))));
        futures.add(completionService.submit(() -> new Pair<>("pull_requests",
                usersService.getPullRequests(username))));
        futures.add(completionService.submit(() -> new Pair<>("repositories_contributed_to",
                usersService.getRepositoriesContributedTo(username))));
        futures.add(completionService.submit(() -> new Pair<>("organizations",
                usersService.getOrganizations(username))));
        futures.add(completionService.submit(() -> new Pair<>("total_stars", usersService.getTotalStars(username))));
        futures.add(completionService.submit(() -> new Pair<>("repositories_written_in",
                usersService.getLanguages(username))));

        for (int i = 0; i < futures.size(); i++) {
            Pair<String, Object> futureResult = completionService.take().get();
            String key = futureResult.getKey();
            if (key.equals("main_json")) {
                JSONObject mainJson = (JSONObject) futureResult.getValue();
                result.put("avatar_url", mainJson.getString("avatar_url"))
                        .put("name", mainJson.get("name"))
                        .put("email", mainJson.get("email"))
                        .put("followers", mainJson.getInt("followers"))
                        .put("own_repositories", mainJson.getInt("public_repos"))
                        .put("login", mainJson.getString("login"))
                        .put("created_at", mainJson.getString("created_at"))
                        .put("location", mainJson.get("location"))
                        .put("html_url", mainJson.getString("html_url"));
            } else {
                result.put(key, futureResult.getValue());
            }
        }

        return result.toString(4);
    }

}


