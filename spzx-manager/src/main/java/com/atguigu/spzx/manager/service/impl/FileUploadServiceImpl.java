package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: fileUploadServiceImpl
 * @author: XiaoHB
 * @date: 2024/1/5 18:57
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;
    /**
     * 1.创建minioClient对象
     * 2.上传文件
     * 3.获取文件地址并返回
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            //创建minioClient对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl())
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket "+minioProperties.getBucketName()+" already exists.");
            }
            //获取上传的文件名称
            //生成日期数据dateDir
            //生成随机uuid
            //上传文件更名为:dateDir/uuid filename
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String filename = dateDir+"/"+uuid+file.getOriginalFilename();
            //文件上传
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(filename)
                            .stream(file.getInputStream(),file.getSize(),-1)    //获取输入流，文件大小
                            .build());
            //获取minio上传文件
            String url = minioProperties.getEndpointUrl()+"/"+minioProperties.getBucketName()+"/"+filename;
            return url;
        } catch (Exception e) {
           e.printStackTrace();
           throw new GuiguException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
}
