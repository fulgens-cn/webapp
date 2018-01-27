package cn.fulgens.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private int authId;

    @Column(name = "authority", unique = true, nullable = false)
    private String authority = AuthorityRole.ROLE_USER.getAuthorityRole();

    public Authority() {

    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
