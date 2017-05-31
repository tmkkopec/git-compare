package org.tai.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class CommitsPerDayController {

    private UsersService usersService;

    @RequestMapping(path = "/commits_per_day/{username}", method = RequestMethod.GET)
    public double getCommitsPerDay(@PathVariable String username) throws IOException {
        return usersService.getCommitsPerDay(username);
    }
}
