package com.rizomm.filemanager.entities.utilities;


import com.rizomm.filemanager.entities.Role;
import com.rizomm.filemanager.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomUser extends User implements UserDetails {

    public CustomUser(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getListOfCustomGrantedAuthority();
    }

    private List<SimpleGrantedAuthority> getListOfCustomGrantedAuthority() {
        return listOfMappingRoles();
    }

    private List<SimpleGrantedAuthority> listOfMappingRoles() {
        return getRoles()
                .stream()
                .map((CustomUser::getCustomGrantedAuthorityWithRole))
                .collect(toList());
    }

    private static SimpleGrantedAuthority getCustomGrantedAuthorityWithRole(Role role) {
        return new SimpleGrantedAuthority(role.getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
