package hexlet.code.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "First name should not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password should not be empty")
    @Size(min = 3, message = "Password should be longer than 3 chars")
    private String password;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Task> authorOfTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "executor")
    private List<Task> executorOfTasks;
}
