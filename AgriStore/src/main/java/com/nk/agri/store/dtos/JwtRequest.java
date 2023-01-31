package com.nk.agri.store.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtRequest
{
    private String email;
    private String password;

}
