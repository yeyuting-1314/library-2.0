package library.system.dao;

import library.system.dto.SysBook;
import library.system.dto.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeyuting
 * @create 2021/2/25
 */
@Repository
@Mapper
public interface UserMapper {
    public Boolean insertOneUser(SysUser sysUser) ;

    public SysUser selectUserByUserId(SysUser sysUser) ;


}
