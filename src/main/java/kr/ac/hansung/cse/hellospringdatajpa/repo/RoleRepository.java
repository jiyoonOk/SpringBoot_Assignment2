package kr.ac.hansung.cse.hellospringdatajpa.repo;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // 기본적으로 id를 사용한 CRUD 메서드만 제공되기 때문에 roleName을 사용한 메서드를 추가해줌
    Optional<Role> findByRolename(String rolename);
}