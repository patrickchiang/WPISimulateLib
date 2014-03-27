package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class IterativeRobot {

    public void startCompetition() {

    }

    public void robotInit() {
        System.out.println("Default robotInit()... override me.");
    }

    public void autonomousInit() {
        System.out.println("Default autonomousInit()... override me.");
    }

    public void autonomousPeriodic() {
        System.out.println("Default autonomousPeriodic()... override me.");
    }

    public void teleopInit() {
        System.out.println("Default teleopInit()... override me.");
    }

    public void teleopPeriodic() {
        System.out.println("Default teleopPeriodic()... override me.");
    }

    public void testInit() {
        System.out.println("Default testInit()... override me.");
    }

    public void testPeriodic() {
        System.out.println("Default testPeriodic()... override me.");
    }

    public void disabledInit() {
        System.out.println("Default disabledInit()... override me.");
    }

    public void disabledPeriodic() {
        System.out.println("Default disabledPeriodic()... override me.");
    }
}
