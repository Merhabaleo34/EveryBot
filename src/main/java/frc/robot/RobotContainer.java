// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.ClimbSubsystem;
import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.FuelSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  DriveTrain driveTrain = new DriveTrain();
  FuelSubsystem fuelSubsystem = new FuelSubsystem();
  ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() { 

    controller.leftBumper().whileTrue(fuelSubsystem.runIntakeCommand());

    controller.rightBumper().whileTrue(fuelSubsystem.runLaunchCommand());

    controller.a().whileTrue(fuelSubsystem.runEjectCommand());

    controller.povDown().whileTrue(climbSubsystem.runClimbCommand());
   
    controller.povUp().whileTrue(climbSubsystem.runClimbDownCommand());


    climbSubsystem.setDefaultCommand(climbSubsystem.run(climbSubsystem::stop));

    fuelSubsystem.setDefaultCommand(fuelSubsystem.run(fuelSubsystem::stop));

    driveTrain.setDefaultCommand(driveTrain.runDriveCommand(
        () -> -controller.getLeftY(),
        controller::getLeftX));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
