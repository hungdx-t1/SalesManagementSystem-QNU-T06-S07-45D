package pika.hungt1.dx.dto;

import lombok.*;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String fullName;
    // optional: role when creating
    private String role = "customer";
}

