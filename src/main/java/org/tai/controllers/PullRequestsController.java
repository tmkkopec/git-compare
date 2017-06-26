package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving number of pull requests created by user
 */
@RestController
public class PullRequestsController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /pull_requests/{username} endpoint
     * @param username - username of GitHub user
     * @return number of pull requests created by user
     * @throws IOException
     */
    @RequestMapping(path = "/pull_requests/{username}", method = RequestMethod.GET)
    public int getPullRequests(@PathVariable String username) throws IOException {
        return usersService.getPullRequests(username);
    }
}
