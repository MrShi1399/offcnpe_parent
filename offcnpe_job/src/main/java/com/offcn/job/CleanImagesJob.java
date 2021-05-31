package com.offcn.job;

import com.offcn.util.MyFileUtils;
import com.offcn.util.RedisConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Set;

/**
 * 清理垃圾图片文件
 * @author 13320
 */
@Component
public class CleanImagesJob {

    @Value("${imgFilePath}")
    private String imgFilePath;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 在每个月的每周的某天的11时05分00秒执行
     */
    @Scheduled(cron = "0 05 11 ? * *")
    public void cleanImagesJob(){
        Set<String> difference=redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_UPLOAD,RedisConstant.SETMEAL_PIC_DB);
        //有垃圾图片时执行
        if(difference!=null&&difference.size()>0){
            for (String img : difference) {
                File file=new File(imgFilePath+img);
                file.delete();
            }
        }
        redisTemplate.delete(RedisConstant.SETMEAL_PIC_UPLOAD);
        redisTemplate.delete(RedisConstant.SETMEAL_PIC_DB);
    }
}
