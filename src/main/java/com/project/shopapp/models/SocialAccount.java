package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300,nullable = false,name = "provider")
    private String provider;

    @Column(length = 300,nullable = false,name = "provider_id")
    private String providerId;

    @Column(name="email",length = 150)
    private String email;

    @Column(name="name",length = 150)
    private String name;
}
