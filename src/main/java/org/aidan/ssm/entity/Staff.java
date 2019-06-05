package org.aidan.ssm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 员工表 员工表
 * </p>
 *
 * @author Aidan
 * @since 2019-06-05
 */
@TableName("t_staff")
@ApiModel(value = "Staff对象", description = "员工表 员工表")
public class Staff extends Model<Staff> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "员工姓名 员工姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "年龄 年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty(value = "性别 性别")
    @TableField("sex")
    private Integer sex;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", sex=" + sex +
                "}";
    }
}
