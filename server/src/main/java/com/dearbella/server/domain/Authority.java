package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "authority")
public class Authority {
    @Id
    @Column(name = "authority_name", length = 12, columnDefinition = "varchar")
    @ApiModelProperty(example = "ROLE_USER | ROLE_ADMIN")
    private String authorityName;

}
