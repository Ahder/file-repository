package com.rizomm.filemanager.business.entites;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    @NonNull
    private String name;

    @Email
    private String email;

}
