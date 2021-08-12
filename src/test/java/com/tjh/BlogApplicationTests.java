package com.tjh;

import com.tjh.entity.Blog;
import com.tjh.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogService blogService;


    @Test
    void contextLoads() {
        Blog blog = blogService.getBlog(70L);
        System.out.println(blog);
        System.out.println(blog.getFirstPicture());

    }

}
