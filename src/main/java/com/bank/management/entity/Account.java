package com.bank.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "operations_id")
    private Operations operations;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
