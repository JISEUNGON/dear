package com.dearbella.server.dto.response.review;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String title;
    private Float rate;

    @Override
    public int hashCode() {
        return this.reviewId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReviewResponseDto) {
            ReviewResponseDto reviewResponseDto = (ReviewResponseDto) obj;

            return this.hashCode() == reviewResponseDto.hashCode();
        }

        return false;
    }
}
