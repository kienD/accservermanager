package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class User {
    @Id
    public String id;

    public String username;
    public String password;
}
