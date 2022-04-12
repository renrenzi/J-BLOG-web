import com.jj.jblog.JenBlogMain;
import com.jj.jblog.controller.front.BlogViewController;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 任人子
 * @date 2022/4/11  - {TIME}
 */
@SpringBootTest(classes = {JenBlogMain.class})
public class BlogViewControllerTest {
    @Resource
    private BlogViewController blogViewController;

    @org.junit.jupiter.api.Test
    public void test(){

    }
}
