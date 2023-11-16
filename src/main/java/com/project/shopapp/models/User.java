package com.project.shopapp.models;

import java.util.ArrayList;
import java.util.List;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import jakarta.persistence.*;
// import jakarta.validation.OverridesAttribute.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname",length = 100)
    private String fullName;

    @Column(name = "phone_number",length = 20,nullable = false)
    private String phoneNumber;

    @Column(name = "address",length = 100)
    private String address;

    @Column(name = "password",nullable = false,length = 200)
    private String password;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "face_account_id")
    private int faceAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorrityList = new ArrayList<>();   
        // authorrityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName()));
        authorrityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorrityList;     
    }

    @Override
    public String getUsername() {
        return phoneNumber;
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
