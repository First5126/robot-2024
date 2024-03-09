// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.ShooterSetpoint;
import frc.robot.ShooterSetpointConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


public class Shoot extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private double GoalRPS;
    private boolean isFinished;
    private double error;
    private double DeadZone;

    public Shoot(Shooter subsystem, double RPS) {
        m_subsystem = subsystem;
        GoalRPS = RPS;
        addRequirements(subsystem);
        
    }

    @Override
    public void initialize() {
        isFinished = false;
        ShooterSetpoint t = ShooterSetpointConstants.getInstance().getShooterSetpointElement(0);
    
        DeadZone = 2;
        //DeadZone = ShooterSetpointConstants.getInstance().getShooterSetPoints()[0].getDistance();
        GoalRPS = m_subsystem.getGoalRPS();
        System.out.println(GoalRPS);
    }

    @Override
    public void execute() {
        m_subsystem.setShooterRPS(GoalRPS);
        error = GoalRPS - m_subsystem.getCurrentShooterVelocity();
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("current velo", m_subsystem.getCurrentShooterVelocity());

        if( error <= DeadZone && m_subsystem.BackSeesNote() ) {
            m_subsystem.ManualIntakeSpeed(0.8);
        }

        if ( !m_subsystem.BackSeesNote() ){
            Timer.delay(1);
            isFinished = true;
        }

        
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.zeroShooter();
        m_subsystem.zeroIntake();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}