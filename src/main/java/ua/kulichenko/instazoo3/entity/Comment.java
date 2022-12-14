package ua.kulichenko.instazoo3.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "text", nullable = false)
    private String massage;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreateDate() {
        this.createdDate = LocalDateTime.now();
    }
}
