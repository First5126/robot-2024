package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;


public class TroubleShootReverse extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished; 

    public TroubleShootReverse(Shooter subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    @Override
    public void execute() {
        if(m_subsystem.FrontSeesNote()){
            isFinished = true;
        }
        else{
            m_subsystem.ReverseTheNote();
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
