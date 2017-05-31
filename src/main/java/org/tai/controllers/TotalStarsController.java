package org.tai.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

import static org.tai.jsonutils.JSONCalc.getArrays;

@RestController
public class TotalStarsController {

    private UsersService usersService;

    @RequestMapping(path = "/total_stars/{username}", method = RequestMethod.GET)
    public int getTotalStars(@PathVariable String username) throws IOException {
        String ownReposUrl = String.format("https://api.github.com/users/%s/repos?per_page=100", username);
        return usersService.getTotalStars(getArrays(ownReposUrl));
    }
}
