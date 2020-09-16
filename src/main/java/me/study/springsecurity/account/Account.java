package me.study.springsecurity.account;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
public class Account {
    @Id @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String username;
    private Integer age;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
