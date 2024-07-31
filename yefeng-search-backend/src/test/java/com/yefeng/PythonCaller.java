package com.yefeng;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

@Slf4j
public class PythonCaller {
    public static void main(String[] args) throws IOException, InterruptedException {
// 调用 Process 类执行 Maven 打包命令
        // 当前idea打开的窗口
        String projectPath = System.getProperty("user.dir");
        String projectDir = projectPath + "/src/main/java/com/yefeng/script";
        log.info("command Path: {}", projectDir);
        String pythonCommand = "python 4k.py --page " + 10;
        log.info("python command: {}", pythonCommand);
        // 空格拆分
        ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand.split(" "));
        processBuilder.directory(new File(projectDir));
        Map<String, String> environment = processBuilder.environment();
        System.out.println(environment);

        Process process = processBuilder.start();

        // 读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码" + exitCode);
    }
}
