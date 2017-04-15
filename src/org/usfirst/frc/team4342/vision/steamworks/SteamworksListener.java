package org.usfirst.frc.team4342.vision.steamworks;

import org.usfirst.frc.team4342.vision.api.cameras.Camera;
import org.usfirst.frc.team4342.vision.api.listeners.Listener;
import org.usfirst.frc.team4342.vision.api.tables.SmartDashboard;
import org.usfirst.frc.team4342.vision.api.target.Target;
import org.usfirst.frc.team4342.vision.api.target.TargetReport;

/**
 * <p>Listener for FIRST Steamworks</p>
 * 
 * Calculates the yaw of the boiler and sends a recommended yaw
 * that the robot should orient to to shoot
 */
public class SteamworksListener implements Listener {
    private Camera camera;

    /**
     * Constructs a new <code>SteamworksListener</code>
     * @param camera the camera that retrieved the image to process the targets
     */
    public SteamworksListener(Camera camera) {
            this.camera = camera;
    }

    /**
     * <p>Calculates the yaw of boiler and puts it up to the SmartDashboard</p>
     * 
     * {@inheritDoc}
     */
    @Override
    public void processTargets(TargetReport report) {
        SmartDashboard.putNumber("DV-Targets", report.getTargetCount());

        if(report.getTargetCount() > 1) {
            Target top = report.getTarget(0);

            double robotYaw = SmartDashboard.getNumber("NavX-Yaw", 0.0);
            double boilerYaw = robotYaw + top.getYawOffset(camera.getFOV());

            SmartDashboard.putNumber("Boiler-CenterX-Ratio", top.getCenterXRatio());
            SmartDashboard.putNumber("Boiler-CenterY-Ratio", top.getCenterYRatio());
            SmartDashboard.putNumber("Boiler-Yaw", boilerYaw);
        }
    }
}