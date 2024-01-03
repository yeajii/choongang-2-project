package com.oracle.projectGo.type;

/**
 * @brief adsfsdafdsafsdaf
 * @params fdasdfdsfsad
 */
public enum HomeworksEvaluateType {
    NONE("미평가",0),
    POOR("미흡", 1),
    AVERAGE("보통", 2),
    EXCELLENT("우수", 3);

    private String label;
    private int value;

    HomeworksEvaluateType(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}
