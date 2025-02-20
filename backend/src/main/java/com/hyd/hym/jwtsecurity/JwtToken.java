package com.hyd.hym.jwtsecurity;

public record JwtToken(String token, String createdAt, String expireAt) {

}
