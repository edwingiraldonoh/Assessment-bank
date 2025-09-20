package com.bank.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operations")
public class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String numberAccount;

    @OneToMany(mappedBy = "operations",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Users> users;

    @OneToMany(mappedBy = "operations",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Account> account;
}
