package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 王云飞
 * @since 2019-12-20
 */
/*
定义接口说明和参数说明
定义在类上：@Api
定义在方法上：@ApiOperation
定义在参数上：@ApiParam
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询所有讲师数据")
    @GetMapping("/getAllTeacher")
    public R getAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }

    //浏览器直接测试只能是get提交方式，删除是delete提交方式
    @ApiOperation(value = "根据id删除讲师数据")
    @DeleteMapping("removeById/{id}")
    public R removeById(@ApiParam(value = "讲师id",name = "id",required = true)
                        @PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        }else{
            return R.error();
        }

    }


    @ApiOperation(value = "带条件分页查询讲师数据")
    //带条件分页查询
    @PostMapping("getPage/{pageNum}/{limitcount}")
    public R getPage(
                    @ApiParam(value = "查询的页码",name = "pageNum",required = true)
                    @PathVariable Integer pageNum,
                     @ApiParam(value = "每页查询条数",name = "limitcount",required = true)
                     @PathVariable Integer limitcount,
                    @ApiParam(value = "查询条件",name = "teacherQuery",required = false)
                    @RequestBody TeacherQuery teacherQuery
                    ){
        //1 创建Page对象，传入当前页 和 每页记录数
        Page<EduTeacher> page = new Page<>(pageNum,limitcount);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String bigin = teacherQuery.getBigin();
        String end = teacherQuery.getEnd();

        //如果名字不为空
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(bigin)){
            wrapper.ge("gmtCreate",bigin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmtCreate",end);
        }

        teacherService.page(page,wrapper);
        List<EduTeacher> teacherList = page.getRecords();
        return R.ok().data("teacherList",teacherList);
    }


    //添加讲师数据
    @ApiOperation(value = "添加讲师数据")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(value = "请输入要添加的数据",name = "eduTeacher",required = true)
                        @RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }



    //根据id查询讲师数据
    @ApiOperation(value = "根据id查询讲师数据")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@ApiParam(value = "讲师id",name = "id",required = true)
                            @PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    //修改讲师数据
    @ApiOperation(value = "修改讲师数据")
    @PutMapping("updateTeacher")
    public R updateTeacher( @ApiParam(value = "修改的讲师数据",name = "eduTeacher",required = true)
                            @RequestBody EduTeacher eduTeacher){
        boolean update = teacherService.updateById(eduTeacher);
        if (update){
            return  R.ok();
        }else {
            return  R.error();
        }
    }



}

