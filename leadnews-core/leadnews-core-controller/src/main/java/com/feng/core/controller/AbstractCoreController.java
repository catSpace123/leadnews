package com.feng.core.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.common.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/***
 * 描述
 * @author feng
 * @packagename com.changgou.core
 * @version 1.0
 * @date 2020/8/10
 */

public abstract class AbstractCoreController<T> implements ICoreController<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCoreController.class);


    /**
     * 调用方的service
     */
    protected IService<T> coreService;
    public AbstractCoreController(IService<T> coreService) {
        this.coreService = coreService;
    }



    //请求进入处理器之前进行触发
    /*@Override
    public void preHandle(WebRequest request) throws Exception {
        String[] headerValues = request.getHeaderValues(SystemConstants.USER_HEADER_NAME);
        if(headerValues!=null && headerValues.length>0){
            userInfoMap.put(SystemConstants.USER_HEADER_NAME,headerValues[0]);
        }else{
            userInfoMap.put(SystemConstants.USER_HEADER_NAME,null);
            logger.info("自媒体用户登录之后的用户ID为: null值");
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        //之中
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        //之后
    }*/

    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public Result deleteById(@PathVariable(name = "id") Serializable id) {
        boolean flag = coreService.removeById(id);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 添加记录
     *
     * @param record
     * @return
     */
    @PostMapping
    @Override
    public Result<T> insert(@RequestBody T record) {
        boolean flag = coreService.save(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok(record);
    }

    //更新数据
    @Override
    @PutMapping
    public Result updateByPrimaryKey(@RequestBody T record) {
        boolean flag = coreService.updateById(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    @GetMapping("/{id}")
    public Result<T> findById(@PathVariable(name = "id") Serializable id) {
        T t = coreService.getById(id);
        return Result.ok(t);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    @GetMapping
    public Result<List<T>> findAll() {
        List<T> list = coreService.list();
        return Result.ok(list);
    }


    /**
     * 条件查询  通用的只考虑相等的情况
     * @param record
     * @return
     */
    @Override
    @PostMapping("/listCondition")
    public Result<List<T>> findByRecord(@RequestBody T record) {
        //查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        /**
         * 反射获取所有属性值
         */
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            /**
             * 在获取属性上面有这个注解的属性
             */
            TableField annotation = field.getAnnotation(TableField.class);
            try {
                //暴力反射
                field.setAccessible(true);


                /*
                    获取这个字段的值
                 */
                Object value = field.get(record);

                if(value!=null) {
                    /**
                     * annotation.value()  注解里面的字段，（表字段）   value  属性值
                     */
                    queryWrapper.eq(annotation.value(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        List<T> list = coreService.list(queryWrapper);
        return Result.ok(list);
    }

    /**
     * 通用条件分页查询
     *
     * @param pageRequestDto
     * @return
     */
    @PostMapping(value = "/search")
    @Override
    public Result<PageInfo<T>> findByPage(@RequestBody PageRequestDto<T> pageRequestDto) {
        /**
         * 获取页码
         */
        Page page = new Page(pageRequestDto.getPage(), pageRequestDto.getSize());

        //条件 name 查询 非 lamda表达式查询条件

        QueryWrapper<T> queryWrapper = getWrapper(pageRequestDto.getBody());
        IPage iPage = coreService.page(page, queryWrapper);
        PageInfo<T> pageInfo = new PageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages(), iPage.getRecords());
        return Result.ok(pageInfo);
    }

    private QueryWrapper<T> getWrapper(T body) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (body == null) {
            return queryWrapper;
        }
        //获取所有的属性
        Field[] declaredFields = body.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                //遇到 id注解 则直接跳过 不允许实现根据主键查询
                //https://www.coder.work/article/2808807
                if (declaredField.isAnnotationPresent(TableId.class) || declaredField.getName().equals("serialVersionUID")) {
                    //遇到
                    continue;
                }
                //属性描述器  record.getClass()
                PropertyDescriptor propDesc = new PropertyDescriptor(declaredField.getName(), body.getClass());
                //获取这个值  先获取读方法的方法对象,并调用获取里面的值
                Object value = propDesc.getReadMethod().invoke(body);
                //如果是字符串
                TableField annotation = declaredField.getAnnotation(TableField.class);
                //如果传递的值为空则不做处理
                if(value != null) {
                    //如是字符串 则用like
                    if (value.getClass().getName().equals("java.lang.String")) {
                        queryWrapper.like(annotation.value(), value);
                    } else {
                        //否则使用=号
                        queryWrapper.eq(annotation.value(), value);
                    }
                }

            } catch (Exception e) {
                logger.error(e.getCause().getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return queryWrapper;
    }



   //获取分页对象
   protected PageInfo<T> getPageInfo(IPage<T> iPage){
        return new PageInfo<T>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal(),iPage.getPages(),iPage.getRecords());
   }




}
