package com.yefeng;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonCaller {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "F:/learn-py/spider/sturdy/唯美女生.py");
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
