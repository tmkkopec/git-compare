package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving JSON object containing languages and number of repositories written in given language
 */
@RestController
public class RepoLanguagesController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /repositories_written_in/{username} endpoint
     * @param username - username of GitHub user
     * @return String representation of JSON object containing languages and number of repositories written in given language
     * @throws IOException
     */
    @RequestMapping(path = "/repositories_written_in/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRepoLanguages(@PathVariable String username) throws IOException {
        return usersService.getLanguages(username).toString(4);
    }
}
