// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.LEDS_CANdle;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shooter;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


public class Shoot extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_ShooterSubsystem;
    private Arm m_ArmSubsystem;
    private double GoalRPS;
    private boolean isFinished;
    private double error;
    private double DeadZone;
    private double ArmPosition;
    private LEDS_CANdle m_CANdle;

    public Shoot(Shooter m_ShooterSubsystem, Arm m_ArmSubsystem, double RPS) {
        this.m_ShooterSubsystem = m_ShooterSubsystem;
        this.m_ArmSubsystem = m_ArmSubsystem;
        GoalRPS = RPS;
        m_CANdle = LEDS_CANdle.getInstance();

        addRequirements(m_ShooterSubsystem);
        
    }

    @Override
    public void initialize() {
        System.out.println("shoot innit");
        isFinished = false;
        ArmPosition = m_ArmSubsystem.getPosition();
        //ShooterSetpoint t = ShooterSetpointConstants.getInstance().getShooterSetpointElement(0);
    
        DeadZone = 2;
        //DeadZone = ShooterSetpointConstants.getInstance().getShooterSetPoints()[0].getDistance();
    }

    @Override
    public void execute() {
        m_ShooterSubsystem.setShooterRPS(GoalRPS);
        error = GoalRPS - m_ShooterSubsystem.getCurrentShooterVelocity();
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("current velo", m_ShooterSubsystem.getCurrentShooterVelocity());

        if( error <= DeadZone && m_ShooterSubsystem.BackSeesNote() ) {
            m_CANdle.clearAnimation();
            m_CANdle.Twinkle(245,99,2);
            m_ShooterSubsystem.ManualIntakeSpeed(0.7);
        } 

        if (!m_ShooterSubsystem.BackSeesNote()){
            m_CANdle.clearAnimation();
            m_CANdle.Twinkle(132, 2, 245);
            isFinished = true;  
        }
        //m_CANdle.Twinkle(132, 2, 245);

    }

    @Override
    public void end(boolean interrupted) {
        m_CANdle.clearAnimation();
        m_CANdle.Twinkle(132, 2, 245);
        System.out.println("shoot end");
        m_ShooterSubsystem.zeroShooter();
        m_ShooterSubsystem.zeroIntake();

        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}