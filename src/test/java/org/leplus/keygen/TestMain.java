/**
 * 
 */
package org.leplus.keygen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void test() throws IOException {
        Main.main(new String[] { "test" });
    }

}
