package com.feng.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */
@Component
public class TestTask {


    //写一个方法 用来测试 是一个任务
    @XxlJob("testJob")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        System.out.println("打印数据："+ param);

        return ReturnT.SUCCESS;
    }
}
