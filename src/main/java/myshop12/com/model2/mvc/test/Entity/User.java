package myshop12.com.model2.mvc.test.Entity;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Getter
//@AllArgsConstructor id는 설정하지 않아야 하니까
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    @Column
    private String username;

    @Setter
    @NonNull
    @Column
    private String email;

    @Builder
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    //
}
