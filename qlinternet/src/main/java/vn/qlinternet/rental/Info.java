package vn.qlinternet.rental;


import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import vn.qlinternet.entity.*;

@XmlRootElement(name = "info")
@XmlAccessorType(XmlAccessType.FIELD)
public class Info implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Computer computer;
    private Guest guest;
    private String rentaltime;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Info() {
    }

    public Info(int id, Computer cpu, Guest guest) {
        super();
        this.id = id;
        this.computer = cpu;
        this.guest = guest;
        this.rentaltime = LocalTime.now().format(fmt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Computer getComputer() {
        return computer;
    }
    
    public void setComputer(Computer cpu) {
        this.computer = cpu;
    }
    
    public Guest getGuest() {
        return guest;
    }
    
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    
    public String getRentalTime() {
        return rentaltime;
    }
    
    public void setRentalTime(String rentaltime) {
        this.rentaltime = rentaltime;
    }
}
