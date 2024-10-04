package ru.practicum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private Timestamp timestamp;
}
