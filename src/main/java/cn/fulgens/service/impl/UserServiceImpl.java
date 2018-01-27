package cn.fulgens.service.impl;

import cn.fulgens.dao.AuthorityRepository;
import cn.fulgens.dao.UserRepository;
import cn.fulgens.entity.Authority;
import cn.fulgens.entity.AuthorityRole;
import cn.fulgens.entity.User;
import cn.fulgens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public User register(User user) {
        // 设置用户id
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUid(uid);
        // 对密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        // 设置用户可用
        user.setEnabled(1);
        // 设置注册用户权限为ROLE_USER
        String ROLE_USER = AuthorityRole.ROLE_USER.getAuthorityRole();
        Set<Authority> authorities = authorityRepository.findAuthoritiesByAuthorityLike(ROLE_USER);
        if (authorities == null || authorities.size() == 0) {
            authorities = new HashSet<>();
            Authority authority = new Authority(ROLE_USER);
            authorityRepository.save(authority);
            authorities.add(authority);
        }
        user.setAuthorities(authorities);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page<User> getUserList(Pageable pageable) {
        // 使用Example查询忽略用户密码
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("password");
        Example<User> example = Example.of(new User(), exampleMatcher);
        return userRepository.findAll(example, pageable);
    }

    @Override
    public void remove(String id) {
        userRepository.delete(id);
    }
}
