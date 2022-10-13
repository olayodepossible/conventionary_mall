package com.possible.vendorservice.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class Vendor {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    @Indexed(unique = true)
    private String email;
    private String companyName;
    private String companyLogo;
    private String desc;
    private Gender gender;
    private Address companyAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
