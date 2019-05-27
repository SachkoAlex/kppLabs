package backend.com.edu.epam.kpp.Cache;

import backend.com.edu.epam.kpp.Entity.Input;
import backend.com.edu.epam.kpp.Entity.ResultCollection;
import backend.com.edu.epam.kpp.Entity.TravelTime;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Data
@Component
public class Cache {



   private Map<Input, TravelTime> map = new HashMap<>();

   private Map<Long, Future<ResultCollection>> resultCollectionMap= new HashMap<>();

    public Cache() {
    }

    public long generateId() {
        return new Random().nextLong();
    }

    public void add(Input input, TravelTime entity){
        map.put(input, entity);

    }


    public boolean checkInput(Input input) {
        return this.map.containsKey(input);
    }

    public TravelTime findByInput(Input input) {
        return this.map.get(input);
    }


    public void addResultToMap(Future<ResultCollection> future, Long id) {
        resultCollectionMap.put(id, future);
    }
}
