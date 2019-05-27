package backend.com.edu.epam.kpp.Service;


import backend.com.edu.epam.kpp.Cache.Cache;
import backend.com.edu.epam.kpp.Cache.Counter;
import backend.com.edu.epam.kpp.Entity.*;
import backend.com.edu.epam.kpp.Repository.TravelTimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Component
@Slf4j
public class TravelTimeService {

    @Autowired
    private TravelTimeRepository repository;

    private Cache cache;

    private Counter counter;


    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public TravelTimeService(Cache cache,Counter counter)
    {
        this.cache=cache;
        this.counter=counter;

    }

    public int incrementAndGetCounter()
    {
        return(counter.increment());
    }


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("travelTime");
    }



    public Optional<TravelTime> buildTime(List<Double> data)
    {
        log.debug("BuildTime method is called for: {}",data);
        Input input =new Input(data.get(0),data.get(1));
        if(!validateInput(input))
        {
            log.error("Incorrect input");
            return Optional.empty();
        }
        return Optional.of(convertInputToTime(input));
    }


    public boolean validateInput(Input input)
    {
        return(input.getDistance()>0&&input.getSpeed()>0);
    }



    public Long createTimeCollection(InputList inputList)
    {
        long id = cache.generateId();
        log.info("id of request: {}",id);
        Future<ResultCollection> future=processInputList(inputList);
       cache.addResultToMap(future,id);
    return id;
    }

    public TravelTime convertInputToTime(Input input)
    {
        System.out.println(cache.checkInput(input));
        if(cache.checkInput(input))
        {
            return cache.findByInput(input);
        }

        TravelTime time=new TravelTime();
        time.setDistance(input.getDistance());
        time.setSpeed(input.getSpeed());
        time.setTime();
        time=repository.save(time);
        cache.add(input,time);
        log.info("Added to cache");
        return time;
    }

    private Future<ResultCollection> processInputList(InputList inputList) {
        return executorService.submit(() -> {
            ResultCollection collection = new ResultCollection();
            collection.setList(inputList.getInputList().stream().filter(this::validateInput)
                    .map(this::convertInputToTime)
                    .collect(Collectors.toList()));
            Stats stats = new Stats();

            stats.setTotalAmount(collection.getList().size());

            stats.setTotalAmountOfInvalidInputParams(
                    inputList.getInputList().size() - collection.getList().size());

            stats.setMaxValue(collection.getList().stream().max(TravelTime::compareTo).orElse(null));

            stats.setMinValue(collection.getList().stream().min(TravelTime::compareTo).orElse(null));

            stats.findMostPopularResult(collection.getList());
            collection.setStats(stats);
            return collection;
        });
    }


}
