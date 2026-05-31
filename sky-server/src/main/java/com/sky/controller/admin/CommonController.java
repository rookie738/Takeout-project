package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String id = UUID.randomUUID().toString();
        StringBuffer url = new StringBuffer(id);
        url.append(extensionName);
        try {
            aliOssUtil.upload(file.getBytes(),url.toString());
            return Result.success(url.toString());
        } catch (IOException e) {
            log.info("文件上传失败");
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }
}
