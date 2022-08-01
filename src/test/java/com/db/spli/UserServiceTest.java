package com.db.spli;

import com.db.spli.domain.User;
import com.db.spli.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testQueryOne() {
        User user1 = userService.queryOneById(1);
        System.out.println(user1.toString());
        User user = new User();
        user.setUserName("xiaolv");
        user.setAddress("shanghai");
        userService.insertOne(user);
    }

    @Test
    public void testInsertOne() {
        User user = new User();
        user.setUserName("xiaolv");
        user.setAddress("shanghai");
        userService.insertOne(user);
    }
}
