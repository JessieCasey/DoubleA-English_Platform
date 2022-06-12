package com.doubleA.platform.payloads;

import lombok.Data;

@Data
public class LessonDTO {
    private String title;
    private String description;
    private String time;
    private String level;
    private String type;
}
