package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.LLSubsystem;

public class LLDrive extends Command {
    private boolean isFinished;
    
    private LLSubsystem m_LLSubsystem;

    public LLDrive(LLSubsystem subsystem){
        m_LLSubsystem = subsystem;
        addRequirements(m_LLSubsystem);
        SmartDashboard.putNumber("Distance Deadzone", 36);
    }

    @Override
    public void initialize(){
        isFinished = false;
    }

    @Override

    public void execute(){
        m_LLSubsystem.LLDrive();
        if(m_LLSubsystem.getDistance(2) >= SmartDashboard.getNumber("Distance Deadzone", 36)){
            isFinished = true;
        }
    }
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
