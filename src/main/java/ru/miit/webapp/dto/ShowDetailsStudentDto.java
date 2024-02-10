package ru.miit.webapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShowDetailsStudentDto extends ShowUserDetailsDto {

    private String studentName;

    private String groupName;

}
