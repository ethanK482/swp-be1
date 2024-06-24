package swp.group2.swpbe.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    @SuppressWarnings("unchecked")
    @Bean
    public Cloudinary getCloudinary(){
          Map config = new HashMap();
            config.put("cloud_name", "dswewjrly");
            config.put("api_key", "165441651593246");
            config.put("api_secret","rTHZkn-myLzt3T4kwvF5lRDEvVQ" );
            config.put("secure", true );
            return new Cloudinary(config);
        }
}
