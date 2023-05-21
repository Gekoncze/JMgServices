package cz.mg.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;

@SuppressWarnings("unused")
public @Test class ServicesTest {
    public static void main(String[] args) {
        System.out.print("Running " + ServicesTest.class.getSimpleName() + " ... ");

        ServicesTest test = new ServicesTest();
        test.testGet();
        test.testGetFailed();

        System.out.println("OK");
    }

    private void testGet() {
        Services.get(TestService.class).service();
    }

    private void testGetFailed() {
        testGetFailed(TestServiceWithoutClassAnnotation.class);
        testGetFailed(TestServiceWithoutInstanceField.class);
        testGetFailed(TestServiceWithoutInstanceFieldAnnotation.class);
        testGetFailed(TestServiceWithIncorrectInstanceFieldType.class);
        testGetFailed(TestServiceWithIncorrectInstanceFieldModifier.class);
        testGetFailed(TestServiceWithoutFieldAnnotation.class);
        testGetFailed(TestServiceWithIncorrectFieldModifier.class);
        Assert.assertNull(TestServiceWithoutClassAnnotation.instance);
        Assert.assertNull(TestServiceWithoutInstanceFieldAnnotation.instance);
        Assert.assertNull(TestServiceWithoutFieldAnnotation.instance);
        Assert.assertNull(TestServiceWithIncorrectFieldModifier.instance);
    }

    private <T> void testGetFailed(@Mandatory Class<T> serviceClass) {
        Assert
            .assertThatCode(() -> Services.get(serviceClass))
            .throwsException();
    }

    public static @Service class TestService {
        private static @Service TestService instance;

        private @Service TestService testService;
        private @Service TestDependencyService testDependencyService;

        public void service() {
            Assert.assertNotNull(instance);
            Assert.assertNotNull(testService);
            Assert.assertNotNull(testDependencyService);
            testDependencyService.service();
        }
    }

    public static @Service class TestDependencyService {
        private static @Service TestDependencyService instance;

        public void service() {
            Assert.assertNotNull(instance);
        }
    }

    public static class TestServiceWithoutClassAnnotation {
        private static @Service TestServiceWithoutClassAnnotation instance;
    }

    public static @Service class TestServiceWithoutInstanceField {
    }

    public static @Service class TestServiceWithoutInstanceFieldAnnotation {
        private static TestServiceWithoutInstanceFieldAnnotation instance;
    }

    public static @Service class TestServiceWithIncorrectInstanceFieldType {
        private static @Service TestDependencyService instance;
    }

    public static @Service class TestServiceWithIncorrectInstanceFieldModifier {
        private @Service TestServiceWithIncorrectInstanceFieldModifier instance;
    }

    public static @Service class TestServiceWithoutFieldAnnotation {
        private static @Service TestServiceWithoutFieldAnnotation instance;
        private TestDependencyService testDependencyService;
    }

    public static @Service class TestServiceWithIncorrectFieldModifier {
        private static @Service TestServiceWithIncorrectFieldModifier instance;
        private static @Service TestDependencyService testDependencyService;
    }
}
