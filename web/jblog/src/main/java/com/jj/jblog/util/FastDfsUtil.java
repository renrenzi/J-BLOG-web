package com.jj.jblog.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import java.io.IOException;

/**
 * FastDfs 文件上传工具类
 * @author 张俊杰
 */
public class FastDfsUtil {

    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;


    public static StorageClient getStorageClient() throws IOException, MyException {
        //1.加载配置文件，默认去classpath下加载
        ClientGlobal.init("fastdfs.conf");
        //2.创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //3.创建TrackerServer对象
        trackerServer = trackerClient.getConnection();
        //4.创建StorageServler对象
        storageServer = trackerClient.getStoreStorage(trackerServer);
        //5.创建StorageClient对象，这个对象完成对文件的操作
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        return storageClient;
    }

    public static void closeFastDFS() {
        if (storageServer != null) {
            try {
                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (trackerServer != null) {
            try {
                trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] fileUpload(byte[] buffFile,String fileName){
        String [] uploadArray = null;
        try {
            //1. 获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            //2.上传文件  第一个参数：本地文件路径 第二个参数：上传文件的后缀 第三个参数：文件信息
            uploadArray = storageClient.upload_file(buffFile,fileName,null);
            for (String str:uploadArray) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            closeFastDFS();
        }
        return uploadArray;
    }

    public static byte[] fileDownload(String groupName,String remotefilepath){
        byte[] result = null;
        try {
            //1. 获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            //2.下载文件 返回0表示成功，其它均表示失败
            result = storageClient.download_file(groupName, remotefilepath);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            closeFastDFS();
        }
        return result;
    }

    public static int fileDelete(String groupName,String remotefilepath){
        int num = 1;
        try {
            //1. 获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            //2.删除文件 返回0表示成功，其它均表示失败
            num = storageClient.delete_file(groupName,remotefilepath);
            System.out.println(num);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            closeFastDFS();
        }
        return num;
    }
}
