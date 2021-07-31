package com.feng.article.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.article.pojo.ApAuthor;
import com.feng.article.service.ApAuthorService;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
* APP文章作者信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP文章作者信息表",tags = "ApAuthorController")
@RestController
@RequestMapping("/apAuthor")
public class ApAuthorController extends AbstractCoreController<ApAuthor> {

    private ApAuthorService apAuthorService;

    //注入
    @Autowired
    public ApAuthorController(ApAuthorService apAuthorService) {
        super(apAuthorService);
        this.apAuthorService=apAuthorService;
    }

    /**
     * 创建作者
     * @param apAuthor
     * @return
     */
    @RequestMapping("/saveAuthor")
    public Result saveAuthor(@RequestBody ApAuthor apAuthor){

        apAuthorService.saveAuthor(apAuthor);
     return  Result.ok();
    }


    /**
     * 根据自媒体用户id查询作者信息
     * @param wmUserId
     * @return
     */
    @GetMapping("wmUserId/{id}")
    public Result<ApAuthor> findByWmUserId(@PathVariable("id") Integer wmUserId){

        QueryWrapper<ApAuthor> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id",wmUserId);
        ApAuthor apAuthor = apAuthorService.getOne(queryWrapper);
        return Result.ok(apAuthor);
    }

}

