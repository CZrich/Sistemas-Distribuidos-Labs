package lab02.resuelto_docente.res;

import java.util.ArrayList;
import java.util.List;

public class LamportDriveTest {
    public static void main(String[] args) {

        List<Thread> threads = new ArrayList<>();
        LamportClock clock = new LamportClock();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    int time = clock.tick();
                    System.out.println(
                            "Thread: " + Thread.currentThread().getId() + " crated event with Lamport time " + time);
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int receivedTime = clock.tick();
                    System.out.println("Thread: " + Thread.currentThread().getId()
                            + " received event with Lamport time " + receivedTime);
                    clock.update(receivedTime);

                }
            });

            threads.add(thread);
            thread.start();

        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
        System.out.println("Final Lamport time: " + clock.getTime());
    }

}
