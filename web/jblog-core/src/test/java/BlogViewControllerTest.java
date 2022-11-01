import com.jj.jblog.JenBlogMain;
import com.jj.jblog.controller.front.BlogViewController;
import com.jj.jblog.message.MessageConsumer;
import com.jj.jblog.message.MessageProducer;
import com.jj.jblog.pojo.dto.MessageDto;
import com.jj.jblog.util.SensitiveUtil;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 任人子
 * @date 2022/4/11  - {TIME}
 */
@SpringBootTest(classes = {JenBlogMain.class})
public class BlogViewControllerTest {
    @Resource
    private MessageProducer messageProducer;

    @org.junit.jupiter.api.Test
    public void test(){
        messageProducer.sendMessage(new MessageDto());
    }
}
