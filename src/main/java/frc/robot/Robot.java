// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.ManualRotation;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private LEDS_CANdle m_CANdle;
  private Blinkin m_Blinkin;

  
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    m_CANdle = new LEDS_CANdle();
    m_Blinkin = new Blinkin();

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    m_CANdle.Larson(255, 56, 0);
    m_Blinkin.PurpleHeartbeat();
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    m_Blinkin.DoubleColorWave();
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    
  }

  @Override
  public void teleopPeriodic() {
    m_CANdle.Twinkle(255, 87, 51);
    m_Blinkin.DoubleSinelon();
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();

    SmartDashboard.putData(new ManualRotation(null, kDefaultPeriod));
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
}
