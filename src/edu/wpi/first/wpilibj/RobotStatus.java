package edu.wpi.first.wpilibj;

import java.util.ArrayList;

/**
 *
 * @author Patrick
 */
public class RobotStatus {

    // List of all the registered PWM devices.
    public static ArrayList<DigitalSidecarDevice> sidecar = new ArrayList<DigitalSidecarDevice>();
    // List of solenoid devices
    public static ArrayList<Solenoid> solenoids = new ArrayList<Solenoid>();
    /*
     * Status of robot.
     * 0 = Disabled
     * 1 = Autonomous
     * 2 = Teleop
     * 3 = Test
     */
    public static int state = 0;

    public static void addDigitalDevice(DigitalSidecarDevice device) {
        // Registering same channel twice will result in error.
        for (DigitalSidecarDevice p : sidecar) {
            if (p.equals(device)) {
                System.err.println("Important Error: Channel " + device.getChannel() + " on Module "
                        + device.getModuleNumber() + " already allocated.");
                return; // behavior is to ignore new registration
            }
        }

        sidecar.add(device);
    }

    public static void addSolenoidDevice(Solenoid device) {
        // Registering same channel twice will result in error.
        for (Solenoid s : solenoids) {
            if (s.equals(device)) {
                System.err.println("Important Error: Channel " + device.getChannel() + " on Module "
                        + device.getModuleNumber() + " already allocated.");
                return; // behavior is to ignore new registration
            }
        }

        solenoids.add(device);
    }

    public static String devicesToJson() {
        String json = "{";
        for (DigitalSidecarDevice p : sidecar) {
            json += p + ",";
        }
        for (Solenoid s : solenoids) {
            json += s + ",";
        }
        return json.substring(0, json.length() - 1) + "}";
    }

    public static void disable() {
        for (DigitalSidecarDevice p : sidecar) {
            p.set(0.0);
        }
        for (Solenoid s : solenoids) {
            s.set(false);
        }
    }
}
