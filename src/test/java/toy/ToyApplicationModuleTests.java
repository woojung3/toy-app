package toy;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
class ToyApplicationModuleTests {

    @Test
    void applicationTest() {
        ApplicationModules modules = ApplicationModules.of(ToyApplication.class).verify();
        modules.forEach(System.out::println);

        new Documenter(modules)
                .writeAggregatingDocument();
    }
}
