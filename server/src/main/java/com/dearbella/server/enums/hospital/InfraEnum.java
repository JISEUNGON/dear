package com.dearbella.server.enums.hospital;

public enum InfraEnum {
    Entire(0L),
    CCTV(1L),
    Recovery_Room(2L),
    Hospital_Room(3L),
    Concierge_Team(4L);

    private Long value;

    InfraEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static InfraEnum findByValue(Long value) {
        for (InfraEnum category : InfraEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}
