package com.learning.securedapp.domain;

import com.learning.securedapp.web.validators.ValidEmail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id private String id;

    @Indexed
    @Size(min = 2, max = 30)
    private String userName;

    @Size(min = 2, max = 30)
    private String password;

    @NotNull @ValidEmail private String email;

    private String passwordResetToken;

    @Builder.Default private boolean enabled = false;

    @NotNull
    @Pattern(regexp = "[0-9]{6}")
    private String zip;

    private String address;

    @DBRef(lazy = true)
    @Builder.Default
    private List<Role> roleList = new ArrayList<>();
}
