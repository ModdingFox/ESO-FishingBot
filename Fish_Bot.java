import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.naming.Referenceable;

public class Fish_Bot implements Runnable {
	public static boolean Run_Bot = false;
	public static boolean Bot_Running = false;
	
	enum Process_Phases_Enum { Search_For_Spot, Waiting_For_Reelin};
	enum Process_Result_Enum {Wait, Ready, Reset};
	
	public void run()
	{
		Fish_Bot.Run_Bot = true;
		Bot_Running = true;
		Fish_Bot.Fishing_Bot();
		Bot_Running = false;
	}
	
	public static void Fishing_Bot()
	{
		BufferedImage Screen_Capture = null;
		Process_Result_Enum Process_Result;
		
		while(Run_Bot)
		{ 	
			do
			{
				Timer(Generate_Random_Number(Entry.Running_Settings.Frame_Capture_Delay_Min, Entry.Running_Settings.Frame_Capture_Delay_Max));
				Screen_Capture = Capture_Screen(Entry.Running_Settings.Capture_Zone);
				Process_Result = Process_Capture(Process_Phases_Enum.Search_For_Spot, Screen_Capture, Entry.Running_Settings.Capture_Zone, Entry.Running_Settings.Text_Color_To_Serch_For, Entry.Running_Settings.Reelin_Marker_Color_To_Search_For);
				Print_State(Process_Result);
			}
			while((Process_Result == Process_Result_Enum.Wait) && Run_Bot);
			if(!Run_Bot){break;}
			Timer(Generate_Random_Number(Entry.Running_Settings.Next_Step_Delay_Min, Entry.Running_Settings.Next_Step_Delay_Max));
			Press_Button(Entry.Running_Settings.Key_To_Use, Entry.Running_Settings.Key_Press_Release_Delay_Min, Entry.Running_Settings.Key_Press_Release_Delay_Max);
			Timer(Generate_Random_Number(Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Min, Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Max));
			
			do
			{
				Timer(Generate_Random_Number(Entry.Running_Settings.Frame_Capture_Delay_Min, Entry.Running_Settings.Frame_Capture_Delay_Max));
				Screen_Capture = Capture_Screen(Entry.Running_Settings.Capture_Zone);
				Process_Result = Process_Capture(Process_Phases_Enum.Waiting_For_Reelin, Screen_Capture, Entry.Running_Settings.Capture_Zone, Entry.Running_Settings.Text_Color_To_Serch_For, Entry.Running_Settings.Reelin_Marker_Color_To_Search_For);
				Print_State(Process_Result);
			}
			while(Process_Result == Process_Result_Enum.Wait && Run_Bot);
			if(!Run_Bot){break;}
			if(Process_Result == Process_Result_Enum.Reset){continue;}
			Timer(Generate_Random_Number(Entry.Running_Settings.Next_Step_Delay_Min, Entry.Running_Settings.Next_Step_Delay_Max));
			Press_Button(Entry.Running_Settings.Key_To_Use, Entry.Running_Settings.Key_Press_Release_Delay_Min, Entry.Running_Settings.Key_Press_Release_Delay_Max);
			Timer(Generate_Random_Number(Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Min, Entry.Running_Settings.Reelin_To_Start_Fishing_State_Delay_Max));
		}
	}
	
	private static void Print_State(Process_Result_Enum State)
	{
		switch(State)
		{
		case Wait:
			UI.Set_Status_Label("Wait State");
			break;
		case Ready:
			UI.Set_Status_Label("Event Triggered");
			break;
		case Reset:
			UI.Set_Status_Label("Reset State");
			break;
		default:
			break;
		}
	}
	
	public static void Timer(int Wait_Time_In_Ms)
	{
		UI.Set_Time_Label("Waiting " + Wait_Time_In_Ms + " Milliseconds...");
		try {TimeUnit.MILLISECONDS.sleep(Wait_Time_In_Ms);}
		catch (InterruptedException e){System.console().writer().println("An error has occured waiting for next event");}
	}
	
	private static BufferedImage Capture_Screen(Rectangle Area_To_Capture)
	{
		UI.Set_Status_Label("Captureing Screen Area...");
		
		BufferedImage Screen_Capture = null;
		
		try
		{
			Robot Screen_Capture_Bot = new Robot();			
			Screen_Capture = Screen_Capture_Bot.createScreenCapture(Area_To_Capture);
		}
		catch(AWTException e){UI.Set_Status_Label("Error Captureing Screen");}
		
		return Screen_Capture;
	}
	
