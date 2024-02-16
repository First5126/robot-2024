// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Arm extends SubsystemBase {

  private TalonFX RightArm;
  private TalonFX LeftArm;
  private Slot0Configs slot0Configs;

  public Arm() {
    RightArm = new TalonFX(0);
      RightArm.setInverted(true);

    LeftArm = new TalonFX(0);
      LeftArm.setInverted(false);
    
            
    slot0Configs = new Slot0Configs();
      slot0Configs.kP = Constants.ArmConstants.kP;
      slot0Configs.kI = Constants.ArmConstants.kI;
      slot0Configs.kD = Constants.ArmConstants.kD;
      slot0Configs.kA = Constants.ArmConstants.kA;
      slot0Configs.kS = Constants.ArmConstants.kS;
      slot0Configs.kV = Constants.ArmConstants.kV;
      slot0Configs.kG = Constants.ArmConstants.kG;
  }


  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {

  }
}
