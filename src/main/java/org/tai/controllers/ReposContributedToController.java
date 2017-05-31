package org.tai.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class ReposContributedToController {

    private UsersService usersService;

    @RequestMapping(path = "/repositories_conitrubed_to/{username}", method = RequestMethod.GET)
    public int getReposContribuedTo(@PathVariable String username) throws IOException {
        return usersService.getRepositoriesContributedTo(username);
    }
}