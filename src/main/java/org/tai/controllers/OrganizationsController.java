package org.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tai.service.UsersService;

import java.io.IOException;

@RestController
public class OrganizationsController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/organizations/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrganizations(@PathVariable String username) throws IOException {
        return usersService.getOrganizations(username).toString(4);
    }
}
