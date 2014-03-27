package edu.wpi.first.wpilibj;

import java.util.ArrayList;

/**
 *
 * @author Patrick
 */
public class RobotStatus {

    public static ArrayList<PWM> pwms = new ArrayList<>();

    public static void addPWMDevice(PWM device) {
        for (PWM p : pwms) {
            if (p.equals(device)) {
                System.err.println("Important Error: Channel " + device.getChannel() + " on Module "
                        + device.getModuleNumber() + " already allocated.");
                return;
            }
        }

        pwms.add(device);
    }
}
