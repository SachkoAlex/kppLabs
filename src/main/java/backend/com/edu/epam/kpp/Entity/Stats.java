package backend.com.edu.epam.kpp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.sampled.Line;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Stats {
    int totalAmount;
    int totalAmountOfInvalidInputParams;
    TravelTime minValue;
    TravelTime maxValue;
    TravelTime mostPopularResult;

    public void findMostPopularResult(List<TravelTime> timeList) {
        if (timeList.isEmpty()) return;
        HashMap<TravelTime, Integer> timeMap = new HashMap<>();
        timeList.forEach(a -> {
            if (timeMap.containsKey(a)) {
                timeMap.put(a, timeMap.get(a) + 1);
            } else timeMap.put(a, 1);
        });
        int maxNumber = timeMap.values().stream().max(Integer::compareTo).orElse(1);
        timeMap.forEach((k, v) -> {
            if (v == maxNumber) {
                this.mostPopularResult = k;
            }
        });
    }
}
