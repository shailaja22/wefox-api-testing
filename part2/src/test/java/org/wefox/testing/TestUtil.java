package org.wefox.testing;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtil {

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(500, 10000);
    }
}
