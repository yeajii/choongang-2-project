package com.oracle.projectGo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DistributionRequestDto {
    private List<Integer> studentIds;
    private List<Integer> homeworkIds;
}
