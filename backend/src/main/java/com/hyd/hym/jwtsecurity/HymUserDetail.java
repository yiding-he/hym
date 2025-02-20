package com.hyd.hym.jwtsecurity;

import com.hyd.hym.models.HymUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 对 Data Object 的封装
 */
public class HymUserDetail implements UserDetails {

    private final HymUser hymUser;

    public HymUserDetail(HymUser hymUser) {
        this.hymUser = hymUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return hymUser.getPassword();
    }

    @Override
    public String getUsername() {
        return hymUser.getUsername();
    }
}
