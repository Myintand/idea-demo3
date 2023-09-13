import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.example.demo3.config.WebConfiguration;
import com.example.demo3.pojo.User;
import com.example.demo3.service.UserService;
import com.example.demo3.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class mytest {

        // 创建 OSSClient 实例
        String endpoint = "https://zqy-287189.oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tSC2jqvERCtYKRvMeSX";
        String accessKeySecret = "kWQnvih6FSATmiIKqvIdoB3gMFHcVQ";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

}
