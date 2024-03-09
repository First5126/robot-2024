package frc.robot.commands.commandGroups;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.RotateArm;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shooter;

public class MoveArmShoot extends SequentialCommandGroup {
    private Shooter m_ShooterSubsystem;
    private Arm m_ArmSubsystem;

    public MoveArmShoot(Shooter m_ShooterSubsystem, double RPS, Arm m_ArmSubsystem, double position) {
        addCommands(
            new RotateArm(m_ArmSubsystem, position),
            new Shoot(m_ShooterSubsystem, RPS)

        );
    }
}