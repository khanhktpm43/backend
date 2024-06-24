package com.example.DoAnAngular.RequestResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    public String status;
    public String message;
    public Object data;

}
