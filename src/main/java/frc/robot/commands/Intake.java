package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsystem m_subsystem;
    private boolean isFinished; 

    public Intake(ShooterSubsystem subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    @Override
    public void execute() {
        m_subsystem.setIntakeSpeed();
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
