package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Solenoid {

    private boolean value = false;
    private int moduleNumber = 0;
    private int channel = 0;

    public Solenoid(int channel) {
        this(1, channel);
    }

    public Solenoid(int moduleNumber, int channel) {
        this.moduleNumber = moduleNumber;
        this.channel = channel;

        RobotStatus.addSolenoidDevice(this);
    }

    public boolean get() {
        return value;
    }

    public void set(boolean on) {
        value = on;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public int getChannel() {
        return channel;
    }

    public boolean equals(PWM o) {
        return getModuleNumber() == o.getModuleNumber() && getChannel() == o.getChannel();
    }

    @Override
    public String toString() {
        return "\"" + this.getClass().getSimpleName() + "\": {\"Module\": "
                + getModuleNumber() + ", \"Channel\": " + getChannel()
                + ", \"Value\": " + get() + "}";
    }
}
