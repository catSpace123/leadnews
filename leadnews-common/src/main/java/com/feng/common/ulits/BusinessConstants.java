package com.feng.common.ulits;

public interface BusinessConstants {
    //实名认证相关
   public static class ApUserRealnameConstants{
        //创建中
        public static final Integer SHENHE_ING=0;
        //待审核
        public static final Integer SHENHE_WARTING=1;
        //审核失败
        public static final Integer SHENHE_FAILE=2;
        //审核通过
        public static final Integer SHENHE_SUCESS=9;
    }


    public static class ApAuthorConstants{
        /**
         * 平台自媒体人
         */
        public static final Integer A_MEDIA_USER= 2;
        //合作商
        public static final Integer A_MEDIA_SELLER= 1;
        //普通作者
        public static final Integer A_MEDIA_ZERO= 0;
    }

    public static class TokenInfo{
        /**
         * 用来解决不同角色能同时登录前后台的问题
         */
        public static final String ADMIN_ROLE = "admin";    //管理员
        public static final String WM_USER_ROLE = "wmUser"; //自媒体
        public static final String USER_ROLE = "user";      //普通用户

    }

    public static class ContentAudit{
        /**
         * 内容审核
         */
        public static final String Content_Audit = "contentAudit";  //用来内容审核的mq主题

        /**
         * 上下架同步主题
         */
        public static final String WM_NEWS_DOWN_OR_UP_TOPIC = "wm.news.up.or.down.topic";


        /**
         * 行为主题
         */
        public static final String AP_USER_BEHAVIOUR_OR_UP_TOPIC = "ap.user.behaviour.or.up.topic";

        /**
         * 关键词主题
         */
        public static final String KEY_WORD_TOPIC = "key.word.topic";




        /**
         * 通过
         */
        public static final String PASS = "pass";
        /**
         * 拒绝
         */
        public static final String BLOCK="block";
        /**
         * 不确定
         */
        public static final String REVIEW="review";



        public static final String TEXT="text";   //表示文本


        public static final String IMAGE="image";  //表示图片


   }

}