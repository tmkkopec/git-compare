package org.tai.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

@Service
public class UsersService {

    private JSONObject readUsersJSON(String username) throws IOException {
        String mainUrl = String.format("https://api.github.com/users/%s", username);
        return readJson(mainUrl);
    }

    public String getName(String username) throws IOException {
        try {
            return readUsersJSON(username).getString("name");
        } catch (JSONException e) {
            return "null";
        }
    }

    public int getFollowers(String username) throws IOException {
        return readUsersJSON(username).getInt("followers");
    }

    public String getAvatarUrl(String username) throws IOException {
        return readUsersJSON(username).getString("avatar_url");
    }

    public String getEmail(String username) throws IOException {
        try {
            return readUsersJSON(username).getString("email");
        } catch (JSONException e) {
            return "null";
        }
    }

    public int getOwnRepositories(String username) throws IOException {
        return readUsersJSON(username).getInt("public_repos");
    }

    public int getRepositoriesContributedTo(String username) throws IOException {
        String reposContributedToUrl = String.format("https://api.github.com/users/%s/subscriptions", username);
        return getArrayLength(reposContributedToUrl);
    }

    public int getIssues(String username) throws IOException {
        String issuesUrl = String.format("https://api.github.com/search/issues?q=author:%s%%20is:issue", username);
        return readJson(issuesUrl).getInt("total_count");
    }

    public int getCommits(String username) throws IOException {
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

    public double getCommitsPerDay(String username) throws IOException {
        int commits = getCommits(username);
        String githubDate = getFirstCommitDate(username);
        String date = githubDate.substring(0, githubDate.indexOf("T"));
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate firstDate = LocalDate.parse(date, formatter);
        final LocalDate secondDate = LocalDate.now();
        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return (double) commits / days;
    }

    public int getPullRequests(String username) throws IOException {
        String issuesUrl = String.format("https://api.github.com/search/issues?q=author:%s%%20is:pr", username);
        return readJson(issuesUrl).getInt("total_count");
    }

    public JSONArray getOrganizations(String username) throws IOException, JSONException {
        String organizationsUrl = String.format("https://api.github.com/users/%s/orgs?per_page=100", username);
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

    public int getTotalStars(String username) throws JSONException, IOException {
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        int result = 0;
        for (JSONArray repoArray : getArrays(ownReposUrl)){
            for (int i = 0; i < repoArray.length(); i++){
                JSONObject repo = repoArray.getJSONObject(i);
                result += repo.getInt("stargazers_count");
            }
        }
        return result;
    }

    public JSONObject getLanguages(String username) throws IOException {
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        Map<String, Integer> counter = new HashMap<>();
        for (JSONArray repoArray : getArrays(ownReposUrl)){
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
