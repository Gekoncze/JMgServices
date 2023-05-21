package cz.mg.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class ServicesSynchronizationTest {
    private static final int ITERATIONS = 5000;

    public static void main(String[] args) {
        System.out.print("Running " + ServicesSynchronizationTest.class.getSimpleName() + " ... ");

        ServicesSynchronizationTest test = new ServicesSynchronizationTest();
        test.testSynchronization();

        System.out.println("OK");
    }

    private void testSynchronization() {
        int errorCount = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            try {
                TestThread firstThread = new TestThread();
                TestThread secondThread = new TestThread();
                firstThread.start();
                secondThread.start();
                firstThread.join();
                secondThread.join();
                if (firstThread.getService() != secondThread.getService()) {
                    errorCount++;
                }
                TestService.instance = null;
            } catch (InterruptedException e) {
                throw new IllegalStateException("Unexpected interrupt.");
            }
        }

        Assert
            .assertThat(0, errorCount)
            .withMessage("Errors " + errorCount + " out of " + ITERATIONS + ".")
            .areEqual();
    }

    public static @Service class TestService {
        public static @Service TestService instance;
    }

    private static class TestThread extends Thread {
        private TestService service = null;

        @Override
        public void run() {
            service = Services.get(TestService.class);
        }

        public TestService getService() {
            return service;
        }
    }
}
