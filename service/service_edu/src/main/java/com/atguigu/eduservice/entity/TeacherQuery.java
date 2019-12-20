package com.atguigu.eduservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 * @create 2019-12-20 17:36
 */
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "名称模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "开始时间")
    private String bigin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "结束时间")
    private String end;
}
