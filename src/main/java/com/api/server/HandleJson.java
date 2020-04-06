package com.api.server;

import org.apache.commons.io.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(allowCredentials = "true")
@RestController
@ApiOperation(value = "/api/v1", tags = "处理前端传来的Json数据")
@RequestMapping("/api/v1")
public class HandleJson {

    @RequestMapping(value = "/json/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "上传想要返回的Json格式接口", httpMethod = "POST")
    public String createJson(HttpServletRequest request, @RequestBody(required = false) String jsonString) throws IOException {

        String apiName = request.getHeader("API-Name");

        if (Objects.isNull(jsonString)) {
            return "Hi Boss, the request body should not be empty...!";
        }

        if (apiName.trim().isEmpty()) {
            return "The API name should not be empty...!";
        }

        if (isSpecialChar(apiName)) {
            return "The API name should not contain special characters...! ";
        }

        String projectPath = System.getProperty("user.dir");
        System.out.println("Project path: " + projectPath);
        System.out.println("Name: " + apiName);
        String filePath = projectPath + "/src/main/json/" + apiName + ".json";
        String[] fileList = new File(projectPath + "/src/main/json/").list();

        if (fileList != null) {
            for (String file : fileList) {
                String existedFileName = file.substring(0, file.indexOf("."));
                System.out.println(existedFileName);
                if (apiName.equals(existedFileName)) {
                    return "The API name '" + apiName + "' already existed...!";
                }
            }
        }

        FileUtils.writeStringToFile(new File(filePath), jsonString);
        System.out.println(jsonString);
        return jsonString;
    }

    @RequestMapping(value = "/json/get/{name}", method = RequestMethod.GET)
    @ApiOperation(value = "通过文件名获取自定义的JSON数据", httpMethod = "GET")
    public String returnJSONGet(@PathVariable(value = "name", required = false) String apiName) throws IOException {

        if(apiName == null){
            return "{Status:}";
        }
        System.out.println("传过来的文件名:" + apiName);
//        System.out.println(System.getProperties().getProperty("os.name"));
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/main/json/";
        String[] fileList = new File(filePath).list();
        if (fileList != null) {
            for (String file : fileList) {
                String jsonFileName = file.substring(0, file.indexOf("."));
                if (apiName.equals(jsonFileName)) {
                    String resultString = FileUtils.readFileToString(new File(filePath + file));
                    System.out.println("Existed file found: " + file);
                    return resultString;
                }
            }
        }
        return "No data found...!";
    }

    @RequestMapping(value = "/json/post/{name}", method = RequestMethod.POST)
    @ApiOperation(value = "通过文件名获取自定义的JSON数据", httpMethod = "POST")
    public String returnJSONPost(@PathVariable(value = "name", required = false) String apiName) throws IOException {
        return returnJSONGet(apiName);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

}