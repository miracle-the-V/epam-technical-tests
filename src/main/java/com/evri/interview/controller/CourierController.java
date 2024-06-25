package com.evri.interview.controller;

import com.evri.interview.model.Courier;
import com.evri.interview.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CourierController {

    private CourierService courierService;

    @GetMapping("/couriers")
    public ResponseEntity<List<Courier>> getAll(
            @RequestParam(defaultValue = "false") boolean isActive) {
        if (isActive) {
            return ResponseEntity.ok(courierService.getActiveCouriers());
        } else {
            return ResponseEntity.ok(courierService.getAllCouriers());
        }
    }

    @PutMapping("/couriers")
    public ResponseEntity<Void> add(
            @RequestBody Courier courier,
            @RequestParam long courierId) {
        Courier newCourier = courier.toBuilder().id(courierId).build();
        if (courierService.courierExists(courierId)) {
            courierService.addCourier(newCourier);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
