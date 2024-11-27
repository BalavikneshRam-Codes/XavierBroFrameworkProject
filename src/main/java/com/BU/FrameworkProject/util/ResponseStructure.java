package com.BU.FrameworkProject.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResponseStructure {
    int statusCode;
    String message;
}
