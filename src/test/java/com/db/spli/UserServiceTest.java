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
        User user = userService.queryOneById(1196978513958141952L);
        System.out.println(user.toString());
    }
}
