package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Victor extends PWM {

    public Victor(int channel) {
        this(1, channel);
    }

    public Victor(int slot, int channel) {
        super(slot, channel);
    }

    public String toString() {
        return "Victor, Module: " + getModuleNumber() + ", Channel: " + getChannel();
    }
}
