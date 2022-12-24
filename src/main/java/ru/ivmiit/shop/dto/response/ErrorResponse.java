package ru.ivmiit.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private Integer status;

    private String message;

    private String path;

    private String exceptionName;

    private Instant timeStamp;

}
