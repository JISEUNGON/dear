package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "infra")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Infra implements Serializable {
    @Id
    @Column(name = "infra_num", columnDefinition = "int")
    private Long infraNum;

    @Column(name = "infra_name", length = 50, columnDefinition = "varchar")
    private String infraName;
}
