import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UI extends JFrame
{
	private static final long serialVersionUID = 2584652515365917421L;
	
	private static JFrame Status_Window_Frame;
	private static JFrame Settings_Window_Frame;
	private static JFrame Calibrate_Window_Frame;
	private static JFrame About_Window_Frame;
	
	private static JLabel Status_Label;
	private static JLabel Time_Label;
	
	public static void Initialize_Status_Window()
	{
		Status_Window_Frame = new JFrame("FoxTek - Eso Fishing ReelBot");		
		Status_Window_Frame.setLayout(new GridLayout(4, 1));		
		Status_Window_Frame.setSize(350, 100);
		Status_Window_Frame.setAlwaysOnTop(true);
		Status_Window_Frame.setResizable(false);
		Status_Window_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Status_Label = new JLabel("Status");
		Status_Window_Frame.add(Status_Label);
		
		Time_Label = new JLabel("Time");
		Status_Window_Frame.add(Time_Label);
		
		JButton Settings_Button = new JButton("Settings");
		Settings_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Status_Window_Frame.setEnabled(false);
				
				Fish_Bot.Run_Bot = false;
				while(Fish_Bot.Bot_Running){Fish_Bot.Timer(100);}
				
				Create_Settings_Window();
			}
		});
		Status_Window_Frame.add(Settings_Button);
		
		JButton About_Button = new JButton("About");
		About_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Status_Window_Frame.setEnabled(false);
				
				Fish_Bot.Run_Bot = false;
				while(Fish_Bot.Bot_Running){Fish_Bot.Timer(100);}
				
				Create_About_Window();
			}
		});
		Status_Window_Frame.add(About_Button);
		
		Status_Window_Frame.setVisible(true);
	}
	
	public static void Set_Status_Label(String Status)
	{
		Status_Label.setText(Status);
	}
	
	public static void Set_Time_Label(String Time_String)
	{
		Time_Label.setText(Time_String);
	}
	
	private static void Create_Settings_Window()
	{
		Settings_Window_Frame = new JFrame("FoxTek - Eso Fishing ReelBot : Settings");
		Settings_Window_Frame.setLayout(new GridLayout(17, 2));
		Settings_Window_Frame.setSize(1050, 500);
		Settings_Window_Frame.setAlwaysOnTop(true);
		Settings_Window_Frame.setResizable(false);
		Settings_Window_Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JLabel Calibrate_Label = new JLabel("Calibrate: ");
		JButton Calibrate_Button = new JButton("Calibrate");
		Calibrate_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings_Window_Frame.setVisible(false);
				Calibrate_Window(false);
			}
		});
		Settings_Window_Frame.add(Calibrate_Label);
		Settings_Window_Frame.add(Calibrate_Button);
		
		Settings_Window_Frame.add(new JLabel(""));
		Settings_Window_Frame.add(new JLabel(""));
		
		//public int Key_To_Use = KeyEvent.VK_E;
		
		JLabel Text_Color_To_Serch_For_Label = new JLabel("Interaction Text Color: ");
		Color Text_Color_To_Serch_For_Color = new Color(Entry.Running_Settings.Text_Color_To_Serch_For.getRGB());
		JButton Text_Color_To_Serch_For_Button = new JButton("Set");
		Settings_Window_Frame.add(Text_Color_To_Serch_For_Label);
		Settings_Window_Frame.add(Text_Color_To_Serch_For_Button);
		
		JLabel Reelin_Marker_Color_To_Search_For_Label = new JLabel("Reelin Animation Color: ");
		Color Reelin_Marker_Color_To_Search_For_Color = new Color(Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getRGB());
		JButton Reelin_Marker_Color_To_Search_For_Button = new JButton("Set");
		Settings_Window_Frame.add(Reelin_Marker_Color_To_Search_For_Label);
		Settings_Window_Frame.add(Reelin_Marker_Color_To_Search_For_Button);
		
		Settings_Window_Frame.add(new JLabel(""));
		Settings_Window_Frame.add(new JLabel(""));
		
		JLabel Delay_Note_Label_1 = new JLabel("All delays are in milliseconds. 1000 Milliseconds = 1 Second.");
		Settings_Window_Frame.add(Delay_Note_Label_1);
		
		JLabel Delay_Note_Label_2 = new JLabel("If min and max are not the same a random number between the two values will be used.");
		Settings_Window_Frame.add(Delay_Note_Label_2);
		
		JLabel Frame_Capture_Delay_Min_Label = new JLabel("Min Frame Capture Delay: ");
		SpinnerNumberModel Frame_Capture_Delay_Min_Model = new SpinnerNumberModel(Entry.Running_Settings.Frame_Capture_Delay_Min, 0, 60000, 1);
		JSpinner Frame_Capture_Delay_Min_Spinner = new JSpinner(Frame_Capture_Delay_Min_Model);
		Settings_Window_Frame.add(Frame_Capture_Delay_Min_Label);
		Settings_Window_Frame.add(Frame_Capture_Delay_Min_Spinner);
		
		JLabel Frame_Capture_Delay_Max_Label = new JLabel("Max Frame Capture Delay: ");
		SpinnerNumberModel Frame_Capture_Delay_Max_Model = new SpinnerNumberModel(Entry.Running_Settings.Frame_Capture_Delay_Max, 0, 60000, 1);
		JSpinner Frame_Capture_Delay_Max_Spinner = new JSpinner(Frame_Capture_Delay_Max_Model);
		Settings_Window_Frame.add(Frame_Capture_Delay_Max_Label);
		Settings_Window_Frame.add(Frame_Capture_Delay_Max_Spinner);
		
		JLabel Next_Step_Delay_Min_Label = new JLabel("Pre Key Press Delay Min");
		SpinnerNumberModel Next_Step_Delay_Min_Model = new SpinnerNumberModel(Entry.Running_Settings.Next_Step_Delay_Min, 0, 60000, 1);
		JSpinner Next_Step_Delay_Min_Spinner = new JSpinner(Next_Step_Delay_Min_Model);
		Settings_Window_Frame.add(Next_Step_Delay_Min_Label);
		Settings_Window_Frame.add(Next_Step_Delay_Min_Spinner);
		
		JLabel Next_Step_Delay_Max_Label = new JLabel("Pre Key Press Delay Max");
		SpinnerNumberModel Next_Step_Delay_Max_Model = new SpinnerNumberModel(Entry.Running_Settings.Next_Step_Delay_Max, 0, 60000, 1);
		JSpinner Next_Step_Delay_Max_Spinner = new JSpinner(Next_Step_Delay_Max_Model);
		Settings_Window_Frame.add(Next_Step_Delay_Max_Label);
		Settings_Window_Frame.add(Next_Step_Delay_Max_Spinner);
		
		JLabel Key_Press_Release_Delay_Min_Label = new JLabel("Key Hold Time Min");
		SpinnerNumberModel Key_Press_Release_Delay_Min_Model = new SpinnerNumberModel(Entry.Running_Settings.Key_Press_Release_Delay_Min, 0, 60000, 1);
		JSpinner Key_Press_Release_Delay_Min_Spinner = new JSpinner(Key_Press_Release_Delay_Min_Model);
		Settings_Window_Frame.add(Key_Press_Release_Delay_Min_Label);
		Settings_Window_Frame.add(Key_Press_Release_Delay_Min_Spinner);
		
		JLabel Key_Press_Release_Delay_Max_Label = new JLabel("Key Hold Time Max");
		SpinnerNumberModel Key_Press_Release_Delay_Max_Model = new SpinnerNumberModel(Entry.Running_Settings.Key_Press_Release_Delay_Max, 0, 60000, 1);
		JSpinner Key_Press_Release_Delay_Max_Spinner = new JSpinner(Key_Press_Release_Delay_Max_Model);
		Settings_Window_Frame.add(Key_Press_Release_Delay_Max_Label);
		Settings_Window_Frame.add(Key_Press_Release_Delay_Max_Spinner);
		
		JLabel Start_Fishing_To_Reelin_State_Delay_Min_Label = new JLabel("Start of Fishing - Reelin Wait Delay Min");
		SpinnerNumberModel Start_Fishing_To_Reelin_State_Delay_Min_Model = new SpinnerNumberModel(Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Min, 0, 60000, 1);
		JSpinner Start_Fishing_To_Reelin_State_Delay_Min_Spinner = new JSpinner(Start_Fishing_To_Reelin_State_Delay_Min_Model);
		Settings_Window_Frame.add(Start_Fishing_To_Reelin_State_Delay_Min_Label);
		Settings_Window_Frame.add(Start_Fishing_To_Reelin_State_Delay_Min_Spinner);
		
		JLabel Start_Fishing_To_Reelin_State_Delay_Max_Label = new JLabel("Start of Fishing - Reelin Wait Delay Max");
		SpinnerNumberModel Start_Fishing_To_Reelin_State_Delay_Max_Model = new SpinnerNumberModel(Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Max, 0, 60000, 1);
		JSpinner Start_Fishing_To_Reelin_State_Delay_Max_Spinner = new JSpinner(Start_Fishing_To_Reelin_State_Delay_Max_Model);
		Settings_Window_Frame.add(Start_Fishing_To_Reelin_State_Delay_Max_Label);
		Settings_Window_Frame.add(Start_Fishing_To_Reelin_State_Delay_Max_Spinner);
		
		JLabel Reelin_To_Start_Fishing_State_Delay_Min_Label = new JLabel("Reelin Wait - Start of Fishing Delay Min");
		SpinnerNumberModel Reelin_To_Start_Fishing_State_Delay_Min_Model = new SpinnerNumberModel(Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Min, 0, 60000, 1);
		JSpinner Reelin_To_Start_Fishing_State_Delay_Min_Spinner = new JSpinner(Reelin_To_Start_Fishing_State_Delay_Min_Model);
		Settings_Window_Frame.add(Reelin_To_Start_Fishing_State_Delay_Min_Label);
		Settings_Window_Frame.add(Reelin_To_Start_Fishing_State_Delay_Min_Spinner);
		
		JLabel Reelin_To_Start_Fishing_State_Delay_Max_Label = new JLabel("Reelin Wait - Start of Fishing Delay Max");
		SpinnerNumberModel Reelin_To_Start_Fishing_State_Delay_Max_Model = new SpinnerNumberModel(Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Max, 0, 60000, 1);
		JSpinner Reelin_To_Start_Fishing_State_Delay_Max_Spinner = new JSpinner(Reelin_To_Start_Fishing_State_Delay_Max_Model);
		Settings_Window_Frame.add(Reelin_To_Start_Fishing_State_Delay_Max_Label);
		Settings_Window_Frame.add(Reelin_To_Start_Fishing_State_Delay_Max_Spinner);
		
		JButton Apply_Button = new JButton("Apply");
		Apply_Button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Entry.Running_Settings.Text_Color_To_Serch_For = Text_Color_To_Serch_For_Color;
				Entry.Running_Settings.Reelin_Marker_Color_To_Search_For = Reelin_Marker_Color_To_Search_For_Color;
				
				Entry.Running_Settings.Frame_Capture_Delay_Min = (int)Frame_Capture_Delay_Min_Spinner.getValue();			
				Entry.Running_Settings.Frame_Capture_Delay_Max = (int)Frame_Capture_Delay_Max_Spinner.getValue();
				
				Entry.Running_Settings.Next_Step_Delay_Min = (int)Next_Step_Delay_Min_Spinner.getValue();
				Entry.Running_Settings.Next_Step_Delay_Max = (int)Next_Step_Delay_Max_Spinner.getValue();
				
				Entry.Running_Settings.Key_Press_Release_Delay_Min = (int)Key_Press_Release_Delay_Min_Spinner.getValue();
				Entry.Running_Settings.Key_Press_Release_Delay_Max = (int)Key_Press_Release_Delay_Max_Spinner.getValue();
				
				Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Min = (int)Start_Fishing_To_Reelin_State_Delay_Min_Spinner.getValue();
				Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Max = (int)Start_Fishing_To_Reelin_State_Delay_Max_Spinner.getValue();
				
				Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Min = (int)Reelin_To_Start_Fishing_State_Delay_Min_Spinner.getValue();
				Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Max = (int)Reelin_To_Start_Fishing_State_Delay_Max_Spinner.getValue();
				
				Settings_Window_Frame.setVisible(false);
				Settings_Window_Frame = null;
				
				new Thread(new Fish_Bot()).start();
				
				Status_Window_Frame.setEnabled(true);
			}
		});
		Settings_Window_Frame.add(Apply_Button);
		
		JButton Cancel_Button = new JButton("Cancel");
		Cancel_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings_Window_Frame.setVisible(false);
				Settings_Window_Frame = null;
				
				new Thread(new Fish_Bot()).start();
				
				Status_Window_Frame.setEnabled(true);
			}
		});
		Settings_Window_Frame.add(Cancel_Button);
		
		Settings_Window_Frame.setVisible(true);
	}
	
	public static void Calibrate_Window(boolean Required)
	{
		if(Status_Window_Frame.isEnabled()){Status_Window_Frame.setEnabled(false);}
		
		Calibrate_Window_Frame = new JFrame("FoxTek - Eso Fishing ReelBot : Calibration");
		Calibrate_Window_Frame.setLayout(new GridLayout(7, 1));
		Calibrate_Window_Frame.setSize(375, 250);
		Calibrate_Window_Frame.setAlwaysOnTop(true);
		Calibrate_Window_Frame.setResizable(false);
		if(!Required){Calibrate_Window_Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);}
		else{Calibrate_Window_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
		
		Calibrate_Window_Frame.add(new JLabel("1.Find a fishing spot."));
		Calibrate_Window_Frame.add(new JLabel("2.Select bait."));
		Calibrate_Window_Frame.add(new JLabel("3.Hit \"Start\"."));
		Calibrate_Window_Frame.add(new JLabel("4.Return to game."));
		Calibrate_Window_Frame.add(new JLabel("5.Keep prompt to start fishing up until complete."));
		
		JButton Start_Button = new JButton("Start");
		Start_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fish_Bot.Calibrate_Screen_Capture();						
				Calibrate_Window_Frame.setVisible(false);
				
				Calibrate_Window_Frame = null;
					
				if(Settings_Window_Frame != null){Settings_Window_Frame.setVisible(true);}
				else
				{
					if(Required)
					{
						new Thread(new Fish_Bot()).start();
					}
					Status_Window_Frame.setEnabled(true);
				}								
			}
		});
		Calibrate_Window_Frame.add(Start_Button);
		
		if(!Required)
		{
			JButton Cancel_Button = new JButton("Cancel");
			Cancel_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				Calibrate_Window_Frame.setVisible(false);
				
				Calibrate_Window_Frame = null;
					
				if(Settings_Window_Frame != null){Settings_Window_Frame.setVisible(true);}
				else
				{
					new Thread(new Fish_Bot()).start();
					
					Status_Window_Frame.setEnabled(true);
				}								
			}
		});
			Calibrate_Window_Frame.add(Cancel_Button);
		}
		
		Calibrate_Window_Frame.setVisible(true);
	}

	public static void Create_About_Window()
	{
		About_Window_Frame = new JFrame("FoxTek - Eso Fishing ReelBot : About");
		About_Window_Frame.setLayout(new GridLayout(2, 1));
		About_Window_Frame.setSize(375, 250);
		About_Window_Frame.setAlwaysOnTop(true);
		About_Window_Frame.setResizable(false);
		About_Window_Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		About_Window_Frame.add(new JLabel("Written By: ModdingFox"));
		//About_Window_Frame.add(new J));
		
		JButton Exit_Button = new JButton("Exit");
		Exit_Button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {					
			About_Window_Frame.setVisible(false);
			
			About_Window_Frame = null;
			
			if(Entry.Running_Settings.Capture_Zone != null){new Thread(new Fish_Bot()).start();}
				
			Status_Window_Frame.setVisible(true);								
		}
		});
		About_Window_Frame.add(Exit_Button);
		
		About_Window_Frame.setVisible(true);
	}
}
