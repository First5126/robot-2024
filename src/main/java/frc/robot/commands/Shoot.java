// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


public class Shoot extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished; 

    public Shoot(Shooter subsystem) {
        m_subsystem = subsystem;
        //addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    @Override
    public void execute() {
        m_subsystem.setShooterRPS();
        Timer.delay(5);
        if(m_subsystem.BackSeesNote()){
            m_subsystem.ManualIntakeSpeed(0.5);
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