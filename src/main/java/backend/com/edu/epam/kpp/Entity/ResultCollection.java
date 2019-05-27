package backend.com.edu.epam.kpp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ResultCollection {
    private List<TravelTime> time;
    private Stats stats;


    public List <TravelTime> getList()
    {
        return this.time;
    }

    public void setList(List<TravelTime> time)
    {
        this.time=time;
    }


}
