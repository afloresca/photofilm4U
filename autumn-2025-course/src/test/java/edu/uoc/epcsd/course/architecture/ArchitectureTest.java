package edu.uoc.epcsd.course.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class ArchitectureTest {

    private static final String BASE_PACKAGE = "edu.uoc.epcsd.course";

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE_PACKAGE);

    /**
     * Controllers must not access infrastructure directly
     */
    @Test
    void controllers_should_not_depend_on_infrastructure() {
        ArchRule rule = noClasses()
                .that().areAnnotatedWith(RestController.class)
                .should().dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        rule.check(classes);
    }

    /**
     * Domain repositories are pure ports (no Spring, no infrastructure)
     */
    @Test
    void domain_repositories_should_be_independent() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain.repository..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "..infrastructure.."
                );

        rule.check(classes);
    }

    /**
     * Infrastructure must not be accessed directly by controllers
     */
    @Test
    void infrastructure_should_not_be_accessed_by_controllers() {
        ArchRule rule = noClasses()
                .that().areAnnotatedWith(RestController.class)
                .should().accessClassesThat()
                .resideInAPackage("..infrastructure..");

        rule.check(classes);
    }
}
