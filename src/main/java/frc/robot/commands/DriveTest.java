package frc.robot.commands;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.CommandSwerveDrivetrain;

public class DriveTest extends Command{
    final CommandSwerveDrivetrain drive;
    final SwerveRequest.RobotCentric driveRequest;
    public DriveTest(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric test){
        drive = drivetrain;
        driveRequest = test;
        addRequirements(drive);
        
    }
    @Override
    public void initialize() {}
    @Override
    public void end(boolean interrupted) {}
    @Override
    public void execute() {
        drive.applyRequest(() -> driveRequest.withVelocityX(0.5).withVelocityY(0).withRotationalRate(0)).execute();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    
    
}
