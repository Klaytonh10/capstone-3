package org.yearup.controllers;

import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {

    @GetMapping("")
    public Profile getProfile() {

        return null;
    }

    @PutMapping("")
    public void updateProfile() {

    }

}
