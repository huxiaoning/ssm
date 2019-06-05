package org.aidan.ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aidan.ssm.entity.Staff;
import org.aidan.ssm.mapper.StaffMapper;
import org.aidan.ssm.service.StaffService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工表 员工表 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-06-05
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {

}
