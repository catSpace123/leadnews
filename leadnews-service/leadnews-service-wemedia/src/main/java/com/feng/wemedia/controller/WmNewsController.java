package com.feng.wemedia.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.common.pojo.Result;
import com.feng.common.ulits.BusinessConstants;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.*;
import com.feng.wemedia.service.WmNewsService;
import com.feng.wemedia.service.WmUserService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
* 自媒体图文内容信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体图文内容信息表",tags = "WmNewsController")
@RestController
@RequestMapping("/wmNews")
public class WmNewsController extends AbstractCoreController<WmNews> {

    private WmNewsService wmNewsService;

    @Autowired
    private WmUserService wmUserService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //注入
    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        super(wmNewsService);
        this.wmNewsService=wmNewsService;
    }

    /**
     * 查询自媒体文章列表
     * @param pageRequestDto
     * @return
     */
    @PostMapping("/findWmNewsList")
    @ApiOperation(notes = "查询条件可选",value = "查询自媒体文章列表")
    public Result<PageInfo<WmNewsVo>> findWmNewsList(@RequestBody PageRequestDto<WmNewsDto> pageRequestDto){

        //获取自媒体用户id
        String userId = GetUserIdHeaderUtil.getUserId();
        pageRequestDto.getBody().setUserId(Integer.valueOf(userId));

        PageInfo<WmNewsVo>  pageInfo =  wmNewsService.findWmNewsList(pageRequestDto);
        return Result.ok(pageInfo);
    }


    /**
     * 提交保存自媒体文章
     * @return
     */
    @PostMapping("/save/{isSubmit}")
    public Result saveWmNew(@PathVariable("isSubmit") int isSubmit, @RequestBody WmNewsDtoSave wmNewsDtoSave){

        if(isSubmit < 0 || isSubmit > 1){
            throw new LeadnewsException("格式不正确");
        }

        wmNewsDtoSave.setStatus(isSubmit);

        wmNewsService.saveWmNew(wmNewsDtoSave);

        return Result.ok();
    }

    /**
     *
     * 根据id获取文章
     */
    @GetMapping("one/{id}")
    public Result<WmNewsDtoSave> getWmNewsById(@PathVariable("id") int id){

        WmNewsDtoSave wmNewsDtoSave =  wmNewsService.getWmNewsById(id);
        return Result.ok(wmNewsDtoSave);
    }


    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Serializable id) {
        /**
         *先根据id查询判断要删除的文章是否已经上线
         */
        WmNews wmNews = wmNewsService.getById(id);
        if(wmNews.getStatus() == 9 || wmNews.getEnable() == 1){
           return Result.errorMessage("该文章已上线，请先下架");
        }
        return super.deleteById(id);
    }

    /**
     * 用于查询未审核通过的和人工审核列表查询，进行人工审核
     * @param title  查询条件
     * @return
     */
    @GetMapping("/searchAudit")
    @ApiOperation(value = "findWmNewsAudit",notes ="用于查询未审核通过的和人工审核列表，进行人工审核" )
    public Result<PageInfo<WmNewsSearchVo>> findWmNewsAudit(@RequestParam(defaultValue = "1") Long page,
                                  @RequestParam(defaultValue = "10") Long pagesize,
                                  @RequestParam(required = false) String title){
        PageInfo<WmNewsSearchVo>  wmNewsSearchVo = wmNewsService.findWmNewsAudit(page,pagesize,title);

        return Result.ok(wmNewsSearchVo);
    }

    /**
     * 人工审核和驳回  审核通过统一设置状态 为 8  待发布
     * @param id
     * @return
     */
    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Integer id,@PathVariable("status") Integer status){

        if(status == 8 || status == 2){
            WmNews entity = new WmNews();
            entity.setId(id);
            entity.setStatus(status);
            wmNewsService.updateById(entity);
        }else {
            return Result.errorMessage("状态不正确");
        }
        return Result.ok();
    }


    /**
     * 根据id查询后台审核的文章
     * @param id
     * @return
     */
    @GetMapping("/Audit{id}")
    public Result<WmNewsDtoSave> findByIdAudit(@PathVariable("id") Integer id){
        WmNewsDtoSave wmNewsById = wmNewsService.getWmNewsById(id);

        //获取用户id
        WmUser wmUser = wmUserService.getById(wmNewsById.getUserId());
        wmNewsById.setAuthorName(wmUser.getName());

        return Result.ok(wmNewsById);
    }


    /**
     * 查询自媒体文章表中的状态=8 的文章列表进行对比时间发布
     * @return
     */
    @GetMapping("/fundState")
    public Result<List<WmNews>> fundState(@RequestParam("stare") int stare){

        QueryWrapper<WmNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",stare);
        List<WmNews> list = wmNewsService.list(queryWrapper);
        return Result.ok(list);
    }


    /**
     * 对文章经行上下架操作
     * @param id  文章id
     * @param enable 上下架   0 下架  ，1 上架
     * @return
     */
    @GetMapping("/isEnable/{id}/{enable}")
    public Result isDonLoad(@PathVariable("id") int id ,@PathVariable("enable") int enable){

        WmNews entity = new WmNews();
        entity.setId(id);
        entity.setEnable(enable);
        wmNewsService.updateById(entity);

        //根据id查询文章id
        WmNews wmNews = wmNewsService.getById(id);

        //发送消息到mq 同步文章表的上下架操作
        Map<String,String> map = new HashMap<>();
        map.put("id",wmNews.getArticleId().toString());
        map.put("enable",enable+"");
        kafkaTemplate.send(BusinessConstants.ContentAudit.WM_NEWS_DOWN_OR_UP_TOPIC, JSON.toJSONString(map));

        return Result.ok();
    }

}

