package com.fitness.Service.Work;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionService {
    public List<Position> getPositions() {
        return new ArrayList<>(Arrays.asList(
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մարզիչ", new Service(1, "Մարզում", 1000)),
                new Position("Մերսող", new Service(1, "Մերսում", 1200)),
                new Position("abcd", new Service(1, "abcd", 1000)),
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մերսող", new Service(1, "Մերսում", 1200)),
                new Position("abcd", new Service(1, "abcd", 1000)),
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մերսող", new Service(1, "Մերսում", 1200)),
                new Position("abcd", new Service(1, "abcd", 1000)),
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մերսող", new Service(1, "Մերսում", 1200)),
                new Position("abcd", new Service(1, "abcd", 1000)),
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մերսող", new Service(1, "Մերսում", 1200)),
                new Position("abcd", new Service(1, "abcd", 1000)),
                new Position("Բոքսի մարզիչ", new Service(1, "Բոքս", 1200)),
                new Position("Մարզիչ", new Service(1, "Մարզում", 1000))
        ));
    }
    //@TODO
}
