package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class OwnRepositoriesController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/own_repositories/{username}", method = RequestMethod.GET)
    public int getOwnRepositories(@PathVariable String username) throws IOException {
        return usersService.getOwnRepositories(username);
    }
}
