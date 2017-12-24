package org.usfirst.frc.team4342.vision.steamworks;

import org.usfirst.frc.team4342.vision.DemonVision;
import org.usfirst.frc.team4342.vision.api.cameras.Camera;
import org.usfirst.frc.team4342.vision.api.cameras.USBCamera;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.Blur;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.PipelineParameters;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.RGBBounds;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.Resolution;
import org.usfirst.frc.team4342.vision.api.target.Target;
import org.usfirst.frc.team4342.vision.api.target.TargetComparator;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Main class
 */
public class Main  {
	private static final int TEAM_NUMBER = 4342;
	private static final String NETWORK_TABLES_IDENTITY = "team4342-raspberry-pi-3";

	// Microsoft LifeCam HD 3000
	private static final int USB_PORT = 0;
	private static final double FIELD_OF_VIEW = 68.5;
	
	// 360x240, Gaussian blur, and keep green targets
	private static final Resolution RESOLUTION = new Resolution(360, 240);
	private static final Blur BLUR = new Blur(Blur.Type.GAUSSIAN, 1.88);
	private static final RGBBounds RGB = new RGBBounds(0, 70, 84, 255, 0, 45);

	static {
		// Load OpenCV
		System.loadLibrary("opencv_java310");
		
		// Configure NetworkTables
		NetworkTable.setClientMode();
		NetworkTable.setNetworkIdentity(NETWORK_TABLES_IDENTITY);
		NetworkTable.setTeam(TEAM_NUMBER);
		NetworkTable.initialize();
	}
	
	/**
	 * The main entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		NetworkTable table = NetworkTable.getTable("SmartDashboard");
		
		// Camera
		Camera camera = new USBCamera(USB_PORT, FIELD_OF_VIEW);
		
		// Pipeline Parameters
		PipelineParameters parameters = new PipelineParameters(RESOLUTION, BLUR, RGB);
		
		// DemonVision
		DemonVision dv = new DemonVision(camera, parameters, (report) -> {
			table.putNumber("DV-Targets", report.getTargetCount());
			
	        if(report.getTargetCount() > 1) {
	        	Target[] targets = report.getTargets(TargetComparator.Type.AREA);
	            Target top = targets[0];

	            double robotYaw = table.getNumber("NavX-Yaw", 0.0);
	            double boilerYaw = robotYaw + top.getYawOffset(camera.getFoV());

	            table.putNumber("Boiler-CenterX", top.x);
	            table.putNumber("Boiler-CenterY", top.y);
	            table.putNumber("Boiler-Yaw", boilerYaw);
	        }
		});
		
		// Let's do this
		dv.runForever();
	}
}
