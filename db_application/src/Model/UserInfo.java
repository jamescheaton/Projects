package Model;

public class UserInfo {
    public static String username;
    public static String email;
    public enum userType{
        VISITOR, STAFF, ADMIN
    };
    public static userType role;
}
