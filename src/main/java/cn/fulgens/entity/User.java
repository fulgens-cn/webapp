package cn.fulgens.entity;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uid;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "${NotEmpty.user.username}")
    // @Size(min = 6, max = 15, message = "{username.size}")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "${NotEmpty.user.password}")
    // @Size(min = 6, max = 15, message = "{password.size}")
    private String password;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "${NotEmpty.user.email}")
    @Email(message = "{email.valid}")
    private String email;

    @Column(name = "enabled", nullable = false)
    @NotEmpty(message = "${NotEmpty.user.enabled}")
    private int enabled;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "auth_id")})
    private Set<Authority> authorities = new HashSet<>();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }
}
