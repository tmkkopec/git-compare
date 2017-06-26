package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving number of issues created by user
 */
@RestController
public class IssuesController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /issues/{username} endpoint
     * @param username - username of GitHub user
     * @return number of issues created by user
     * @throws IOException
     */
    @RequestMapping(path = "/issues/{username}", method = RequestMethod.GET)
    public int getIssues(@PathVariable String username) throws IOException {
        return usersService.getIssues(username);
    }

}
