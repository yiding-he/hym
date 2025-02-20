package demo;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void test() {
        var password = "admin123";
        var saltedHashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(saltedHashedPassword);
    }
}
