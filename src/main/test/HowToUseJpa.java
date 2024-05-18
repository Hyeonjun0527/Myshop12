
import myshop12.com.model2.mvc.Myshop12Application;
import myshop12.com.model2.mvc.common.console.Color;
import myshop12.com.model2.mvc.test.Entity.User;
import myshop12.com.model2.mvc.test.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static myshop12.com.model2.mvc.common.console.Color.RED;
import static myshop12.com.model2.mvc.common.console.Color.ORANGE;
import static myshop12.com.model2.mvc.common.console.Color.YELLOW;
import static myshop12.com.model2.mvc.common.console.Color.BLUE;
import static myshop12.com.model2.mvc.common.console.Color.GREEN;

import static myshop12.com.model2.mvc.common.console.Color.RESET;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Myshop12Application.class})
public class HowToUseJpa {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        //나는 테이블을 만든거야. 컬럼은 id(자동증가), username, email이 있어.

        User user = new User();
        System.out.println(RED);
        System.out.println("user = " + user);
        System.out.println(user.getUsername());
        System.out.println(RESET);

        //빌더 사용
        User user2 = User.builder()
                .username("hyeonjun")
                .email("hello@email.com")
                .build();
        System.out.println(user2);
        user2.setUsername("hyeonjun2");
        System.out.println(user2);
        userRepository.save(user2);
        System.out.println(userRepository.findByUsername(user2.getUsername()));
//        User alex = new User("Alex", "alex@example.com");
//        userRepository.save(alex);  // 테스트 데이터 저장
//
//        // when
//        User found = userRepository.findByUsername(alex.getUsername());
//
//        // then
//        System.out.println(RED);
//        System.out.println("found = " + found);
//        System.out.println("found.getUserId() = " + found.getId());
//        System.out.println("found.getUsername() = " + found.getUsername());
//        System.out.println("alex.getUsername() = " + alex.getUsername());
//        System.out.println(RESET);
//        assertThat(found.getUsername()).isEqualTo(alex.getUsername());
//
//
//        User hyeonjun = User.builder()
//                .username("hyeonjun")
//                .email("hyeonjun@email.com")
//                .build();
//        userRepository.save(hyeonjun);
//        User found2 = userRepository.findByUsername(hyeonjun.getUsername());
//
//        // then
//        System.out.println(ORANGE);
//        System.out.println("found2 = " + found2);
//        System.out.println("found2.getUserId() = " + found2.getId());
//        System.out.println("found2.getUsername() = " + found2.getUsername());
//        System.out.println("hyeonjun.getUsername() = " + hyeonjun.getUsername());
//        System.out.println(RESET);
//        assertThat(found2.getUsername()).isEqualTo(hyeonjun.getUsername());


    }
}
