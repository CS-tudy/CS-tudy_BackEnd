package com.cstudy.moduleapi.application.member.impl;

import com.cstudy.modulecommon.domain.member.Member;
import com.cstudy.modulecommon.error.member.NotFoundMemberEmail;
import com.cstudy.modulecommon.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final RedisTemplate<String, Member> redisTemplate;
    private final MemberRepository memberRepository;
    private final static Duration Member_Cache_TTL = Duration.ofDays(3);

    public void setMember(Member member) {
        String key = getKey(member.getEmail());
        Member existingMember = redisTemplate.opsForValue().get(key);

        if (existingMember == null || !existingMember.getPassword().equals(member.getPassword())) {
            log.info("Set Member to Redis: {}, {}", key, member);
            redisTemplate.opsForValue().set(key, member);
        }
    }

    public void updateMember(Member member) {
        String key = getKey(member.getEmail());
        Member existingMember = redisTemplate.opsForValue().get(key);
        assert existingMember != null;
        Objects.requireNonNull(existingMember, "멤버를 찾을 수 없습니다.");
        existingMember.changePassword(member.getPassword());

        redisTemplate.opsForValue().set(key, existingMember);
    }

    public Optional<Member> getMember(String memberEmail) {
        String key = getKey(memberEmail);
        Member member = redisTemplate.opsForValue().get(key);
        log.info("Get Redis Key:{}, {}", key, member);
        return Optional.ofNullable(member);
    }

    private String getKey(String memberEmail) {
        return "Member:" + memberEmail;
    }

    public void deleteMember(Member member) {
        String memberEmail = member.getEmail();
        String key = getKey(memberEmail);
        Boolean isDeleted = redisTemplate.delete(key);
        log.info("Delete Redis Key: {}, Deleted: {}", key, isDeleted);
    }


}