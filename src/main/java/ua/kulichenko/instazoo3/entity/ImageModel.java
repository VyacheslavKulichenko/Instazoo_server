package ua.kulichenko.instazoo3.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] imageBytes;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long postId;

}
