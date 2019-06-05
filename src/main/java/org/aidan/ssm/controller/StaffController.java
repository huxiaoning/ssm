package org.aidan.ssm.controller;


import org.aidan.ssm.entity.Staff;
import org.aidan.ssm.service.StaffService;
import org.aidan.ssm.vo.OptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工表 员工表 前端控制器
 * </p>
 *
 * @author Aidan
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public OptResult hello() {

        Staff staff = new Staff();
        staff.setName("111");
        staff.setAge(18);
        staff.setSex(1);
        staffService.save(staff);
        return OptResult.ok("Hello", staff);
    }
}

