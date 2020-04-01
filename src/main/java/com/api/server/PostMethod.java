package com.api.server;

import com.api.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@Api(value = "/api/v1", tags = "My POST Request")
@RequestMapping("/api/v1")
public class PostMethod {

    //这个变量是用来装我们cookies信息的
    private static Cookie cookie;

    //用户登陆成功获取到cookies，然后再访问其他接口获取到列表
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登陆接口，成功后获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "password", required = true) String password) {
        if (userName.equals("zhangsan") && password.equals("123456")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜你登陆成功了!";
        }
        return "用户名或者是密码错误！";
    }

    @RequestMapping(value = "/post/with/cookies", method = RequestMethod.POST)
    @ApiOperation(value = "要求客户端携带cookies访问", httpMethod = "POST")
    public String getWithCookies(HttpServletRequest request) {
        return GetMethod.determineCookies(request);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "登陆成功后获取用户信息", httpMethod = "POST")
    public String getUserInfo(HttpServletRequest request, @RequestBody User u) {
        User user;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && u.getUserName().equals("admin")
                    && u.getPassword().equals("123456")) {
                 user = new User();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("男");
                return user.toString();
            }
        }
        return "参数不合法...!";
    }

}