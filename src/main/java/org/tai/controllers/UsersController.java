package org.tai.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.tai.jsonutils.JSONCalc.getArrayLength;
import static org.tai.jsonutils.JSONCalc.getArrays;
import static org.tai.jsonutils.JSONReader.readJson;
import static org.tai.jsonutils.JSONReader.readJsonArray;

@RestController
public class UsersController {

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserInfo(@PathVariable String username) throws IOException {
        String mainUrl = String.format("https://api.github.com/users/%s", username);
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        String reposContributedToUrl = String.format("https://api.github.com/users/%s/subscriptions", username);
        JSONObject mainJson = readJson(mainUrl);

        int commits = getCommits(username);
        JSONObject result = new JSONObject();
        result.put("login", mainJson.getString("login"))
                .put("avatar_url", mainJson.getString("avatar_url"))
                .put("name", mainJson.get("name"))
                .put("email", mainJson.get("email"))
                .put("followers", mainJson.getInt("followers"))
                .put("own_repositories", mainJson.getInt("public_repos"))
                .put("issues", getIssues(username))
                .put("commits", commits)
                .put("commits_per_day", getCommitsPerDay(commits, getFirstCommitDate(username)))
                .put("pull_requests", getPullRequests(username))
                .put("repositories_conitrubed_to", getArrayLength(reposContributedToUrl))
                .put("organizations", getOrganizations(username))
                .put("total_stars", getTotalStars(ownReposUrl))
                .put("repositories_written_in", getLanguages(ownReposUrl));
        return result.toString(4);
    }

    private int getIssues(String username) throws IOException {
        String issuesUrl = String.format("https://api.github.com/search/issues?q=author:%s%%20is:issue", username);
        return readJson(issuesUrl).getInt("total_count");
    }

    private int getCommits(String username) throws IOException {
        String commitsUrl = String.format("https://api.github.com/search/commits?q=author:%s", username);
        return readJson(commitsUrl, "application/vnd.github.cloak-preview").getInt("total_count");
    }

    private String getFirstCommitDate(String username) throws IOException {
        String firstCommitsUrl = String.format("https://api.github.com/search/commits?q=author:%s%%20sort:author-date-asc", username);
        return readJson(firstCommitsUrl, "application/vnd.github.cloak-preview")
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("commit")
                .getJSONObject("author")
                .getString("date");
    }

    private double getCommitsPerDay(int commits, String githubDate){
        String date = githubDate.substring(0, githubDate.indexOf("T"));
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate firstDate = LocalDate.parse(date, formatter);
        final LocalDate secondDate = LocalDate.now();
        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return (double) commits / days;
    }

    private int getPullRequests(String username) throws IOException {
        String issuesUrl = String.format("https://api.github.com/search/issues?q=author:%s%%20is:pr", username);
        return readJson(issuesUrl).getInt("total_count");
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

    private int getTotalStars(String reposUrl) throws JSONException, IOException {
        int result = 0;
        for (JSONArray repoArray : getArrays(reposUrl)){
            for (int i = 0; i < repoArray.length(); i++){
                JSONObject repo = repoArray.getJSONObject(i);
                result += repo.getInt("stargazers_count");
            }
        }
        return result;
    }

    private JSONObject getLanguages(String reposUrl) throws IOException {
        Map<String, Integer> counter = new HashMap<>();
        for (JSONArray repoArray : getArrays(reposUrl)){
            for (int i = 0; i < repoArray.length(); i++){
                JSONObject repo = repoArray.getJSONObject(i);
                String key;
                try {
                     key = repo.getString("language");
                } catch (JSONException e){
                    key = "null";
                }
                counter.put(key, counter.getOrDefault(key, 0) + 1);
            }
        }
        JSONObject result = new JSONObject();
        for (Map.Entry entry : counter.entrySet()) {
            String key = (String) entry.getKey();
            if (key.equals("null")) continue;
            result.put(key, entry.getValue());
        }
        return result;
    }
}


