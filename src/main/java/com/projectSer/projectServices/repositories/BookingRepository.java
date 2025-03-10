package com.projectSer.projectServices.repositories;

import com.projectSer.projectServices.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser_Id(int id);

}
