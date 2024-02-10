package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished; 

    public Intake(Shooter subsystem) {
        m_subsystem = subsystem;
        //addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    @Override
    public void execute() {
        m_subsystem.SetPickUpSpeed();
        if(m_subsystem.BackSeesNote() == true){
            //m_subsystem.setIntakeSpeed
            isFinished = true;
        }
        else{
            m_subsystem.SetPickUpSpeed();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.zeroIntake();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}

