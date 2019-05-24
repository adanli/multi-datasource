package com.multi.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.multi.biz.service.UserService;
import com.multi.common.db.mapper.one.UserMapper;
import com.multi.common.db.model.one.User;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
