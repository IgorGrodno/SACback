package com.example.sacBack.models.ntities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat
    private Date startDate;

    @JsonFormat
    private Date endDate;

    private Boolean active;

    @ElementCollection
    @CollectionTable(name = "session_student_numbers", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "student_number")
    private List<Integer> studentNumbers = new ArrayList<>();
}
