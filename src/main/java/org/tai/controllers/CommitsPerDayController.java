package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving number of commits per day
 */
@RestController
public class CommitsPerDayController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /commits_per_day/{username} endpoint
     * @param username - username of GitHub user
     * @return user's ratio of commits per day
     * @throws IOException
     */
    @RequestMapping(path = "/commits_per_day/{username}", method = RequestMethod.GET)
    public double getCommitsPerDay(@PathVariable String username) throws IOException {
        return usersService.getCommitsPerDay(username);
    }
}
