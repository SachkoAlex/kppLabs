package backend.com.edu.epam.kpp.Controller;


import backend.com.edu.epam.kpp.Entity.InputList;
import backend.com.edu.epam.kpp.Entity.TravelTime;
import backend.com.edu.epam.kpp.Service.TravelTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/travelTime")
public class TravelTimeController {

 private  TravelTimeService service;


    @Autowired
    public TravelTimeController(TravelTimeService service)
    {
        this.service=service;
    }



    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity createTime(@RequestParam(name = "params") List<String> params) {
        try {
            service.incrementAndGetCounter();
            List<Double> doubles = params.stream().map(Double::valueOf).collect(Collectors.toList());
            log.info("Data: {}", doubles);
            Optional<TravelTime> time = service.buildTime(doubles);
            if (time.isPresent()) {
                log.info("Response body: " + time.get().toString());
                return ResponseEntity.ok(time.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception exception) {
            log.error("Bad params");
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCounter(){

        Integer counter = service.incrementAndGetCounter();
        return ResponseEntity.ok(counter);
    }

    @RequestMapping(value = "/listParam", method = RequestMethod.POST)
    public ResponseEntity buildTimeCollection(@RequestBody InputList inputList) {
        service.incrementAndGetCounter();
        return ResponseEntity.ok(service.createTimeCollection(inputList));
    }



}



