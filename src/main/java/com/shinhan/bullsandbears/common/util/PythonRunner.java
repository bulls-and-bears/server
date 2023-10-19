package com.shinhan.bullsandbears.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonRunner {

    public void run() {
        try {
            // Python 스크립트 실행
            Process process = Runtime.getRuntime().exec("python your_script.py");

            // 스크립트 실행 결과 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Python 스크립트 종료 코드: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
