package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.invoke.MethodHandles.lookup;

public class UsersManager {

    private final Queue<String> availableUsers = new ConcurrentLinkedQueue<>();
    private boolean initialized = false;
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());

    public UsersManager() {

    }

    public synchronized void initialize(String[] users) {
        if (!initialized) {

            Collections.addAll(availableUsers, users);
            initialized = true;
        }
    }

    public synchronized boolean printAvailableUsers() {
        System.out.println("Available users: " + availableUsers);
        return false;
    }


    public synchronized String acquireUser() throws InterruptedException {
        while (availableUsers.isEmpty()) {
            System.out.println("waiting");
            log.info("waiting");
            wait(); // Wait until a user is released
        }
        return availableUsers.poll();
    }

    public synchronized void releaseUser(String user) {
        availableUsers.offer(user);
        notifyAll(); // Notify waiting threads
    }

    public boolean isInitialized() {
        return initialized;
    }
}
