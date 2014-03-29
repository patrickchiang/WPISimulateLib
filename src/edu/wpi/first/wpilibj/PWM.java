package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class PWM {

    private double value = 0;
    private int moduleNumber = 0;
    private int channel = 0;

    public PWM(int channel) {
        this(1, channel);
    }

    public PWM(int moduleNumber, int channel) {
        this.moduleNumber = moduleNumber;
        this.channel = channel;

        RobotStatus.addPWMDevice(this);
    }

    public double get() {
        return value;
    }

    public void set(double speed) {
        value = speed;
    }

    public void setRaw(int raw) {
        value = raw / 127 - 1.0;
    }

    public int getRaw() {
        return (int) ((value + 1.0) * 127.0);
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
