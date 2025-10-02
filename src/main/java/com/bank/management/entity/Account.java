package com.bank.management.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "account")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AccountNumber;
    private String accountType;
    private String saldo;
    private Long usersId;

    public Account(Long id, String accountNumber, String accountType, String currency, Users users, List<Operations> operations) {
    }

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Operations> operations;

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Users> users;
}
