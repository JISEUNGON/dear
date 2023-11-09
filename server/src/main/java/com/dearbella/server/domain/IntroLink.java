package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "intro_link")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntroLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id", columnDefinition = "int")
    private Long linkId;

    @Column(name = "link_url", columnDefinition = "text")
    private String linkUrl;
}
