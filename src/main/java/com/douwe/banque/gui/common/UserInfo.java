package com.douwe.banque.gui.common;

import com.douwe.banque.data.RoleType;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class UserInfo {
    
    private static String username;
    private static int user_id;
    private static RoleType role;
    private static int customer_id;
    private static boolean logged = false;
    

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInfo.username = username;
    }

    public static int getUserId() {
        return user_id;
    }

    public static void setUserId(int user_id) {
        UserInfo.user_id = user_id;
    }

    public static RoleType getRole() {
        return role;
    }

    public static void setRole(RoleType role_id) {
        UserInfo.role = role_id;
    }

    public static boolean isLogged() {
        return logged;
    }

    public static void setLogged(boolean logged) {
        UserInfo.logged = logged;
    }

    public static int getCustomerId() {
        return customer_id;
    }

    public static void setCustomerId(int customer_id) {
        UserInfo.customer_id = customer_id;
    }
}