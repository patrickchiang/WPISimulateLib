package edu.wpi.first.wpilibj;

import java.util.ArrayList;

/**
 *
 * @author Patrick
 */
public class RobotStatus {

    // List of all the registered PWM devices.
    public static ArrayList<PWM> pwms = new ArrayList<PWM>();
    /*
     * Status of robot.
     * 0 = Disabled
     * 1 = Autonomous
     * 2 = Teleop
     * 3 = Test
     */
    public static int state = 0;

    public static void addPWMDevice(PWM device) {
        // Registering same channel twice will result in error.
        for (PWM p : pwms) {
            if (p.equals(device)) {
                System.err.println("Important Error: Channel " + device.getChannel() + " on Module "
                        + device.getModuleNumber() + " already allocated.");
                return; // behavior is to ignore new registration
            }
        }

        pwms.add(device);
    }

    public static String devicesToJson() {
        String json = "{";
        for (PWM p : pwms) {
            json += p + ",";
        }
        return json.substring(0, json.length() - 1) + "}";
    }

    public static void disable() {
        for (PWM p : pwms) {
            p.set(0);
        }
    }
}
