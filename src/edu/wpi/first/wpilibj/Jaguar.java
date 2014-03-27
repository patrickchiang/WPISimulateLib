package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Jaguar extends PWM {

    public Jaguar(int channel) {
        this(1, channel);
    }

    public Jaguar(int slot, int channel) {
        super(slot, channel);
    }

    public String toString() {
        return "Jaguar, Module: " + getModuleNumber() + ", Channel: " + getChannel();
    }
}
