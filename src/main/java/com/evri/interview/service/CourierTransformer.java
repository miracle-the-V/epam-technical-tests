package com.evri.interview.service;

import com.evri.interview.model.Courier;
import com.evri.interview.repository.CourierEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourierTransformer {

    private static final String NAME_SPLIT_TOKEN = " ";

    public CourierEntity toCourierEntity(Courier courier) {
        String[] nameTokens = courier.getName().split(NAME_SPLIT_TOKEN);
        return CourierEntity.builder()
                .id(courier.getId())
                .firstName(nameTokens[0])
                .lastName(nameTokens[1])
                .active(courier.isActive())
                .build();
    }

    public Courier toCourier(CourierEntity entity) {
        return Courier.builder()
                .id(entity.getId())
                .name(String.format("%s%s%s", entity.getFirstName(), NAME_SPLIT_TOKEN, entity.getLastName()))
                .active(entity.isActive())
                .build();
    }

    public List<Courier> toCouriers(List<CourierEntity> courierEntities) {
        return courierEntities.stream()
                .map(this::toCourier)
                .collect(Collectors.toList());
    }

}
