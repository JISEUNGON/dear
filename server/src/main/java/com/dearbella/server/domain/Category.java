package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name = "category_num", columnDefinition = "int")
    private Long categoryNum;

    @Column(name = "category_name", length = 50, columnDefinition = "varchar")
    private String categoryName;
}
