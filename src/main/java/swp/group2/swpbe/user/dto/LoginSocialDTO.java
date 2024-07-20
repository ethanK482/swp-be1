package swp.group2.swpbe.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginSocialDTO {
    private String name;
    private String picture;
    private String sid;
    private String email;

}
