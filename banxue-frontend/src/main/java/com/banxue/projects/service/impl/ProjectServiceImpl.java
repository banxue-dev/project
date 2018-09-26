package com.banxue.projects.service.impl;

import com.banxue.projects.entity.Project;
import com.banxue.projects.mapper.ProjectMapper;
import com.banxue.projects.service.IProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feng
 * @since 2018-09-25
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
