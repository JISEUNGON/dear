package com.dearbella.server.dto.response.review;

import com.dearbella.server.domain.Category;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewAddResponseDto {
    private Long reviewId;
    private String memberName;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private Long likeNum;
    private List<String> befores;
    private List<String> afters;

    private Float totalRate;
    private Long doctorId;
    private String doctorName;
    private String doctorImage;
    private String doctorIntro;
    private String hospitalName;
    private Float doctorReviewRate;
    private List<Category> parts;

    private List<Comment> comments;

    private Long hospitalId;
    private String hospitalLocation;
    private Float hospitalReviewRate;
}
