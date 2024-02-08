package com.mountainstory.project.repository.region;

import com.mountainstory.project.entity.region.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long>,LocationCustom {


}
