package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "career")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id", columnDefinition = "int")
    private Long careerId;

    @Column(name = "career_name", length = 100, columnDefinition = "varchar")
    private String careerName;

    @Column(name = "career_date", columnDefinition = "date")
    private LocalDate careerDate;
}
