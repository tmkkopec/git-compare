package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class CommitsController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/commits/{username}", method = RequestMethod.GET)
    public int getCommits(@PathVariable String username) throws IOException {
        return usersService.getCommits(username);
    }
}
