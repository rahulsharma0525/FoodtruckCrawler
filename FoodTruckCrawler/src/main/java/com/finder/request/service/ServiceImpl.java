package com.finder.request.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finder.request.TruckClient;
import com.finder.request.constants.TruckConstants;
import com.finder.request.vo.TruckDetails;

public class ServiceImpl {
	
	TruckClient client = new TruckClient();
	StringBuilder result = new StringBuilder();
	int start=0;
	int limit=10;
	String input="";

	public void  process(){

		try(Scanner scan= new Scanner(System.in)) {
			
			do{
				StringBuilder res= new StringBuilder(TruckConstants.api);
				HttpURLConnection conn= client.getConnection(res,"GET",getCurrentData(limit,start,TruckConstants.sortBy));
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
				
				//check if it is an empty array
				if(result.length()==2){
					System.out.println("No more Data Set To read");
					break;
				}
				
				//convert the string into the pojo
				ObjectMapper mapper = new ObjectMapper();
				TruckDetails[] trucks = mapper.readValue(result.toString(), TruckDetails[].class);
				
				//print the array
				printDetails(trucks);
				
				//reset the result for next iteration
				result.setLength(0);				
				start=limit+start;
				
				System.out.println("\n\nPlese press n for next page");
				input =scan.nextLine();
				
			}while( input.equals("n"));
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void printDetails(TruckDetails[] trucks) {
		
		for(String col : TruckConstants.columns){
			System.out.print(col.toUpperCase()+"\t\t\t\t\t\t\t\t\t\t\t\t");
		}	
		
		System.out.print("\n");
		for(int i=0;i<4;i++)
		System.out.print("==============================================================");

		for(TruckDetails truck: trucks){
			
			System.out.print("\n"+truck.getApplicant());
			//appending some spaces for formatting
			for(int i=0;i<96-truck.getApplicant().length();i++)
				System.out.print(" ");
			System.out.print(truck.getLocation());	
		}
		System.out.print("\n");
		for(int i=0;i<4;i++)
		System.out.print("==============================================================");		
	}

/*	private String StringTrimmer(String input) {

		String elipsis="...";
		int stringLimit=15;
		
		if(input =="" || input == null)
			return "";
		
		if( input.length()>stringLimit)
			input= input.substring(0, 12)+elipsis;
			
		else if	(input.length()<stringLimit){
			for(int i=0;i<stringLimit-input.length();i++)
				input=input+" ";
		}
			
		return input;
	}*/

	
	/*form the url query by adding the parameters
	 * Get the current time in hours
	 * get the day of the month
	 * order by clause
	 * limit and offset
	 * */
	private String getCurrentData(int limit, int offset, String sortBy){
		
		try{
		
			StringBuilder params =new StringBuilder();
					
			int time = 	LocalDateTime.now().getHour() ==0?24:LocalDateTime.now().getHour();
			
			DayOfWeek  day =LocalDateTime.now().getDayOfWeek();
			params.append("$where=");
			
			params.append(URLEncoder.encode(TruckConstants.end24,"UTF-8"));
			params.append(URLEncoder.encode(">","UTF-8"));
			params.append(URLEncoder.encode("'"+String.valueOf(time)+":00'","UTF-8"));
			
			params.append(URLEncoder.encode(" and ","UTF-8"));
			params.append(URLEncoder.encode(TruckConstants.dayorder,"UTF-8"));
			params.append(URLEncoder.encode("=","UTF-8"));
			params.append(day.getValue());
					
			params.append("&");
			params.append("$limit");
			params.append("=");
			params.append(limit);
			
			params.append("&");
			params.append("$offset");
			params.append("=");
			params.append(offset);
			
			params.append("&");
			params.append("$order");
			params.append("=");
			params.append(sortBy);
					
			return params.toString();
		}catch(UnsupportedEncodingException e){	
			System.out.println("Endoing of Parameter Failed"+e.getCause());
			return null;
		}
		
	}

}
