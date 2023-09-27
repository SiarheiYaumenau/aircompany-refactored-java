import Planes.ExperimentalPlane;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class AirportTest {

    private List<Plane> planes;
    private PassengerPlane planeWithMaxPassengerCapacity;

    @BeforeEach
    public void setUp() {
        planes = List.of(
                new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
                new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
                new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
                new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
                new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
                new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
                new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
                new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
                new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
                new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
                new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
                new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
                new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
                new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
                new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
                new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
        );

        planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);
    }

    @Test
    public void GetTransportMilitaryPlanesTest() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        assertTrue(transportMilitaryPlanes.stream().allMatch(plane -> plane.getType() == MilitaryType.TRANSPORT));
    }

    @Test
    public void GetPassengerPlaneWithMaxCapacityTest() {
        Airport airport = new Airport(planes);
        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        assertEquals(planeWithMaxPassengerCapacity, expectedPlaneWithMaxPassengersCapacity);
    }

    @Test

    public void testSortByMaxLoadCapacity() {
        Airport airport = new Airport(new ArrayList<>(planes));
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();

        assertTrue(IntStream.range(0, planesSortedByMaxLoadCapacity.size() - 1)
                .allMatch(i -> planesSortedByMaxLoadCapacity.get(i).getMaxLoadCapacity() <= planesSortedByMaxLoadCapacity.get(i + 1).getMaxLoadCapacity()));
    }

    @Test
    public void HasAtLeastOneBomberInMilitaryPlanesTest() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();
        assertTrue(bomberMilitaryPlanes.stream().allMatch(plane -> plane.getType() == MilitaryType.BOMBER));
    }

    @Test
    public void ExperimentalPlanesHaveClassificationLevelHigherThanUnclassifiedTest() {
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        assertFalse(experimentalPlanes.stream().anyMatch(plane -> plane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED));
    }
}
