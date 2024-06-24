package com.example.DoAnAngular.RequestResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassRequest {
    private String pass;
    private String newPass;
    private String rePass;

}
