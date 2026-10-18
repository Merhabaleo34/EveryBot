package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveTrain extends SubsystemBase {

    SparkMax leaderRight;
    SparkMax followerRight;
    SparkMax leaderLeft;
    SparkMax followerLeft;

    public DriveTrain() {

        leaderRight = new SparkMax(1, MotorType.kBrushless);
        followerRight = new SparkMax(2, MotorType.kBrushless);
        leaderLeft = new SparkMax(3, MotorType.kBrushless);
        followerLeft = new SparkMax(4, MotorType.kBrushless);

        SparkMaxConfig followerLeftConfig = new SparkMaxConfig();
        SparkMaxConfig followerRightConfig = new SparkMaxConfig();
        SparkMaxConfig leaderLeftConfig = new SparkMaxConfig();
        SparkMaxConfig leaderRightConfig = new SparkMaxConfig();

        followerRightConfig.follow(1);
        followerLeftConfig.follow(3);
        leaderLeftConfig.inverted(true);

        followerLeftConfig.idleMode(IdleMode.kBrake);
        followerRightConfig.idleMode(IdleMode.kBrake);
        leaderLeftConfig.idleMode(IdleMode.kBrake);
        leaderRightConfig.idleMode(IdleMode.kBrake);

        followerLeftConfig.smartCurrentLimit(60);
        followerRightConfig.smartCurrentLimit(60);
        leaderLeftConfig.smartCurrentLimit(60);
        leaderRightConfig.smartCurrentLimit(60);

        followerLeft.configure(followerLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        followerRight.configure(followerRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leaderLeft.configure(leaderLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leaderRight.configure(leaderRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void drive(double speed, double steer) {
        double left = speed * 0.7 - steer * 0.3;
        double right = speed * 0.7 + steer * 0.3;

        leaderLeft.setVoltage(12*left);
        leaderRight.setVoltage(12*right);
    }

    public void stop() {
        leaderLeft.set(0);
        leaderRight.set(0);
    }

    public Command runDriveCommand(DoubleSupplier speed, DoubleSupplier steer) {

        return this.runEnd(
                () -> this.drive(
                        MathUtil.applyDeadband(speed.getAsDouble(), 0.05),
                        MathUtil.applyDeadband(steer.getAsDouble(), 0.05)),

                this::stop);
    }
}