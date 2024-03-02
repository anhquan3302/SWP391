package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@Builder
public class BookingDesign {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "booking_id")
        private int bookingId;

        @Column(name = "date_time")
        private LocalDateTime dateTime;

        @Column(name = "design_description")
        private String design_description;

        @Column(name = "meeting_time")
        private String meetingTime;

        @Column(name = "meeting_date")
        private String meetingDate;


        @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
        private List<DesignProjects> designProjects;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;



}



