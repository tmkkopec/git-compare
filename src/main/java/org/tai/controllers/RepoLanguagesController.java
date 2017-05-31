package org.tai.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

import static org.tai.jsonutils.JSONCalc.getArrays;

@RestController
public class RepoLanguagesController {

    private UsersService usersService;

    @RequestMapping(path = "/repositories_written_in/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRepoLanguages(@PathVariable String username) throws IOException {
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        return usersService.getLanguages(getArrays(ownReposUrl)).toString(4);
    }
}
