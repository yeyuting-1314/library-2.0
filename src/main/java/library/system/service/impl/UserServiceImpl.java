package library.system.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import library.base.Constant;
import library.base.Result;
import library.system.dao.BookMapper;
import library.system.dao.BorrowingRecordsMapper;
import library.system.dao.UserMapper;
import library.system.dao.UserRoleMapper;
import library.system.dto.BorrowingRecords;
import library.system.dto.SysBook;
import library.system.dto.SysUser;
import library.system.service.UserService;
import library.util.Results;
import library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yeyuting
 * @create 2021/2/25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper ;

    @Autowired
    RedisTemplate redisTemplate ;

    @Autowired
    BookMapper bookMapper ;

    @Autowired
    TokenUtil tokenUtil ;

    @Autowired
    BorrowingRecordsMapper borrowingRecordsMapper ;

    @Autowired
    UserRoleMapper userRoleMapper ;

    public Boolean insertOneUser(SysUser sysUser){
        return  userMapper.insertOneUser(sysUser) ;
    }

    public SysUser selectUserByUserId(SysUser sysUser){
       return userMapper.selectUserByUserId(sysUser) ;
    }

    //用户注册
    @Transactional
    public Object RegisterUser(SysUser sysUser){
        SysUser sysUser1 = userMapper.selectUserByUserId(sysUser) ;
        if(sysUser1 != null && !sysUser1.equals(null)){
            return "用户已存在，请登陆！" ;
        }
        Boolean result = userMapper.insertOneUser(sysUser) ;
        userRoleMapper.insertOne(sysUser.getUserId() , Constant.ROLE_USER) ;
        return result ;
    }

    //用户登陆
    public Result Login(SysUser sysUser) {
        SysUser sysUser1 = userMapper.selectUserByUserId(sysUser) ;
        if(sysUser1.equals(null)){
            return Results.failure("用户不存在，请先登陆！") ;
        }
        ValueOperations valueOperations = redisTemplate.opsForValue() ;

        SysUser sysUser2 = (SysUser) valueOperations.get(sysUser.getUserId()) ;
        if((sysUser2 != null)&&(!sysUser2.equals(null))){
            redisTemplate.delete(sysUser.getUserId()) ;
        }
        String token = tokenUtil.getToken(sysUser) ;
        sysUser1.setToken(token);
        valueOperations.set(sysUser1.getUserId() , sysUser1);
        if (sysUser.getUserPassword().equals(sysUser1.getUserPassword())){
            return Results.successWithData(sysUser1) ;
        }
        return Results.failure("密码错误，请重新输入密码") ;
    }

    public List<SysBook> selectAllBooks(){
        return bookMapper.selectAllBooks() ;
    }

    public Boolean insertOneBook (SysBook sysBook){
        return bookMapper.insertOneBook(sysBook) ;
    }

    public Boolean insertManyBooks(List<SysBook> sysBooks) {
        return bookMapper.insertManyBooks(sysBooks) ;
    }

    public List<BorrowingRecords> selectAllBorrowingRecords(){
        return borrowingRecordsMapper.selectAllBorrowingRecords() ;
    }

    public Boolean borrowBooks(String bookCode , HttpServletRequest request){
        String token = request.getHeader("token") ;
        DecodedJWT jwt = tokenUtil.deToken(token) ;
        BorrowingRecords borrowingRecords = new BorrowingRecords() ;
        borrowingRecords.setUserId(jwt.getClaim("userId").asString());
        borrowingRecords.setBookCode(bookCode);
        borrowingRecords.setBorrowingStatus(Constant.BORROWBOOKS);
        return borrowingRecordsMapper.borrowBooks(borrowingRecords) ;
    }

    public Boolean returnBooks(String bookCode , HttpServletRequest request){
        String token = request.getHeader("token") ;
        DecodedJWT jwt = tokenUtil.deToken(token) ;
        BorrowingRecords borrowingRecords = new BorrowingRecords() ;
        borrowingRecords.setUserId(jwt.getClaim("userId").asString());
        borrowingRecords.setBookCode(bookCode);
        borrowingRecords.setBorrowingStatus(Constant.RETURNBOOKS);
        return borrowingRecordsMapper.returnBooks(borrowingRecords) ;
    }



}
