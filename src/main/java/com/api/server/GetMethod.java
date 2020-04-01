package com.api.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/api/v1",tags = "My Get Request")
@RequestMapping("/api/v1")
public class GetMethod {

    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到Cookies", httpMethod = "GET")
    public String getCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("login", "true");
        Cookie cookie1 = new Cookie("bt", "age");
        Cookie cookie2 = new Cookie("hello", "aloha");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "Congratulations~! You've got the cookie info.......!";
    }

    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies访问", httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request) {
        String resString = determineCookies(request);
        return resString;
    }

    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value = "需求携带参数才能访问的get请求方法一", httpMethod = "GET")
    public Map<String, Integer> getList1(@RequestParam Integer start, @RequestParam Integer end) {
        Map<String, Integer> mapList = new HashMap<>();
        mapList.put("高级皮鞋", 599);
        mapList.put("脆脆面", 1);
        mapList.put("羽绒服", 299);
        return mapList;
    }

    @RequestMapping(value = "/get/with/param/{start}/{end}", method = RequestMethod.GET)
    @ApiOperation(value = "需求携带参数才能访问的get请求方法二", httpMethod = "GET")
    public Map<String, Integer> getList2(@PathVariable Integer start, @PathVariable Integer end) {
        Map<String, Integer> mapList = new HashMap<>();
        mapList.put("炒米粉", 6);
        mapList.put("烧生蚝", 18);
        mapList.put("珠江纯生", 9);
        return mapList;
    }

    public static String determineCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            System.out.println(cookie.getName()+"---"+cookie.getValue());
        }
        if (Objects.isNull(cookies)) {
            return "Error: cookies null...You must take cookies to access this page...!";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return "Congratulations...you access this page with cookies";
            }
        }
        return "Sorry, you access this page without cookies...!";
    }

}
