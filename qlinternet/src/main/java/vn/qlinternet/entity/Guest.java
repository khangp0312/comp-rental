package vn.qlinternet.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "guest")
@XmlAccessorType(XmlAccessType.FIELD)
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int point;

    public Guest() {
    }

    public Guest(int id, String name, int point) {
        super();
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getPoint() {
        return point;
    }
    
    public void setPoint(int point) {
        this.point = point;
    }
    
    public void incPoint(int point) {
        this.point += point;
    }
}