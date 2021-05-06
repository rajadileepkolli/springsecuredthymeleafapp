package com.learning.securedapp.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.learning.securedapp")
public class LayeredArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture()
                    .layer("Controllers")
                    .definedBy("com.learning.securedapp.web.controllers..")
                    .layer("Services")
                    .definedBy("com.learning.securedapp.web.services.impl..")
                    .layer("Persistence")
                    .definedBy("com.learning.securedapp.web.repositories..")
                    .layer("Bootstrap")
                    .definedBy("com.learning.securedapp.bootstrap..")
                    .layer("Configuration")
                    .definedBy("com.learning.securedapp.configuration..")
                    .whereLayer("Controllers")
                    .mayNotBeAccessedByAnyLayer()
                    .whereLayer("Services")
                    .mayOnlyBeAccessedByLayers("Controllers")
                    .whereLayer("Persistence")
                    .mayOnlyBeAccessedByLayers("Services", "Bootstrap", "Configuration");

    @ArchTest
    static final ArchRule layer_dependencies_are_respected_with_exception =
            layeredArchitecture()
                    .layer("Controllers")
                    .definedBy("com.learning.securedapp.web.controllers..")
                    .layer("Services")
                    .definedBy("com.learning.securedapp.web.services.impl..")
                    .layer("Persistence")
                    .definedBy("com.learning.securedapp.web.repositories..")
                    .layer("Bootstrap")
                    .definedBy("com.learning.securedapp.bootstrap..")
                    .layer("Configuration")
                    .definedBy("com.learning.securedapp.configuration..")
                    .whereLayer("Controllers")
                    .mayNotBeAccessedByAnyLayer()
                    .whereLayer("Services")
                    .mayOnlyBeAccessedByLayers("Controllers")
                    .whereLayer("Persistence")
                    .mayOnlyBeAccessedByLayers("Services", "Bootstrap", "Configuration");
}
