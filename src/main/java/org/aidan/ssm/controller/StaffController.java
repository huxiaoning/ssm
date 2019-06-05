package org.aidan.ssm.controller;


import org.aidan.ssm.vo.OptResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

    @GetMapping
    public OptResult hello() {
        return OptResult.ok("Hello", new HashMap<String, String>() {{
            put("a", "1");
        }});
    }
}

