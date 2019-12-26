import java.util.concurrent.Semaphore;

/**
 * @author Masoud Dabbaghi
 * @version 1.0
 * @link https://github.com/masoudd2159
 * @since 2019
 **/

public class DiningPhilosophers extends Thread {

    private static final int TIME_SLEEP = 2000;
    private Semaphore[] fork = new Semaphore[5];

    private DiningPhilosophers() {
        for (int i = 0; i < 5; i++) {
            fork[i] = new Semaphore(1, true);
        }
    }

    class Philosopher extends Thread {
        int id;

        private Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    think();
                    fork[id].acquire();
                    fork[(id + 1) % 5].acquire();
                    eat();
                    fork[(id + 1) % 5].release();
                    fork[id].release();
                    putFork();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void think() {
            try {
                System.out.println("Philosopher " + id + " Thinking");
                Thread.sleep(TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void eat() {
            try {
                System.out.println("Philosopher " + id + " Eating");
                Thread.sleep(TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void putFork() {
            System.out.println("Philosopher " + id + " Put His Fork");
        }
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        diningPhilosophers.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Philosopher philosopher = new Philosopher(i);
            philosopher.start();
        }
    }
}