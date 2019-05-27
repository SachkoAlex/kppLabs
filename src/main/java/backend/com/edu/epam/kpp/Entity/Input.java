package backend.com.edu.epam.kpp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Input {

    long id;

    double distance;

    double speed;

    public Input( double distance,double speed)
    {
    this.distance=distance;
    this.speed=speed;
    }
}