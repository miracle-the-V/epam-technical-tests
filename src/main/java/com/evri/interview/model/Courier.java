package com.evri.interview.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Courier {
    long id;
    String name;
    boolean active;
}
