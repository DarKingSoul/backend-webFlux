package com.mitocode.Security;

import java.util.Date;

public record AuthResponse(
    String token,
    Date expiration
) {
}
