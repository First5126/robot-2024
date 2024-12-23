// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private static LEDS_CANdle m_CANdle;

  //private Blinkin m_Blinkin;

  
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    m_CANdle = new LEDS_CANdle();
    //m_Blinkin = new Blinkin();

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    m_CANdle.Larson(132, 2, 245);
  }

  @Override
  public void disabledPeriodic() {
    //mRobotContainer.ChangeArmMode();
    //m_Blinkin.PurpleHeartbeat();
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    //if(DriverStation.Alliance.Blue == DriverStation.getAlliance()
    System.out.println("auto innit");
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      System.out.println("auto is not null");
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    //m_Blinkin.DoubleColorWave();
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if(DriverStation.getAlliance().orElse(Alliance.Blue)==Alliance.Red){


    }
    m_CANdle.Twinkle(132, 2, 245);
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    
  }

  @Override
  public void teleopPeriodic() {
    //RobotContainer.ChangeArmMode();

    //m_Blinkin.DoubleSinelon();
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }

  @Override
  public void testExit() {

  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

  public static LEDS_CANdle getCaNdle(){
    return m_CANdle;
  }
}
