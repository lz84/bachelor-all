package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.OrgMenu;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrgMenuMapper extends Mapper<OrgMenu> {
}