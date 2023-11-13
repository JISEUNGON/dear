package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Tag {
    @Id
    @Column(name = "tag_id", columnDefinition = "int")
    private Long tagId;

    @Column(name = "tag_name", length = 20, columnDefinition = "varchar")
    private String tagName;
}