	public static void Calibrate_Screen_Capture()
	{
		UI.Set_Status_Label("Statring Calibreation...");
		Timer(10000);
		
		int Result[] = {-1, -1, -1};
		int Fish_Start_Pixel_Count = 0;
		int Fish_Reelin_Pixel_Count = 0;
		boolean Static_Pixels[];
		Color Reelin_Marker_Color = Entry.Running_Settings.Reelin_Marker_Color_To_Search_For;
		
		Rectangle ScreenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		
		UI.Set_Status_Label("Start Interaction Text...");
		
		do
		{
			try
			{
				Robot Screen_Capture_Bot = new Robot();
			
				BufferedImage Screen_Capture = Screen_Capture_Bot.createScreenCapture(ScreenRect);
				BufferedImage Compare_Screen_Capture;
				
				for(int Row = 0; Row < ScreenRect.getMaxY(); Row++)
				{
					boolean On_Specified_Color = false;
					int First_Gap_X_Position = -1;
					int Color_Gaps_Found = 0;
				
					for(int Col = 0; Col < ScreenRect.getMaxX(); Col++)
					{
						if(Screen_Capture.getRGB(Col, Row) == Entry.Running_Settings.Text_Color_To_Serch_For.getRGB())
						{
							if(!On_Specified_Color){On_Specified_Color = true;}
						}
						else
						{
							if(On_Specified_Color)
							{
								On_Specified_Color = false;
								Color_Gaps_Found++;
							
								if(Color_Gaps_Found == 1){First_Gap_X_Position = Col;}
							
								if(Color_Gaps_Found >= 4 && On_Specified_Color == false)
								{
									Result[0] = First_Gap_X_Position - 1;
									Result[1] = Row;
									Result[2] = Col - (First_Gap_X_Position - 1);
									break;
								}
							}
						}
					}
				
					if(Color_Gaps_Found >= 4){break;}
				}
				
				ScreenRect = new Rectangle(Result[0], Result[1], Result[2], 1);
				
				Screen_Capture = Screen_Capture_Bot.createScreenCapture(ScreenRect);
				Fish_Start_Pixel_Count = 0;
				for(int i = 0; i < Result[2]; i++)
				{
					if(Screen_Capture.getRGB(i, 0) == Entry.Running_Settings.Text_Color_To_Serch_For.getRGB())
					{
						Fish_Start_Pixel_Count++;
					}
				}
				
				Timer(Generate_Random_Number(Entry.Running_Settings.Next_Step_Delay_Min, Entry.Running_Settings.Next_Step_Delay_Max));
				Press_Button(Entry.Running_Settings.Key_To_Use, Entry.Running_Settings.Key_Press_Release_Delay_Min, Entry.Running_Settings.Key_Press_Release_Delay_Max);
				Timer(Generate_Random_Number(Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Min, Entry.Running_Settings.Start_Fishing_To_Reelin_State_Delay_Max));
				
				UI.Set_Status_Label("Reelin Interaction Text...");
				
				Screen_Capture = Screen_Capture_Bot.createScreenCapture(ScreenRect);	
				Fish_Reelin_Pixel_Count = 0;
				for(int i = 0; i < Result[2]; i++)
				{
					if(Screen_Capture.getRGB(i, 0) == Entry.Running_Settings.Text_Color_To_Serch_For.getRGB())
					{
						Fish_Reelin_Pixel_Count++;
					}
				}
				
				UI.Set_Status_Label("Detecting Static Pixels...");
				
				Static_Pixels = new boolean[Result[2]];
				for(int i = 0; i < Result[2]; i++){Static_Pixels[i] = true;}
				
				Screen_Capture = Screen_Capture_Bot.createScreenCapture(ScreenRect);
				Compare_Screen_Capture = Screen_Capture;
				
				for(int runs = 0; runs < 5; runs++)
				{
					Compare_Screen_Capture = Screen_Capture_Bot.createScreenCapture(ScreenRect);
					
					for(int i = 0; i < Result[2]; i++)
					{
						if(Screen_Capture.getRGB(i, 0) != Compare_Screen_Capture.getRGB(i, 0)){Static_Pixels[i] = false;}
					}
					
					Screen_Capture = Compare_Screen_Capture;
					Timer(100);
				}
				
				UI.Set_Status_Label("Reelin Trigger Color...");
				
				//while(!Reelin_Marker_Found)
				//{
					//gotta figure this one out
				/*
				 *maybe pull 100 samples per second for 5 seconds
				 *track min and max rgb values for the pixels in row
				 *save min and max to bot_settings
				 *
				 *Note: reelin animation will not be detected if its color is within the min and max ranges
				 *
				 */
				//}
			}
			catch(AWTException e){UI.Set_Status_Label("An error occured captureing the screen fo calibration");}
		}while(Result[0] == -1 || Result[1] == -1 || Result[2] == -1);
			
		Entry.Running_Settings.Capture_Zone = ScreenRect;
		Entry.Running_Settings.Fish_Start_Pixel_Count = Fish_Start_Pixel_Count;
		Entry.Running_Settings.Fish_Reelin_Pixel_Count = Fish_Reelin_Pixel_Count;
		Entry.Running_Settings.Reelin_Marker_Color_To_Search_For = Reelin_Marker_Color;
		
		UI.Set_Status_Label("Calibreation  Complete");
		
		return;
	}
	
