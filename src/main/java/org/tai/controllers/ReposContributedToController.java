package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class ReposContributedToController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/repositories_contributed_to/{username}", method = RequestMethod.GET)
    public int getReposContributedTo(@PathVariable String username) throws IOException {
        return usersService.getRepositoriesContributedTo(username);
    }
}
