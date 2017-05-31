package org.tai.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tai.service.UsersService;

import java.io.IOException;
import java.util.List;

import static org.tai.jsonutils.JSONCalc.getArrays;
import static org.tai.jsonutils.JSONReader.readJson;

@RestController
public class UsersController {

    private UsersService usersService;

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserInfo(@PathVariable String username) throws IOException {
        String mainUrl = String.format("https://api.github.com/users/%s", username);
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        JSONObject mainJson = readJson(mainUrl);

        int commits = usersService.getCommits(username);
        List<JSONArray> repoArrays = getArrays(ownReposUrl);
        JSONObject result = new JSONObject();
        result.put("avatar_url", mainJson.getString("avatar_url"))
                .put("name", mainJson.get("name"))
                .put("email", mainJson.get("email"))
                .put("followers", mainJson.getInt("followers"))
                .put("own_repositories", mainJson.getInt("public_repos"))
                .put("issues", usersService.getIssues(username))
                .put("commits", commits)
                .put("commits_per_day",usersService.getCommitsPerDay(commits, usersService.getFirstCommitDate(username)))
                .put("pull_requests", usersService.getPullRequests(username))
                .put("repositories_conitrubed_to", usersService.getRepositoriesContributedTo(username))
                .put("organizations", usersService.getOrganizations(username))
                .put("total_stars", usersService.getTotalStars(repoArrays))
                .put("repositories_written_in", usersService.getLanguages(repoArrays));
        return result.toString(4);
    }

}


