package cn.fulgens.service;

import cn.fulgens.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    @CachePut(value = "UserCache", key = "#result.uid")
    User register(User user);

    @Cacheable(value = "UserCache")
    User getById(String id);

    @CacheEvict(value = "UserCache")
    void remove(String id);

    Page<User> getUserList(Pageable pageable);

}
