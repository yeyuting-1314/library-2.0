package library.system.controller;

import library.base.Result;
import library.system.dto.SysBook;
import library.system.dto.SysUser;
import library.system.service.UserService;
import library.util.BaseEnums;
import library.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yeyuting
 * @create 2021/2/25
 */
@RestController
@RequestMapping("sys/user")
public class UserController {

    @Autowired
    UserService userService ;

    //用户注册
    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody SysUser sysUser){
        Result result = Results.successWithData(
                userService.RegisterUser(sysUser) , BaseEnums.SUCCESS.code() , BaseEnums.SUCCESS.desc()) ;
        return result ;
    }

    //用户登陆
    @RequestMapping("/login")
    public Result Login(@RequestBody SysUser sysUser){
        Result result = Results.successWithData
                (userService.Login(sysUser), BaseEnums.SUCCESS.code() , BaseEnums.SUCCESS.desc()) ;
        return  result ;
    }

    //用户角色 借阅书本
    @PostMapping("/borrowBooks")
    public Result borrowBooks(@RequestParam("bookCode") String bookCode ,
                              HttpServletRequest request) {
        Result result = Results.successWithData(userService.borrowBooks(bookCode ,request)) ;
        return result ;
    }

    //用户角色 还书
    @PostMapping("returnBooks")
    public Result returnBooks (@RequestParam("bookCode") String bookCode ,
                               HttpServletRequest request){
        Result result = Results.successWithData(userService.returnBooks(bookCode,request)) ;
        return result ;
    }

    //用户角色 查询所有书本
    @GetMapping("/selectAllBooks")
    public Result selectAllBooks(){
        Result result = Results.successWithData(userService.selectAllBooks()) ;
        return  result ;
    }

    //超级管理员权限  新增一本书本
    @PostMapping("/insertOneBook")
    public Result insertOneBook(@RequestBody SysBook sysBook){
        Result result = Results.successWithData(userService.insertOneBook(sysBook));
        return  result ;
    }

    //超级管理员  新增若干本书本
    @PostMapping("insertManyBooks")
    public  Result insertManyBooks(@RequestBody List<SysBook> sysBooks){
        Result result = Results.successWithData(userService.insertManyBooks(sysBooks)) ;
        return result ;
    }

    //管理员权限  查看借阅记录
    @GetMapping("/selectAllBorrowingRecords")
    public  Result selectAllBorrowingRecords(){
        Result result = Results.successWithData(userService.selectAllBorrowingRecords())  ;
        return  result ;
    }

}
