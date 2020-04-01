package com.api.server;

import org.apache.commons.io.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@ApiOperation(value = "/api/v1", tags = "处理前端传来的Json数据")
@RequestMapping("/api/v1")
public class HandleJson {

    @RequestMapping(value = "/json/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "返回想要的Json格式", httpMethod = "POST")
    public String createJson(HttpServletRequest request, @RequestBody String jsonString) throws IOException {

        Cookie[] cookies = request.getCookies();
        String fileName = cookies[0].getValue();
        String projectPath = new File("WebAPI-5").getAbsolutePath();
        System.out.println(projectPath);
        String filePath = projectPath + "/src/main/JSONFiles/" + fileName + ".json";

        if (Objects.isNull(fileName) || fileName.equals("true") || fileName.equals(""))
            return "Please provide file name...! Format: name=test001";

        if(fileName.contains("/"))
            return "The file name should not contain '/'...! ";

        String[] fileList = new File(projectPath + "/src/main/JSONFiles/").list();
        for (String file : fileList) {
            String existedFileName = file.substring(0, file.indexOf("."));
            System.out.println(existedFileName);
            if (fileName.equals(existedFileName)) {
                return "The file name '" + fileName + "' already existed...!";
            }
        }
        FileUtils.writeStringToFile(new File(filePath), jsonString);
        System.out.println(jsonString);
        return jsonString;
    }

    @RequestMapping(value = "/json/data/get/{name}", method = RequestMethod.GET)
    @ApiOperation(value = "通过文件名获取自定义的JSON数据", httpMethod = "GET")
    public String returnJSONGet(@PathVariable(value = "name", required = true) String fileName) throws IOException {
        System.out.println("传过来的文件名:" + fileName);
//        System.out.println(System.getProperties().getProperty("os.name"));
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/main/JSONFiles/";
        String[] fileList = new File(filePath).list();
        for(String file : fileList){
            String jsonFileName = file.substring(0, file.indexOf("."));
            if(fileName.equals(jsonFileName)){
                String resultString = FileUtils.readFileToString(new File(filePath + file));
                System.out.println("Existed file found: " + file);
                return resultString;
            }
        }
        return "No file found...!";
    }

    @RequestMapping(value = "/json/data/post/{name}", method = RequestMethod.POST)
    @ApiOperation(value = "通过文件名获取自定义的JSON数据", httpMethod = "POST")
    public String returnJSONPost(@PathVariable(value = "name", required = true) String fileName) throws IOException {
        return returnJSONGet(fileName);
    }

}