package com.dearbella.server.enums.doctor;

public enum CategoryEnum {
    Entire(0L),
    Nose(1L),
    Eye(2L),
    Contour(3L),
    Breast(4L),
    Filer(5L),
    Botox(6L),
    Lifting(7L),
    FatProcedure(8L),
    Skin(9L),
    Etc(10L);

    private Long value;

    CategoryEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static CategoryEnum findByValue(Long value) {
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}