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
        //TODO
        //This commands needs to require the drivetrain
        addRequirements(m_LLSubsystem);
        
        SmartDashboard.putNumber("Distance Deadzone", 36);
    }

    @Override
    public void initialize(){
        //TODO this command never finishes and is created with an OnTrue condition
        // it won't release the subsustems it requires ever
        //This command should probably be called with a whileTrue so the relase of the button stops the command.
        isFinished = false;
    }

    @Override
    public void execute(){
        m_LLSubsystem.LLDrive();
    }
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
