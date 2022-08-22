package com.db.spli.controller;

import com.db.spli.domain.User;
import com.db.spli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lvxuan
 * @description user Controller
 */
@RestController
@RequestMapping("/controller-user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insertOne")
    public ResponseEntity insertOne(@RequestBody User user) {
        userService.insertOne(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/queryOneById")
    public ResponseEntity queryOneById(@RequestParam("id") Integer id) {
        User user = userService.queryOneById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/putCache")
    public ResponseEntity putCache(@RequestParam("idList") List<Integer> idList) {
        userService.queryByIdList(idList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getCache")
    public ResponseEntity getCache() {
        userService.getCache();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/testTransaction")
    public ResponseEntity testTransaction(@RequestBody User user) {
        userService.testTransaction(user);
        return ResponseEntity.ok().build();
    }
}
