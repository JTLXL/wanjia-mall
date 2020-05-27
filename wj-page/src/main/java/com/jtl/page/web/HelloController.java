package com.jtl.page.web;

import com.jtl.page.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

/**
 * @author JT.L
 * @date 2020/4/26 18:31:29
 * @description
 */
@Controller
public class HelloController {
    @GetMapping("hello")
    public String toHello(Model model) {
        model.addAttribute("msg", "Hello, Thymeleaf!");

        User user = new User();
        user.setAge(21);
        user.setName("Jack Chen");
        user.setFriend(new User("李小龙", 30,null));
        // user.setFriend(new User("李小龙", 30,user));

        model.addAttribute("user", user);

        User user2 = new User("JTL", 17, null);
        model.addAttribute("users", Arrays.asList(user,user2));

        // 普通字符串被当成视图名称，结合前缀和后缀寻找视频
        return "hello";
    }
}
