package myshop12.com.model2.mvc.test.Repository;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@NonNull
@Getter
@EqualsAndHashCode
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    @Column(nullable = false)
    private String userName;

    @Setter
    @NonNull
    @Column(nullable = false)
    private String email;

    @Builder
    public User2(String userName,String email){
        this.userName = userName;
        this.email = email;
    }
}