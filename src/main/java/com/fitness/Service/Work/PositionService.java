package com.fitness.Service.Work;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionService {
    public List<Position> getPositions() {
        return new ArrayList<>(Arrays.asList(
                new Position("Բոքսի մարզիչ"),
                new Position("Մարզիչ"),
                new Position("Մերսող")
        ));
    }
    //@TODO
}
