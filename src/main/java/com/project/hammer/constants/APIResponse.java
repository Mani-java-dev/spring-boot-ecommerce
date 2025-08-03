package com.project.hammer.constants;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String status;
    private String message;
    private Object data;

    public APIResponse(String status,String message){
        this.status=status;
        this.message=message;
    }
}
