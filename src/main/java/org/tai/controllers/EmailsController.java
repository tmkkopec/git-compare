package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving user's email
 */
@RestController
public class EmailsController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /emails/{username} endpoint
     * @param username - username of GitHub user
     * @return user's email
     * @throws IOException
     */
    @RequestMapping(path = "/emails/{username}", method = RequestMethod.GET)
    public String getEmail(@PathVariable String username) throws IOException {
        return usersService.getEmail(username);
    }
}
