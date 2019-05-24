package com.multi.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.multi.biz.service.ProjectService;
import com.multi.common.db.mapper.two.ProjectMapper;
import com.multi.common.db.model.two.Project;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}
