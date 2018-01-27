package cn.fulgens.entity;

/**
 * 权限角色枚举类
 */
public enum AuthorityRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DBA("ROLE_DBA");

    private String authorityRole;

    AuthorityRole(String authorityRole) {
        this.authorityRole = authorityRole;
    }

    public String getAuthorityRole() {
        return authorityRole;
    }

}
