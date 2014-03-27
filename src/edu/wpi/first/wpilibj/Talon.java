package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Talon extends PWM {

    public Talon(int channel) {
        this(1, channel);
    }

    public Talon(int slot, int channel) {
        super(slot, channel);
    }
}
