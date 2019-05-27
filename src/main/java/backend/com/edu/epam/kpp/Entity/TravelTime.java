package backend.com.edu.epam.kpp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "kpp")
public class TravelTime implements Comparable<TravelTime>{

    @javax.persistence.Id
    private int id;
    @Column(name = "distance")
    private double distance;
    @Column(name = "speed")
    private double speed;
    @Column(name = "time")
    private double time;


    public void setDistance(double distance)
    {
        this.distance=distance;
    }

    public void setSpeed(double speed)
    {
        this.speed=speed;
    }

    public void setTime()
    {
        time=distance/speed;
    }



    @Override
    public  boolean equals(Object obj)
    {
        if(this==obj)
        {
            return true;
        }
        if(obj==null || getClass()!=obj.getClass())
        {
            return false;
        }
        TravelTime that =(TravelTime)obj;
        return Double.compare(that.speed,speed)==0 &&
                Double.compare(that.distance,distance)==0;
    }

    @Override
    public int compareTo(TravelTime obj) {
        return Double.compare(this.speed, obj.speed);
    }

}
