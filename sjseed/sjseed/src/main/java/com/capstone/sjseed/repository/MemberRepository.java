package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
