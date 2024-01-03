package com.oracle.projectGo.type;

import java.util.Arrays;

public enum UsersRoleType {
    ANONYMOUS("ANONYMOUS","0"),
    ADMIN("ADMIN","1"),
    EDUCATOR("EDUCATOR", "2"),
    STUDENT("STUDENT", "3"),
    NORMAL("NORMAL", "4");

    private String label;
    private String value;

    UsersRoleType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    // value 값을 이용해서 UsersRoleType을 반환하는 메소드
    public static String findLabelByValue(String value) {
        return Arrays.stream(UsersRoleType.values())
                .filter(type -> type.value.equals(value))
                .map(UsersRoleType::getLabel)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
    }

    public static UsersRoleType findByLabel(String label) {
        return Arrays.stream(UsersRoleType.values())
                .filter(type -> type.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid label: " + label));
    }
}

