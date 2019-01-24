package com.example.demo.Job;

import com.example.demo.mapper.TestPaperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LaunchTaskJob {

    @Autowired
    private TestPaperMapper testPaperMapper;

    @Scheduled(cron="0 0 0 * * ?")
    public void changeTestPaperStatus(){
        try{
            log.info("<------------------试卷发布状态更新开始---------------------->");
            testPaperMapper.changeTestPaperEndStatus();
            testPaperMapper.changeTestPaperLaunchStatus();
        }catch (Exception e){
            log.info("更新试卷发布状态失败："+e.getLocalizedMessage());
            e.printStackTrace();
        }finally {
            log.info("<------------------试卷发布状态更新结束---------------------->");
        }
    }

}
