package com.db.spli.controller;

import com.db.spli.service.UserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller-userV2")
public class UserControllerV2 {

    @Autowired
    private UserServiceV2 userServiceV2;

    @PostMapping("/testClassTransaction")
    public ResponseEntity testClassTransaction() {
        userServiceV2.testClassTransaction();
        return ResponseEntity.ok().build();
    }
}
