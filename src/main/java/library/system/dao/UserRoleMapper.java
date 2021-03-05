package library.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yeyuting
 * @create 2021/3/1
 */
@Repository
@Mapper
public interface UserRoleMapper {

    //插入一条记录
    public Boolean insertOne(String userId , int roleId) ;
}
