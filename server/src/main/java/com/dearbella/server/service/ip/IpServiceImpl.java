package com.dearbella.server.service.ip;

import com.dearbella.server.domain.MemberIp;
import com.dearbella.server.repository.MemberIpRepository;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class IpServiceImpl implements IpService {
    private final MemberIpRepository memberIpRepository;

    @Override
    public MemberIp getIp() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = req.getRemoteAddr();
        }

        return memberIpRepository.save(
                MemberIp.builder()
                        .memberId(JwtUtil.getMemberId())
                        .ip(ip)
                        .build()
        );
    }
}
