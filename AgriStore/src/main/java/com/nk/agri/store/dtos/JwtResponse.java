package com.nk.agri.store.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private UserDto user;
}
