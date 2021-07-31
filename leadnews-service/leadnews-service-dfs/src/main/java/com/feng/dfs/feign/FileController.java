package com.feng.dfs.feign;

import com.feng.common.ulits.GreenImageScan;
import com.feng.common.ulits.GreenTextScan;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来上传文件
 */
@RestController
@RequestMapping("/dfs")
public class FileController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    //aliyun 图片审核
    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private GreenTextScan greenTextScan;   //文本审核


    /**
     * 文件图片上传
     * @param file
     * @return
     */
    @PostMapping
    public Map<String,Object> upLoad(MultipartFile file) throws Exception {

        /**
         * 上传图片
         * 获取文件流
         * 获取文件大小
         * 获取文件后缀
         */
        InputStream inputStream = file.getInputStream();
        long fileSize = file.getSize();
        String filename = StringUtils.getFilenameExtension(file.getOriginalFilename());    //这个工具类可以获取文件的后缀，不带“.”
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, fileSize, filename, null);

        String fullPath = storePath.getFullPath();   //获取文件上传后的地址
        String webServerUrl = fdfsWebServer.getWebServerUrl();  //获取前缀 （文件存储的服务器http:ip）

        //可以访问的文件路径
        String fileUrl = webServerUrl +fullPath;
        //===========================用来测试阿里云图片审核start
       /* List<byte[]> list = new ArrayList<>();
        list.add(file.getBytes());  //把文件转化为字节数组
        Map map = greenImageScan.imageScan(list);
        System.out.println(map);

        List<String> text = new ArrayList<>();
        text.add("海洛因便宜买");
        Map textScan = greenTextScan.greeTextScan(text);

        System.out.println(textScan);*/

        //===========================用来测试阿里云图片审核end

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileUrl",fileUrl);



        return hashMap;
    }

    /**
     * 下载图片获取字节数组
     * @param imgList  图片地址
     * @return 返回图片的字节数组
     */

    @PostMapping("/downloadFile")
    public List<byte[]> getImageByte(@RequestBody List<String> imgList){

        List<byte[]> list = new ArrayList<>();

        if(CollectionUtils.isEmpty(imgList)){
            return list;
        }

        for (String imgUrl : imgList) {
            //下载图片 根据地址获取图片组，和url
            StorePath storePath = StorePath.parseFromUrl(imgUrl);

            /**
             * 获取组名
             */
            String group = storePath.getGroup();
            /**
             * 获取url地址
             */
            String path = storePath.getPath();

            /**
             * 获取图片字节数组
             */
            byte[] bytes =  fastFileStorageClient.downloadFile(group, path, new DownloadCallback<byte[]>() {

                @Override
                public byte[] recv(InputStream ins) throws IOException {
                    /**
                     * 把输入流转化为字节数组
                     */
                    return IOUtils.toByteArray(ins);
                }
            });
            list.add(bytes);
        }
        return list;
    }

   
}
