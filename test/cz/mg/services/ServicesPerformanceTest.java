package cz.mg.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;

public @Test class ServicesPerformanceTest {
    private static final int ROUNDS = 3;
    private static final int ITERATIONS = 1000000;

    public static void main(String[] args) {
        System.out.println("Running " + ServicesPerformanceTest.class.getSimpleName() + " ... ");

        ServicesPerformanceTest test = new ServicesPerformanceTest();

        for (int i = 0; i < ROUNDS; i++) {
            System.out.println();

            test.testFrameworkPerformance();
            test.testSynchronizedPerformance();
            test.testSingletonPerformance();

            System.out.println();
        }

        System.out.println("OK");
    }

    private void testFrameworkPerformance() {
        Services.get(ServiceOne.class);

        long time = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            Services.get(ServiceOne.class).service();
            //ServiceOne.instance = null;
        }

        System.out.println("Framework " + (System.currentTimeMillis() - time) + " ms. (" + ServiceOne.count + ")");
        ServiceOne.count = 0;
    }

    private void testSynchronizedPerformance() {
        ServiceOne.getInstanceSynchronized();

        long time = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ServiceOne.getInstanceSynchronized().service();
            //ServiceOne.volatileInstance = null;
        }

        System.out.println("Synchronized " + (System.currentTimeMillis() - time) + " ms. (" + ServiceOne.count + ")");
        ServiceOne.count = 0;
    }

    private void testSingletonPerformance() {
        ServiceOne.getInstance();

        long time = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ServiceOne.getInstance().service();
            //ServiceOne.instance = null;
        }

        System.out.println("Singleton " + (System.currentTimeMillis() - time) + " ms. (" + ServiceOne.count + ")");
        ServiceOne.count = 0;
    }

    private static @Service class ServiceOne {
        private static @Service ServiceOne instance;
        private static volatile @Service ServiceOne volatileInstance;
        private static int count;

        public static ServiceOne getInstance() {
            if (instance == null) {
                instance = new ServiceOne();
                instance.serviceTwo = ServiceTwo.getInstance();
                instance.serviceThree = ServiceThree.getInstance();
            }
            return instance;
        }

        public static ServiceOne getInstanceSynchronized() {
            if (volatileInstance == null) {
                synchronized (Service.class) {
                    if (volatileInstance == null) {
                        volatileInstance = new ServiceOne();
                        volatileInstance.serviceTwo = ServiceTwo.getInstanceSynchronized();
                        volatileInstance.serviceThree = ServiceThree.getInstanceSynchronized();
                    }
                }
            }
            return volatileInstance;
        }

        private @Service ServiceTwo serviceTwo;
        private @Service ServiceThree serviceThree;

        private ServiceOne() {
        }

        public void service() {
            count++;
            serviceTwo.service();
            serviceThree.service();
        }
    }

    private static @Service class ServiceTwo {
        private static @Service ServiceTwo instance;
        private static volatile @Service ServiceTwo volatileInstance;
        private static int count;

        public static ServiceTwo getInstance() {
            if (instance == null) {
                instance = new ServiceTwo();
                instance.serviceFour = ServiceFour.getInstance();
            }
            return instance;
        }

        public static ServiceTwo getInstanceSynchronized() {
            if (volatileInstance == null) {
                synchronized (Service.class) {
                    if (volatileInstance == null) {
                        volatileInstance = new ServiceTwo();
                        volatileInstance.serviceFour = ServiceFour.getInstanceSynchronized();
                    }
                }
            }
            return volatileInstance;
        }

        private @Service ServiceFour serviceFour;

        private ServiceTwo() {
        }

        public void service() {
            count++;
            serviceFour.service();
        }
    }

    private static @Service class ServiceThree {
        private static @Service ServiceThree instance;
        private static volatile @Service ServiceThree volatileInstance;
        private static int count;

        public static ServiceThree getInstance() {
            if (instance == null) {
                instance = new ServiceThree();
            }
            return instance;
        }

        public static ServiceThree getInstanceSynchronized() {
            if (volatileInstance == null) {
                synchronized (Service.class) {
                    if (volatileInstance == null) {
                        volatileInstance = new ServiceThree();
                    }
                }
            }
            return volatileInstance;
        }

        private ServiceThree() {
        }

        public void service() {
            count++;
        }
    }

    private static @Service class ServiceFour {
        private static @Service ServiceFour instance;
        private static volatile @Service ServiceFour volatileInstance;
        private static int count;

        public static ServiceFour getInstance() {
            if (instance == null) {
                instance = new ServiceFour();
                instance.serviceFive = ServiceFive.getInstance();
            }
            return instance;
        }

        public static ServiceFour getInstanceSynchronized() {
            if (volatileInstance == null) {
                synchronized (ServicesPerformanceTest.class) {
                    if (volatileInstance == null) {
                        volatileInstance = new ServiceFour();
                        volatileInstance.serviceFive = ServiceFive.getInstanceSynchronized();
                    }
                }
            }
            return volatileInstance;
        }

        private @Service ServiceFive serviceFive;

        private ServiceFour() {
        }

        public void service() {
            count++;
            serviceFive.service();
        }
    }

    private static @Service class ServiceFive {
        private static @Service ServiceFive instance;
        private static volatile @Service ServiceFive volatileInstance;
        private static int count;

        public static ServiceFive getInstance() {
            if (instance == null) {
                instance = new ServiceFive();
            }
            return instance;
        }

        public static ServiceFive getInstanceSynchronized() {
            if (volatileInstance == null) {
                synchronized (ServicesPerformanceTest.class) {
                    if (volatileInstance == null) {
                        volatileInstance = new ServiceFive();
                    }
                }
            }
            return volatileInstance;
        }

        private ServiceFive() {
        }

        public void service() {
            count++;
        }
    }
}
