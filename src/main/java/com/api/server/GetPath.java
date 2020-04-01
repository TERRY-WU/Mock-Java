package com.api.server;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class GetPath {

    @Test
    public void path() throws IOException {

        String path = System.getProperty("user.dir");
        System.out.println(path);

        File file = new File(path + "/src/main/JSONFiles");
        File[] fileArr = file.listFiles();
        for (File f : fileArr) {
            System.out.println(f);
        }

        File file2 = new File(path + "/src/main/JSONFiles");
        String[] filelist = file2.list();
        for(String f2 : filelist){
            System.out.println(f2);
            System.out.println(f2.substring(0, f2.lastIndexOf(".")));
        }
    }

    /*
    第一种：
File f = new File(this.getClass().getResource("/").getPath());
System.out.println(f);
结果:
C:\Documents%20and%20Settings\Administrator\workspace\projectName\bin
获取当前类的所在工程路径;
如果不加“/”
File f = new File(this.getClass().getResource("").getPath());
System.out.println(f);
结果：
C:\Documents%20and%20Settings\Administrator\workspace\projectName\bin\com\test
获取当前类的绝对路径；

第二种：
File directory = new File("");//参数为空
String courseFile = directory.getCanonicalPath() ;
System.out.println(courseFile);
结果：
C:\Documents and Settings\Administrator\workspace\projectName
获取当前类的所在工程路径;

第三种：
URL xmlpath = this.getClass().getClassLoader().getResource("selected.txt");
System.out.println(xmlpath);
结果：
file:/C:/Documents%20and%20Settings/Administrator/workspace/projectName/bin/selected.txt
获取当前工程src目录下selected.txt文件的路径

第四种：
System.out.println(System.getProperty("user.dir"));
结果：
C:\Documents and Settings\Administrator\workspace\projectName
获取当前工程路径

第五种：
System.out.println( System.getProperty("java.class.path"));
结果：
C:\Documents and Settings\Administrator\workspace\projectName\bin
获取当前工程路径
     */

}
