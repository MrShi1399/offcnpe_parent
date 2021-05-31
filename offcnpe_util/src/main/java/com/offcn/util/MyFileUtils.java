package com.offcn.util;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件下载和上传工具类
 * @author 13320
 */
public class MyFileUtils {
    /**
     * 文件上传和下载的默认路径
     */
    private static String defaultPath ="E:\\code\\uploadfile\\offcnpe\\";

    /**
     * 文件下载
     * @param filename  下载文件的名称
     * @param path  下载文件的路径
     * @return ResponseEntity<byte[]>
     */
    public static ResponseEntity<byte[]> download(String filename, String path){
        checkPath(path);
        return download(filename);
    }


    /**
     * 文件下载，使用默认路径{@value defaultPath}
     * @param filename  下载文件的名称
     * @return  ResponseEntity<byte[]>
     */
    public static ResponseEntity<byte[]> download(String filename){

        //被下载的文件放在哪里
        File file=new File(defaultPath +filename);
        //把下载的文件读取到一个字节数组中
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建一个响应头
        HttpHeaders httpHeaders=new HttpHeaders();
        //转码把utf-8重新编码为ISO-8859-1     浏览器的编码就ISO-8859-1
        String newFileName = null;
        try {
            newFileName = new String(filename.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置弹框的文件名称     放的是重新编码的名称
        httpHeaders.setContentDispositionFormData("acctchment",newFileName);
        //设置响应类型    MediaType.APPLICATION_OCTET_STREAM 以二进制的方式进行下载  所有文件都可以下载
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //返回一个响应对象      ResponseEntity<byte[]>响应对象
        return new ResponseEntity<byte[]>(bytes,httpHeaders, HttpStatus.OK);
    }

    /**
     * 文件上传
     * @param myfile  MultipartFile myfile
     * @param path  上传的路径
     * @return  上传成功后的File对象
     */
    public static File upload(MultipartFile myfile, String path){
        checkPath(path);
        return upload(myfile);
    }

    /**
     * 文件上传，使用默认路径{@value defaultPath}
     * @param myfile  MultipartFile
     * @return 上传成功后的File对象
     */
    public static File upload(MultipartFile myfile){
        File file=null;
        if(myfile!=null&&!myfile.isEmpty()){
            //获取上传文件的名字
            String filename = myfile.getOriginalFilename();
            //文件上传去哪里
            String pat= defaultPath +reName(filename);
            //新建一个空的文件
            file=new File(pat);
            //把上传对象的内容写入到file文件中
            try {
                myfile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 生成新的名字
     * @param fileName 原来的文件名
     * @return 新文件名
     */
    private static String reName(String fileName){
        //获取后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成UUID
        //String newFileName = UUID.randomUUID().toString().replaceAll("-", "")+suffixName;
        //获取当前时
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //生成4位随机数
        String randomNum = String.valueOf(Math.random()).substring(3, 7);
        String newFileName = simpleDateFormat.format(new Date())+randomNum+suffixName;
        return newFileName;
    }

    /**
     * 文件上传，使用默认路径{@value defaultPath}
     * @param myfile  MultipartFile[] myfile
     * @return 上传成功的文件对象的数组
     */
    public static List<File> uploadArray(MultipartFile[] myfile){
        //用于保存上成成功的文件对象
        List<File> fileList=new ArrayList<>();
        if(myfile!=null && myfile.length>0){
            //遍历
            for (MultipartFile multipartFile : myfile) {
                //判断文件是否为空
                if(!multipartFile.isEmpty()){
                    //获取上传文件的名字
                    String filename = multipartFile.getOriginalFilename();
                    //文件上传去哪里
                    String pat= defaultPath +reName(filename);
                    //新建一个空的文件
                    File file=new File(pat);
                    //把上传对象的内容写入到file文件中
                    try {
                        multipartFile.transferTo(file);
                        fileList.add(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 文件上传，
     * @param myfile  MultipartFile[] myfile
     * @param path  上传的路径
     * @return 上传成功的文件对象的数组
     */
    public static List<File> uploadArray(MultipartFile[] myfile,String path){
        checkPath(path);
        return uploadArray(myfile);
    }

    private  static void checkPath(String path){
        if(path!=null && !path.equals("")){
            defaultPath = path;
        }
    }
}
