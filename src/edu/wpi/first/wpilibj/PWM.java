package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class PWM {

    private int value = 0;
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
        return value / 255.0;
    }

    public void set(double speed) {
        value = (int) (speed * 255);
    }

    public void setRaw(int raw) {
        value = raw;
    }

    public int getRaw() {
        return value;
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
}
