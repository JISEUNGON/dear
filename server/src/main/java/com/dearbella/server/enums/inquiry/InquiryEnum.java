package com.dearbella.server.enums.inquiry;

import com.dearbella.server.enums.hospital.InfraEnum;

public enum InquiryEnum {
    Contour(0L),
    Eyes(1L),
    Lifting(2L),
    Breast(3L),
    Nose(4L);

    private Long value;

    InquiryEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static InquiryEnum findByValue(Long value) {
        for (InquiryEnum category : InquiryEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}
