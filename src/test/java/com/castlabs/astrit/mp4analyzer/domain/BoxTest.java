package com.castlabs.astrit.mp4analyzer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class BoxTest {

    @Test
    public void testCreate() {
        byte[] header = new byte[] {0, 0, 0, 16, 'm', 'f', 'h', 'd'};
        Box box = Box.create(header);
        assertThat(box.getType()).isEqualTo("mfhd");
        assertThat(box.getSize()).isEqualTo(16);
        assertThat(box.getSubBoxes()).isEmpty();
        assertThat(box.getPayloadDataSize()).isEqualTo(8);
    }

    @Test
    public void testCreateWithEmptyArray() {
        byte[] header = new byte[0];
        assertThatIllegalArgumentException().isThrownBy(()->Box.create(header)).withMessage("Header size must be 8");
    }
    
}
