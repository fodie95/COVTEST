package rim.mr.covtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("rim.mr.covtest");

        noClasses()
            .that()
            .resideInAnyPackage("rim.mr.covtest.service..")
            .or()
            .resideInAnyPackage("rim.mr.covtest.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..rim.mr.covtest.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
