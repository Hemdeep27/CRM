package com.example.CRM.dto;


import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {

    private String support;
    private List<String> comments;
}
