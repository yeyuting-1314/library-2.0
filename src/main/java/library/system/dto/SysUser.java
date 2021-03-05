package library.system.dto;

/**
 * @author yeyuting
 * @create 2021/2/25
 */
public class SysUser {

    Integer id ;

    String userName ;

    String userId ;

    String userPassword ;

    String token ;

    String extendedField2 ;

    String extendedField3 ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
