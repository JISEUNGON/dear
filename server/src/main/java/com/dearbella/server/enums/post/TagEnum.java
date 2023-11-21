package com.dearbella.server.enums.post;


import com.dearbella.server.enums.hospital.InfraEnum;

public enum TagEnum {
    HOT(0L),
    Free_Talk(1L),
    Surgery_Questions(2L),
    Hospital_Questions(3L),
    Doctor(4L);

    private Long value;

    TagEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static TagEnum findByValue(Long value) {
        for (TagEnum category : TagEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}
