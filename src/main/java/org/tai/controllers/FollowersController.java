package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;


/**
 * REST controller class for retrieving number of followers
 */
@RestController
public class FollowersController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET methods for /followers/{username} endpoint
     * @param username - username of GitHub user
     * @return number of user's followers
     * @throws IOException
     */
    @RequestMapping(path = "/followers/{username}", method = RequestMethod.GET)
    public int getFollowers(@PathVariable String username) throws IOException {
        return usersService.getFollowers(username);
    }
}
