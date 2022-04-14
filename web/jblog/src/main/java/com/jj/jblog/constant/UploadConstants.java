package com.jj.jblog.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 图片上传常量类
 * @author 张俊杰
 * @date 2021/11/11  - {TIME}
 */
@Component
public class UploadConstants {

    /**
     * 用户头像默认上传路径
     */
    public static  String UPLOAD_AUTHOR_IMG = "D:/tmp/jblog/authorImg/";

    /**
     * 文章图片默认上传路径
     */
    public static  String FILE_UPLOAD_DIC = "D:/tmp/jyblog/upload/";

    /**
     * 用户头像数据库路径
     */
    public static  String SQL_AUTHOR_IMG = "/authorImg/";

    /**
     * 文章图片数据库路径
     */
    public static  String SQL_FILE_DIC = "/upload/";

    /**
     * 切换 Https://域名 访问
     */
    public static String UPLOAD_IMG_IP = "https://renrenzi.top/imgs/";

    //public static String UPLOAD_IMG_IP = "http://120.39.217.37:2234/";

    @Value("upload.uploadAuthorImg")
    public static void setUploadAuthorImg(String uploadAuthorImg) {
        UploadConstants.UPLOAD_AUTHOR_IMG = uploadAuthorImg;
    }
    @Value("upload.fileUploadDic")
    public static void setFileUploadDic(String fileUploadDic) {
        UploadConstants.FILE_UPLOAD_DIC = fileUploadDic;
    }
    @Value("upload.sqlAuthorImg")
    public static void setSqlAuthorImg(String sqlAuthorImg) {
        UploadConstants.SQL_AUTHOR_IMG = sqlAuthorImg;
    }
    @Value("upload.sqlFileDic")
    public static void setSqlFileDic(String sqlFileDic) {
        UploadConstants.SQL_FILE_DIC = sqlFileDic;
    }
}
