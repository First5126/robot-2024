// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private TalonFX LeftShoot;
    private TalonFX RightShoot;
    private CANSparkMax Intake;
    private double ShooterRotationsPerSecond;
    private double IntakeSpeed;

    public ShooterSubsystem() {
        LeftShoot = new TalonFX(0);
        RightShoot = new TalonFX(1);
        Intake = new CANSparkMax(0, MotorType.kBrushless);
        ShooterRotationsPerSecond = 0;

        LeftShoot.setControl(new Follower(1, true));
    }

    @Override
    public void periodic() {
        ShooterRotationsPerSecond = SmartDashboard.getNumber("Desired Rotations Per Second", 0);
        IntakeSpeed = SmartDashboard.getNumber("Intake Speed", 0);
    }

    @Override
    public void simulationPeriodic() {}

    public void setShooterRPS(){
        RightShoot.setControl(new VelocityVoltage(ShooterRotationsPerSecond));
    }

    public void setIntakeSpeed(){
        Intake.set(IntakeSpeed);
    }
}
