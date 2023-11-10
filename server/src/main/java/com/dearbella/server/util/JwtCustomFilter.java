package com.dearbella.server.util;

import com.dearbella.server.domain.Member;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is null");

            return;
        }

        // bearer이 아니면 오류
        if (!authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token does not begin with Bearer String");

            return;
        }

        // Token 꺼내기
        String token = authorizationHeader.split(" ")[1];

        // Token 검증
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is not valid");

            return;
        }

        Long id = JwtUtil.getMemberId();

        final Optional<Member> memberOptional = memberRepository.findById(id);

        //member 검증
        final Member member = memberOptional.orElseThrow(
                () -> new MemberIdNotFoundException(id.toString())
        );

        // Token 만료 체크
        if (JwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is expired");

            return;
        }

        String role;

        if(member.contain("ROLE_ADMIN")) {
            role = "ROLE_ADMIN";
        }
        else if(member.contain("ROLE_USER")) {
            role = "ROLE_USER";
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("Member do not have permission");

            return;
        }

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority(role)));

        // UserDetail을 통해 인증된 사용자 정보를 SecurityContext에 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}