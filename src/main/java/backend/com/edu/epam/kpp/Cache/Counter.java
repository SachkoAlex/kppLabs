package backend.com.edu.epam.kpp.Cache;

import backend.com.edu.epam.kpp.Controller.TravelTimeController;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
@Data
@Component
public class Counter {
    private static int count;

    public synchronized int increment()
    {
         return ++count;
    }

}
