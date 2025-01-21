package toy.config.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toy.config.domain.Member;
import toy.config.domain.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findOne(String username) {
        return memberRepository.findByUsername(username);
    }
}
