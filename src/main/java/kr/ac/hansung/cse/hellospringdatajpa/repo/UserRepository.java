package kr.ac.hansung.cse.hellospringdatajpa.repo;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    // 기본적으로 id를 사용한 CRUD 메서드만 제공되기 때문에 email을 사용한 메서드를 추가해줌
    Optional<User> findByEmail(String email);
}