package com.hschoi.homework.common.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hschoi.common.code.UserType;
import com.hschoi.common.code.mapper.EnumFactory;
import com.hschoi.common.code.mapper.EnumValue;
import com.hschoi.homework.common.TestConfig;

/**
 * <pre>
 * com.hschoi.homework.common.code_EnumTest.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class EnumTest {
	
	private EnumFactory enumFactory;
	
	@Before
    public void setup () {
		enumFactory = new EnumFactory();
    }
	
	@Test
    public void 사용자타입_조회() throws Exception {
        
		//given
        final String KEY_USER = "USER";
        enumFactory.put(KEY_USER, UserType.class);

        //when
        List<EnumValue> enumValues = enumFactory.get(KEY_USER);
        //then
        assertThat(enumValues.size()).isEqualTo(2);
    }
	
	@Test
    public void 사용자타입_getAll() throws Exception {
        
		//given
		final String KEY_USER = "USER";
        enumFactory.put(KEY_USER, UserType.class);

        //when
        Map<String, List<EnumValue>> types = enumFactory.getAll();

        //then
        types.get(KEY_USER).forEach(e -> System.out.println(e.toString()));
        
    }
}
