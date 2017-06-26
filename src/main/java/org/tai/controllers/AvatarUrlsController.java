package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

/**
 * REST controller class for retrieving user's avatar url
 */
@RestController
public class AvatarUrlsController {

    @Autowired
    private UsersService usersService;

    /**
     * Method that handles GET requests for /avatar_urls/{username} endpoint
     * @param username - username of GitHub user
     * @return url pointing to user's avatar image
     * @throws IOException
     */
    @RequestMapping(path = "/avatar_urls/{username}", method = RequestMethod.GET)
    public String getAvatarURL(@PathVariable String username) throws IOException {
        return usersService.getAvatarUrl(username);
    }
}
