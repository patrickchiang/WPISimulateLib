package edu.wpi.first.wpilibj;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author Patrick
 */
public class RobotMain {

    public static void main(String[] args) {
        //startServer();
        
        IterativeRobot robot = new TestCode();
        robot.robotInit();

        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            robot.teleopPeriodic();
            //System.out.println(RobotStatus.pwms);
        }
    }

    public static void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            server.createContext("/backend", new Backend());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
