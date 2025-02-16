package org.javaacademy.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private Integer id;
    private String code;
    @JsonProperty("full_name")
    private String fullName;
    private String sign;
}
