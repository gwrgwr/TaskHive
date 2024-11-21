package com.example.taskhive.domain.notification;

import com.example.taskhive.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Users recipient;

    private Boolean read = false;

    private LocalDateTime sentAt;
}
