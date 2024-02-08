package com.mountainstory.project.entity.region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity(name = "location_info")
public class Location {

    @Id
    @Column(name = "LOCATION_ID")
    private int id;

    @Column(name = "LOCATION_PARENT")
    private String locationParent;

    @Column(name = "LOCATION_CHILD")
    private String locationChild;

    @Column(name = "LOCATION_CHILD_DETAIL")
    private String locationChildDetail;

    @Column(name = "NX")
    private int nx;

    @Column(name = "NY")
    private int ny;

    public Location MountainLocation(int id,String locationParent,String locationChild,String locationChildDetail, int nx, int  ny){
        this.id=id;
        this.locationParent=locationParent;
        this.locationChild=locationChild;
        this.locationChildDetail=locationChildDetail;
        this.nx=nx;
        this.ny=ny;
        return this;
    }
}
