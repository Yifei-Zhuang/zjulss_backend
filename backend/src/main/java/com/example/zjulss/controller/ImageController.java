package com.example.zjulss.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RestController
@RequestMapping("/image")
public class ImageController {
    private static final String UPLOAD_DIR = "/home/zjulss2/imgs/"; // 设置上传文件的存储目录
//    private static final String UPLOAD_DIR = "/Users/zhuangyifei/Downloads"; // 设置上传文件的存储目录

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(HttpServletRequest request) {
        try {
            // 获取上传的文件流
            InputStream inputStream = request.getInputStream();

            // 生成文件名：${hashval}_${timestamp}
            String fileName = generateFileName();

            // 构建目标文件路径
            String targetFilePath = UPLOAD_DIR + File.separator + fileName;

            // 保存文件到目标路径
            saveFile(inputStream, targetFilePath);

            // 返回成功响应
            return ResponseEntity.status(HttpStatus.CREATED).body(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    private String generateFileName() {;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String hashval = RandomStringUtils.randomAlphanumeric(8);
        return hashval + "_" + timestamp;
    }

    private void saveFile(InputStream inputStream, String targetFilePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(targetFilePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
    }
    @GetMapping("/get")
    public ResponseEntity<FileSystemResource> getImage(@RequestParam(value = "fileName") String filename) {
        String imagePath = UPLOAD_DIR + filename; // 图片文件路径

        // 创建一个 FileSystemResource 对象
        FileSystemResource fileResource = new FileSystemResource(imagePath);
        System.out.println(imagePath);
        // 检查文件是否存在
        if (fileResource.exists()) {
            // 使用 ResponseEntity 返回图片文件
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType("image/webp")) // 设置响应的 Content-Type 为图片类型
                    .body(fileResource);
        } else {
            // 文件不存在，返回错误响应
            return ResponseEntity.notFound().build();
        }
    }
}
