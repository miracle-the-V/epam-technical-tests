package com.evri.interview.service;

import com.evri.interview.model.Courier;
import com.evri.interview.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourierService {

    private CourierTransformer courierTransformer;
    private CourierRepository repository;

    public List<Courier> getAllCouriers() {
        return courierTransformer.toCouriers(repository.findAll());
    }

    public List<Courier> getActiveCouriers() {
        return courierTransformer.toCouriers(repository.findByActive(true));
    }

    public boolean courierExists(long id) {
       return repository.existsById(id);
    }

    public void addCourier(Courier courier) {
        repository.save(courierTransformer.toCourierEntity(courier));
    }

}
