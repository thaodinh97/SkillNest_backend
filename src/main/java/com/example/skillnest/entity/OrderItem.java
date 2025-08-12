package com.example.skillnest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
     Order order;

    @ManyToOne
    @JoinColumn(name = "course_id")
     Course course;

    @Column(nullable = false)
     BigDecimal price;
}
