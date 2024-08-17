package com.yefeng;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
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

    @Test
    void testPhoneDesensitization() {
        String phone = "13723231234";
        System.out.println(DesensitizedUtil.mobilePhone(phone)); //输出：137****1234
        String add = DesensitizedUtil.desensitized("尼玛死了", DesensitizedUtil.DesensitizedType.ADDRESS);
        System.out.println(add);
    }
    @Test
    void testJWT() {
        // 设置过期时间(此处为硬编码的过期时间)
        long expirationTimeInSeconds = 3999999999L * 1000L;
        Date expirationDate = new Date(expirationTimeInSeconds);
        byte[] secret = "secretKey012345678901234567890123456789012345678901234567890123456789".getBytes();
        String token = JWT.create()
                .setPayload("sub", "nacos")
                .setPayload("exp", "3999999999")
                .setKey(secret)
                .setExpiresAt(expirationDate)
                .sign();

        JWT jwt = JWTUtil.parseToken(token);
        String[] split = token.split("\\.");
        String encode = Base64.encode(split[2]).replace("=", "");
        // 使用 String.format 重新拼接
        String result = String.join(".", split[0], split[1], encode);
        System.out.println(result);

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("userName", "zhangsan");
        payload.put("passWord", "666889");

        String key = "aabb";
        String token1 = JWTUtil.createToken(payload, key.getBytes());
        System.out.println(token1);
    }
}
