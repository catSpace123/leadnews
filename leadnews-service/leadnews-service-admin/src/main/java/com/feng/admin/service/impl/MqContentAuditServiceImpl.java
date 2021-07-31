package com.feng.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.feng.admin.mapper.AdChannelMapper;
import com.feng.admin.mapper.AdSensitiveMapper;
import com.feng.admin.pojo.AdChannel;
import com.feng.admin.service.MqContentAuditService;
import com.feng.article.feign.ApArticleFeign;
import com.feng.article.feign.ApAuthorFegin;
import com.feng.article.pojo.*;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.Result;
import com.feng.common.ulits.BusinessConstants;
import com.feng.common.ulits.GreenImageScan;
import com.feng.common.ulits.GreenTextScan;
import com.feng.common.ulits.SensitiveWordUtil;
import com.feng.dfs.feign.DfsFeign;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.feign.ApUserSearchFeign;
import com.feng.wemedia.feign.WmNewsFeign;
import com.feng.wemedia.pojo.ContentVo;
import com.feng.wemedia.pojo.WmNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 自媒体文章审核业务接口实现类
 */
@Service
@Slf4j
public class MqContentAuditServiceImpl implements MqContentAuditService {

    @Autowired
    private WmNewsFeign wmNewsFeign;

    @Autowired
    private AdSensitiveMapper adSensitiveMapper;

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private DfsFeign dfsFeign;

    @Autowired
    private ApAuthorFegin apAuthorFegin;

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Autowired
    private ApArticleFeign apArticleFeign;

    @Autowired
    private ApUserSearchFeign apUserSearchFeign;

    /**
     * 文本内容审核
     *
     * @param wmId
     */
    @Override
    public void contentAudit(Integer wmId)  throws Exception{

        /**
         * 根据id查询自媒体用户发布的文章
         */
        Result<WmNews> newsResult = wmNewsFeign.findById(wmId);

        WmNews wmNews = newsResult.getData();
        boolean flag = false;

        if (wmNews != null) {
            //获取文本和图片
            Map<String, List<String>> textAndImage = getTextAndImageUri(wmNews);
            /**
             * 运作自己定义的敏感词算法经行敏感词检测
             * 查询敏感词列表
             */

            List<String> stringList = adSensitiveMapper.findSensitive();

            if (CollectionUtils.isEmpty(stringList)) {
                throw new LeadnewsException("敏感词不能为空");
            }
            //获取上传后的状态
            String contentAndImageAudit = getContentAndImageAudit(textAndImage, stringList);
            //判断状态修改表中的内容
            switch (contentAndImageAudit){
                case BusinessConstants.ContentAudit.BLOCK :{
                    //表示审核失败更改自媒体表的状态
                    changeWmNewsStatus(wmId,2,"文章或图片有违禁");
                    break;
                }
                case BusinessConstants.ContentAudit.REVIEW : {
                    //改为人工审核
                    changeWmNewsStatus(wmId,3,"审核失败转为人工审核");
                    break;
                }
                case BusinessConstants.ContentAudit.PASS :{
                    //如果通过判断发布时间是否为空，为空就把值同步到文章表中，和改变自媒体表的状态为发布
                    if(wmNews.getPublishTime() != null){
                        //定时发布
                        changeWmNewsStatus(wmId,8,"审核通过待发布");
                    }else {
                        //表示立即发布
                        changeWmNewsStatus(wmId, 9,"审核通过");
                        //该为true，下面经行添加保存其他表
                        flag = true;
                    }
                    break;
                }
                default:{
                    log.debug("没有正确的状态");
                    break;
                }
            }

            if(flag){
                //用来保存文章和内容
                getApArticleAndSave(wmNews);



            }
        }

    }

    /**
     * 抽取的方法 用来保存文章和内容
     * @param wmNews
     * @return
     */
    public void getApArticleAndSave(WmNews wmNews) {
        ArticleInfoDto articleInfoDto = new ArticleInfoDto();
        //文章对象
        ApArticle apArticle = new ApArticle();
        //根据用户自媒体id获取作者信息
        ApAuthor apAuthor = apAuthorFegin.findByWmUserId(wmNews.getUserId()).getData();

        //文章标题
        apArticle.setTitle(wmNews.getTitle());
        if(wmNews.getArticleId() != null){
            //说明是更新  文章id
            apArticle.setId(wmNews.getArticleId());
        }


        if(apAuthor != null){
            //作者id
            apArticle.setAuthorId(apAuthor.getId());
            //作者名称
            apArticle.setAuthorName(apAuthor.getName());
        }
        /**
         * 根据频道id获取频道信息
         */
        AdChannel channel = adChannelMapper.selectById(wmNews.getChannelId());
        if(channel != null){
            //频道id跟频道名称
            apArticle.setChannelId(channel.getId());
            apArticle.setChannelName(channel.getName());
        }
        //文章类型  0图，2图，多图
        apArticle.setLayout(wmNews.getType());
        //普通文章
        apArticle.setFlag(0);
        //封面图片
        apArticle.setImages(wmNews.getImages());
        //标签
        apArticle.setLabels(wmNews.getLabels());
        if(wmNews.getPublishTime() != null){
            //定时发布
            apArticle.setPublishTime(wmNews.getPublishTime());
        }else{
            //立刻发布
            apArticle.setPublishTime(LocalDateTime.now());
        }

        articleInfoDto.setApArticle(apArticle);


        //设置ap_artile_config  表
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        //是否可评论
        apArticleConfig.setIsComment(1);
        //是否以删除
        apArticleConfig.setIsDelete(0);
        //是否已下架
        apArticleConfig.setIsDown(0);
        //是否转发
        apArticleConfig.setIsForward(0);

        articleInfoDto.setApArticleConfig(apArticleConfig);

        //文章对象
        ApArticleContent apArticleContent = new ApArticleContent();
        //设置文章
        apArticleContent.setContent(wmNews.getContent());

        articleInfoDto.setApArticleContent(apArticleContent);

        //远程调用文章微服务添加信息
        ApArticle data = apArticleFeign.saveApArticle(articleInfoDto).getData();


        if(data != null){
            //添加自媒体表中的文章id
            WmNews recod = new WmNews();
            //文章id
            recod.setId(wmNews.getId());
            recod.setArticleId(data.getId());
            recod.setEnable(1);
            wmNewsFeign.updateByPrimaryKey(recod);
        }

        //远程调用搜索微服务把用户发表的文章也上传到es中 创建索引，es 搜索 todo
        ArticleInfoDocument articleInfoDocument = new ArticleInfoDocument();

        //作者id
        articleInfoDocument.setAuthorId(apAuthor.getId());
        //作者名称
        articleInfoDocument.setAuthorName(apAuthor.getName());
        //标题
        articleInfoDocument.setTitle(wmNews.getTitle());
        //图片
        articleInfoDocument.setImages(wmNews.getImages());
        //发布时间
        articleInfoDocument.setPublishTime(data.getPublishTime());
        //创建时间
        articleInfoDocument.setCreatedTime(data.getCreatedTime());
        articleInfoDocument.setLayout(data.getLayout());
        apUserSearchFeign.saveEs(articleInfoDocument);

    }

