package com.example.DoAnAngular.RequestResponse;


import com.example.DoAnAngular.Entities.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
//    private Long id;
    private String token;
    private String type = "Bearer";
    private Long id;
    private RoleName role;

    public JwtResponse(String token, Long id, RoleName role){
        this.token = token;
        this.id = id;
        this.role = role;
    }

}
