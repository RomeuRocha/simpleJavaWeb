package com.romeu.controllers;

import com.romeu.anotations.Controller;
import com.romeu.anotations.RequestMapping;

@Controller
public class UserController {

    @RequestMapping(path = "/users", method = "GET")
    public String getUsers() {
        return "List of users!";
    }

    @RequestMapping(path = "/users", method = "POST")
    public String createUser() {
        return "User created!";
    }
}