    /**
     * 用来修改自媒体表中的状态
     * @param wmId      主键id
     * @param status    状态
     */
    public void changeWmNewsStatus(Integer wmId,Integer status,String reason){
        WmNews record = new WmNews();
        record.setId(wmId);
        record.setStatus(status);
        //拒绝理由
        record.setReason(reason);

        wmNewsFeign.updateByPrimaryKey(record);
    }

    /**
     * 获取文本图片审核结果
     * @param textAndImage  图片和文本
     * @param stringList  敏感词列表
     * @return  返回审核结果  pass   block不通过  review  人工审核
     * @throws Exception
     */
    private String getContentAndImageAudit(Map<String, List<String>> textAndImage, List<String> stringList) throws Exception {

            //初始化铭感词树
            SensitiveWordUtil.initMap(stringList);
            //检测文本是否包含敏感词
            List<String> list = textAndImage.get(BusinessConstants.ContentAudit.TEXT);

            for (String text : list) {
                /**
                 * 敏感词过滤算法DFA
                 * 调用工具类检测结果
                 */
                Map<String, Integer> matchWords = SensitiveWordUtil.matchWords(text);
                /**
                 * 如果为空表示敏感词审核通过，在调用阿里云审核图片和文本
                 */
                if (!CollectionUtils.isEmpty(matchWords)) {
                    return BusinessConstants.ContentAudit.BLOCK;
                }
            }
            /**
             * 获得文本审核结果
             */
            Map map1 = greenTextScan.greeTextScan(list);
            String TextPassType = getIsPassType(map1);
            if(!BusinessConstants.ContentAudit.PASS.equals(TextPassType)){
                return TextPassType;
            }

            /**
             *
             * 图片审核
             */
            //获取图片地址
            List<String> imageList = textAndImage.get(BusinessConstants.ContentAudit.IMAGE);

            /**
             * 远程调用feign获取图片字节数组
             *
             */
            List<byte[]> imageByte = dfsFeign.getImageByte(imageList);

            //阿里云审核
            Map map2 = greenImageScan.imageScan(imageByte);
            //判断审核结果
            String isPassType = getIsPassType(map2);
            if(!BusinessConstants.ContentAudit.PASS.equals(isPassType)){
                return isPassType;
            }
        return BusinessConstants.ContentAudit.PASS;
    }


    /**
     * 判断审核后的的类型并返回
     */
    public String getIsPassType(Map map){

        String suggestion = (String) map.get("suggestion");

        /**
         * 人工审核状态
         */
        if(BusinessConstants.ContentAudit.REVIEW.equals(suggestion)){
            return BusinessConstants.ContentAudit.REVIEW;

        }
        /**
         * 不通过状态
         */
        if(BusinessConstants.ContentAudit.BLOCK.equals(suggestion)){
            return BusinessConstants.ContentAudit.BLOCK;
        }
        /**
         * 通过
         */
        return BusinessConstants.ContentAudit.PASS;
    }


    /**
     * 获文本和图片地址
     */
    public Map<String, List<String>> getTextAndImageUri(WmNews wmNews) {

        Map<String, List<String>> map = new HashMap<>();

        //存储查询到的图片地址
        List<String> image = new ArrayList<>();
        //存储内容
        List<String> text = new ArrayList<>();

        //获取标题内容
        String title = wmNews.getTitle();

        text.add(title);
        /**
         * 获取文章内容
         */
        String content = wmNews.getContent();

        List<ContentVo> contentVos = JSON.parseArray(content, ContentVo.class);

        if (!CollectionUtils.isEmpty(contentVos)) {
            //遍历
            for (ContentVo contentVo : contentVos) {
                //判断类型是文本还是图片 分别添加到不同的集合中
                if ("text".equals(contentVo.getType())) {
                    text.add(contentVo.getValue());
                } else {
                    image.add(contentVo.getValue());
                }
            }
        }
        /**
         * 获取封面图片
         */
        String images = wmNews.getImages();
        List<String> list = Arrays.asList(images.split(","));

        /**
         * 把封面图片全部添加到一起
         */
        image.addAll(list);

        map.put(BusinessConstants.ContentAudit.TEXT, text);
        map.put(BusinessConstants.ContentAudit.IMAGE, image);
        return map;
    }
}
