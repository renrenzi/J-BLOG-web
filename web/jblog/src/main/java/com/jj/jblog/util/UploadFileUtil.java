package com.jj.jblog.util;

import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.constant.UploadConstants;
import org.apache.commons.lang3 .StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 上传文件工具类
 * @author 张俊杰
 * @date 2021/11/11  - {TIME}
 */
public class UploadFileUtil {

    /**
     * 获取上传文件URL
     * @param file
     * @return
     */
    public static String getUploadFileUrl(MultipartFile file){
        String[] result = FastDfsUtil.fileUpload(UploadFileUtil.getBytes(file),
                UploadFileUtil.getSuffixName(file));
        // 文件组名
        String fileGroupName = result[0];
        // 文件路径
        String fileRemoteFilepath = result[1];
        // URL --->  ip:端口号 + 文件组名 + 文件路径
        String url = org.apache.commons.lang3.StringUtils.join(
                UploadConstants.UPLOAD_IMG_IP,
                fileGroupName,
                StringConstants.URL_UNDERSCORE,
                fileRemoteFilepath);
        return url;
    }
    /**
     * 获取文件二进制数组 (FastDfs 文件上传需要)
     * @param file
     * @return
     */
    public static byte[] getBytes(MultipartFile file){
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 获取文件后缀名
     * @param file 
     * @return java.lang.String
     */
    public static String getSuffixName(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)){
            throw new RuntimeException("获取图片后缀名失败");
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 生成文件名称通用方法
     * @param suffixName
     * @return java.lang.String
     */
    public static String getNewFileName(String suffixName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
        int random = new Random().nextInt(100);
        return new StringBuilder().append(sdf.format(new Date()))
                                  .append(random)
                                  .append(suffixName)
                                  .toString();
    }

}
