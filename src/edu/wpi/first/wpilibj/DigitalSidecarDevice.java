package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public abstract class DigitalSidecarDevice {

    public abstract int getModuleNumber();

    public abstract int getChannel();

    public abstract void set(double speed);

    public abstract double get();
}
