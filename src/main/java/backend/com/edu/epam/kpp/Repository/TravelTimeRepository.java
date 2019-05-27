package backend.com.edu.epam.kpp.Repository;

import backend.com.edu.epam.kpp.Entity.TravelTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTimeRepository extends CrudRepository<TravelTime,Long> {

}
