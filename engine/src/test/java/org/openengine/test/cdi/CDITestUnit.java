package org.openengine.test.cdi;

import org.junit.jupiter.api.*;
import org.openengine.cdi.CDIException;
import org.openengine.cdi.Injector;
import org.openengine.test.mock.CDIModuleNoOverride;
import org.openengine.test.mock.CDIModuleOverridable;
import org.openengine.test.mock.CDIModuleNonSingleton;
import org.openengine.test.mock.CDIModuleWithNoProviders;

class CDITestUnit {

    @Nested
    @DisplayName("Injector")
    public class InjectorTest {

        @BeforeEach
        void setup(){
            Injector.reset();
        }

        @Test
        void whenRegisterThenShouldBeOkay() {
            Injector.register(CDIModuleOverridable.class);
        }

        @Test
        void whenRegisterTryForbiddenOverrideShouldThrowException() {
            Injector.register(CDIModuleNoOverride.class);

            Assertions.assertEquals(
                    "Module [org.openengine.test.mock.CDIModuleNoOverride] does not allow override of provider [java.lang.Integer integer()]",
                    Assertions.assertThrows(
                            CDIException.class,
                            () -> Injector.register(CDIModuleNoOverride.class)
                    ).getMessage()
            );
        }

        @Test
        void whenRegisterNullThenShouldThrowException() {
            Assertions.assertEquals(
                    "Module is null",
                    Assertions.assertThrows(
                            CDIException.class,
                            () -> Injector.register(null)
                    ).getMessage()
            );
        }

        @Test
        void whenRegisterWithNoProviderThenShouldThrowException() {
            Assertions.assertEquals(
                    "No provider annotated method found",
                    Assertions.assertThrows(
                            CDIException.class,
                            () -> Injector.register(CDIModuleWithNoProviders.class)
                    ).getMessage()
            );
        }

        @Test
        void whenCreateUnknownInstanceThenShouldThrowException() {
            Assertions.assertEquals(
                    "Unknow target [java.lang.String]",
                    Assertions.assertThrows(
                            CDIException.class,
                            () -> Injector.inject(String.class)
                    ).getMessage()
            );
        }

        @Test
        void whenCreateKnownInstanceThenShouldBeOkay() {
            Injector.register(CDIModuleNoOverride.class);
            final var instance = Injector.inject(Integer.class);
            Assertions.assertNotNull(instance);
            Assertions.assertEquals(0, instance);

            final var second = Injector.inject(Integer.class);
            Assertions.assertEquals(instance.hashCode(), second.hashCode());
        }

        @Test
        void whenCreateNonSingletonThenShouldHaveDifferentHashes() throws Exception{
            Injector.register(CDIModuleNonSingleton.class);
            final var s1 = Injector.inject(String.class);
            Assertions.assertNotNull(s1);
            final var s2 = Injector.inject(String.class);
            Assertions.assertNotNull(s2);
            Assertions.assertNotSame(s1, s2);

        }
    }

}
