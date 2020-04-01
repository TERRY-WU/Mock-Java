package com.api.server;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class TestFileUtil {
    @Test
    public void test() throws IOException {
        System.out.println(System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "/src/main/jsonFiles/hey.json";
        FileUtils.writeStringToFile(new File(path), "haha");
    }
}
