package frc.robot.commands;

import frc.robot.subsystems.LLSubsystem;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.CommandSwerveDrivetrain;

public class LLAmpAdjust extends Command {
    private boolean isFinished;
    
    private LLSubsystem m_LLSubsystem;
    private CommandSwerveDrivetrain m_drivetrain;

    private SwerveRequest.FieldCentric m_drive;

    public LLAmpAdjust(LLSubsystem subsystem, CommandSwerveDrivetrain drivetrain, SwerveRequest.FieldCentric drive){
        m_LLSubsystem = subsystem;
        m_drivetrain = drivetrain;
        m_drive = drive;

        addRequirements(m_drivetrain);
        addRequirements(m_LLSubsystem);
    }

    @Override
    public void initialize(){
        isFinished = false;
    }

    @Override
    public void execute(){
        m_LLSubsystem.limelightAutoAdjust(m_drivetrain, m_drive);
    }

    @Override
    public void end(boolean interrupted){}

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
