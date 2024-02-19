// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  //Subsystems

  //Controllers
  private final CommandXboxController m_driverController = new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);
  private final GenericHID m_ButtonsController = new GenericHID(Constants.OperatorConstants.ButtonsControllerPort);

  public RobotContainer() {
    configureBindings();
    
  }

  private void configureBindings() {
    //Buttons Controller

  }
 
}

