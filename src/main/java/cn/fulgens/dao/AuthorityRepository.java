package cn.fulgens.dao;

import cn.fulgens.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Set<Authority> findAuthoritiesByAuthorityLike(String authority);

}
