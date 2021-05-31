package com.offcn.test;

import com.offcn.mapper.UserMapper;
import com.offcn.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestUserMapper {
    @Resource
    private UserMapper userMapper;

    @Test
    void testSelectById(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
