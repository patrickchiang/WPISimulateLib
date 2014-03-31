package edu.wpi.first.wpilibj;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author Patrick
 */
public class RobotMain {

    public static final int SERVER_PORT = 3070;
    public static final int LOOP_PERIOD = 20;

    public static void main(String[] args) {
        startServer();

        IterativeRobot robot = new TestCode();
        robot.robotInit();
        robot.disabledInit();
        RobotStatus.disable();
        int lastState = RobotStatus.state;

        // Always in periodic check. Delegate state changing to frontend.
        while (true) {
            try {
                Thread.sleep(LOOP_PERIOD);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Changed state in last loop time
            if (lastState != RobotStatus.state) {
                switch (RobotStatus.state) {
                    case 0:
                        robot.disabledInit();
                        RobotStatus.disable();
                        break;
                    case 1:
                        robot.autonomousInit();
                        break;
                    case 2:
                        robot.teleopInit();
                        break;
                    case 3:
                        robot.testInit();
                        break;
                    default:
                        System.err.println("Fatal Error: Invalid robot state.");
                        System.exit(1);
                        break;
                }
            }

            lastState = RobotStatus.state;

            switch (RobotStatus.state) {
                case 0:
                    robot.disabledPeriodic();
                    break;
                case 1:
                    robot.autonomousPeriodic();
                    break;
                case 2:
                    robot.teleopPeriodic();
                    break;
                case 3:
                    robot.testPeriodic();
                    break;
                default:
                    System.err.println("Fatal Error: Invalid robot state.");
                    System.exit(1);
                    break;
            }
        }
    }

    public static void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            server.createContext("/backend", new Backend());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
