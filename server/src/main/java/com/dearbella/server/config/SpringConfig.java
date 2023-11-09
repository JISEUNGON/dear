package com.dearbella.server.config;

import com.dearbella.server.repository.HospitalRepository;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.repository.TokenRepository;
import com.dearbella.server.service.hospital.HospitalService;
import com.dearbella.server.service.hospital.HospitalServiceImpl;
import com.dearbella.server.service.member.MemberService;
import com.dearbella.server.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final HospitalRepository hospitalRepository;

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository, tokenRepository);
    }

    @Bean
    public HospitalService hospitalService() {
        return new HospitalServiceImpl(hospitalRepository);
    }
}
