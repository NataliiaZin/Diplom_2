package api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {

    private String name;
    @JsonIgnore
    private String accessToken;
    private String email;
    private String password;
}
