package ru.practicum.compilations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.model.Event;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "comp_events")
    private Set<Event> events;

    private Boolean pinned;

    private String title;
}
