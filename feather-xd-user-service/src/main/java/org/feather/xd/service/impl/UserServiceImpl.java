package org.feather.xd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.mapper.UserMapper;
import org.feather.xd.model.UserDO;
import org.feather.xd.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {

}
