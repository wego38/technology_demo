package springboot.disruptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class TestDisruptor {

    @Autowired
    NotifyServiceImpl notifyService;

    @GetMapping("testDisruptor")
    @ResponseBody
    public String testLog() {
        log.info("=============");
        notifyService.sendNotify("Hello,World!");
        return "hello,world";
    }
}
