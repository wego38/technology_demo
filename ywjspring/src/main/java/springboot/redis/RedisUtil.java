package springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisUtil {
    @Autowired
    RedisTemplate redisTemplate;
//    @Value("${ywj}")
    String test;

    @RequestMapping("test")
    @ResponseBody
    public void test(){
        System.out.println(test+"324fsdfdfdf3f");
        redisTemplate.opsForValue().get("11");
    }

}
