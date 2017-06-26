package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving GitHub user's name
 */
@RestController
public class NamesController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /names/{username} endpoint
     * @param username - username of GitHub user
     * @return GitHub user's name
     * @throws IOException
     */
    @RequestMapping(path = "/names/{username}", method = RequestMethod.GET)
    public String getName(@PathVariable String username) throws IOException {
        return usersService.getName(username);
    }
}
