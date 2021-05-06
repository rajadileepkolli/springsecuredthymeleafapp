package com.learning.securedapp.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.AbstractController;

@AnalyzeClasses(
        packages = "com.learning.securedapp",
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class NamingConventionTest {

    @ArchTest
    static ArchRule services_should_be_suffixed =
            classes()
                    .that()
                    .resideInAPackage("..service..")
                    .and()
                    .areAnnotatedWith(Service.class)
                    .should()
                    .haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static ArchRule controllers_should_be_suffixed =
            classes()
                    .that()
                    .resideInAPackage("..controller..")
                    .or()
                    .areAnnotatedWith(Controller.class)
                    .or()
                    .areAssignableTo(AbstractController.class)
                    .should()
                    .haveSimpleNameEndingWith("Controller");

    @ArchTest
    static ArchRule classes_named_controller_should_be_in_a_controller_package =
            classes()
                    .that()
                    .resideInAPackage("com.learning.securedapp.web.controllers..")
                    .should()
                    .haveSimpleNameEndingWith("Controller")
                    .andShould()
                    .beAnnotatedWith(Controller.class)
                    .orShould()
                    .beAnnotatedWith(RestController.class);

    @ArchTest
    static ArchRule classes_named_service_should_be_in_a_service_impl_package =
            classes()
                    .that()
                    .resideInAPackage("com.learning.securedapp.web.services.impl..")
                    .should()
                    .haveSimpleNameEndingWith("ServiceImpl")
                    .andShould()
                    .beAnnotatedWith(Service.class);

    @ArchTest
    static ArchRule classes_named_repository_should_be_in_a_repository_package =
            classes()
                    .that()
                    .resideInAPackage("com.learning.securedapp.web.repositories..")
                    .should()
                    .haveSimpleNameEndingWith("Repository")
                    .orShould()
                    .haveSimpleNameEndingWith("RepositoryImpl")
                    .andShould()
                    .beAnnotatedWith(Repository.class);
}
