/**
 * 
 */
package org.leplus.keygen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * @author thomas
 *
 */
public class TestMain {

    @Test
    void test() throws IOException {
        Main.main(new String[] { "test" });
    }

}
