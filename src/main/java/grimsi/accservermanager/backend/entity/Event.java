package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.EventType;
import grimsi.accservermanager.backend.enums.Track;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("event")
@Getter
@Setter
public class Event {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private Track track;
    private EventType eventType;
    private int preRaceWaitingTimeSeconds;
    private int sessionOverTimeSeconds;
    private int ambientTemp;
    private int trackTemp;
    private BigDecimal cloudLevel;
    private BigDecimal rain;
    private BigDecimal weatherRandomness;
    private List<Session> sessions;
    private int configVersion = 1;
}
