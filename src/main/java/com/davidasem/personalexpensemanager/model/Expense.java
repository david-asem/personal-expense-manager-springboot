package com.davidasem.personalexpensemanager.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
public class Expense {
        @Id
        @SequenceGenerator(
            name = "expense_sequence",
            sequenceName = "expense_sequence",
            allocationSize = 1
        )
        @GeneratedValue(strategy= GenerationType.SEQUENCE,
        generator = "expense_sequence")
        private Long id;

        private String expenseTitle;

        private String description;

        private BigDecimal amount;


    }



