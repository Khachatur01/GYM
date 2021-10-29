package com.fitness.Service.Work;

import com.fitness.Model.Work.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceService {
    public Map<Service, Integer> getServiceBySubscriptionId(long id){
        Map<Service, Integer> offers = new HashMap<>();
        offers.put(new Service(1, "offer1", 1000), 5);
        offers.put(new Service(2, "offer2", 1000), 7);
        offers.put(new Service(3, "offer3", 1000), 3);
        offers.put(new Service(4, "offer4", 1000), 2);
        return offers;
    }

    public List<Service> getServices() {
        return new ArrayList<>();
    }
    //@TODO
}
