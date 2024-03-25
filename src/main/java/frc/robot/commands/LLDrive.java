package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.LLSubsystem;

public class LLDrive extends Command {
    private boolean isFinished;
    
    private LLSubsystem m_LLSubsystem;
    private Timer timer;

    public LLDrive(LLSubsystem subsystem){
        m_LLSubsystem = subsystem;
        timer = new Timer();
        addRequirements(m_LLSubsystem);
        
    }

    @Override
    public void initialize(){
        System.out.println("LLDrive --> Timer startup ");
        timer.stop();
        timer.reset();
        timer.start();
        isFinished = false;
    }

    @Override

    public void execute(){
        /*if(!timer.hasElapsed(12)){
            m_LLSubsystem.LLDrive();
        }
        else{
            isFinished = true;
        }*/
        m_LLSubsystem.LLDrive();
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("LLDRIVE --> Command Ended... ");
        System.out.println("LLDRIVE --> Timer Ran" + timer.get());
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
