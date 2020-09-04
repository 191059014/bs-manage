package junit;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密测试类
 *
 * @version v0.1, 2020/9/4 16:09, create by huangbiao.
 */
public class BCryptPasswordEncoderTest {

    /**
     * 加密
     */
    @Test
    public void encode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

}

    