	private static Process_Result_Enum Process_Capture(Process_Phases_Enum Process_Phase, BufferedImage Captured_Screen_Data, Rectangle Capture_Area, Color Text_Color, Color Reelin_Color_Marker)
	{
		UI.Set_Status_Label("Processing Frame...");
		
		int Color_Count = 0;
		int Red, Green, Blue;
		Color Capture_Screen_Color;
		
		if(Process_Phase == Process_Phases_Enum.Search_For_Spot)
		{
			for(int i = 0; i < Capture_Area.width; i++){if(Captured_Screen_Data.getRGB(i, 0) == Text_Color.getRGB()){Color_Count++;}}	
			if(Color_Count == Entry.Running_Settings.Fish_Start_Pixel_Count){return Process_Result_Enum.Ready;}//we use 5 here so if the user initiated fishing the bot will take over reel in
			else{return Process_Result_Enum.Wait;}//wait for the user to get to a spot
		}
		else
		{
			for(int i = 0; i < Capture_Area.width; i++)
			{
				Capture_Screen_Color = new Color(Captured_Screen_Data.getRGB(i, 0));
				Red = (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getRed() - Capture_Screen_Color.getRed()) >= 0 ? (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getRed() - Capture_Screen_Color.getRed()) : (Capture_Screen_Color.getRed() - Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getRed());
				Green = (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getGreen() - Capture_Screen_Color.getGreen()) >= 0 ? (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getGreen() - Capture_Screen_Color.getGreen()) : (Capture_Screen_Color.getGreen() - Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getGreen());
				Blue = (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getBlue() - Capture_Screen_Color.getBlue()) >= 0 ? (Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getBlue() - Capture_Screen_Color.getBlue()) : (Capture_Screen_Color.getBlue() - Entry.Running_Settings.Reelin_Marker_Color_To_Search_For.getBlue());
				
				if(Red <= Entry.Running_Settings.Reelin_Marker_RGB_Threshold[0])
				{
					if(Green <= Entry.Running_Settings.Reelin_Marker_RGB_Threshold[1])
					{	
						if(Blue <= Entry.Running_Settings.Reelin_Marker_RGB_Threshold[2])
						{
							return Process_Result_Enum.Ready;
						}
					}
				}
				if(Captured_Screen_Data.getRGB(i, 0) == Text_Color.getRGB()){Color_Count++;}	
			}
			
			if(Color_Count == Entry.Running_Settings.Fish_Reelin_Pixel_Count){return Process_Result_Enum.Wait;}//if count still 5 we are still waiting
			else{return Process_Result_Enum.Reset;}//user has left spot or ended fishing
		}
	}
	
	private static int Generate_Random_Number(int Min, int Max)
	{
		int Random_Number;
		
		Random Random_Number_Generator = new Random();
		Random_Number = Random_Number_Generator.nextInt(Max - Min + 1) + Min;
		return Random_Number;
	}
	
	private static void Press_Button(int Key_To_Use, int Press_Release_Delay_Min, int Press_Release_Dealy_Max)
	{
		UI.Set_Status_Label("Key Control Event...");
		try
		{
			Robot KeyPress_Bot = new Robot();
			
			KeyPress_Bot.keyPress(Key_To_Use);
			Timer(Generate_Random_Number(Press_Release_Delay_Min, Press_Release_Dealy_Max));
			KeyPress_Bot.keyRelease(Key_To_Use);
		}
		catch(AWTException e){UI.Set_Status_Label("An error has occured generating key event");}
	}
}
