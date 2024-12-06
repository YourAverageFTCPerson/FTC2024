import static org.firstinspires.ftc.teamcode.ConceptViperSlide.REVOLUTIONS_FOR_FULL_EXTENSION;
import static org.firstinspires.ftc.teamcode.ConceptViperSlide.USING_223;
import static org.firstinspires.ftc.teamcode.ConceptViperSlide.YELLOW_JACKET_223_PPR;
import static org.firstinspires.ftc.teamcode.ConceptViperSlide.YELLOW_JACKET_312_PPR;

import org.junit.jupiter.api.Test;

public class TestTest {
    @Test
    public void function() {
        System.out.println("Full extension: " + REVOLUTIONS_FOR_FULL_EXTENSION * (USING_223 ? YELLOW_JACKET_223_PPR : YELLOW_JACKET_312_PPR));
    }
}
