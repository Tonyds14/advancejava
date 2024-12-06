package com.svi.advancedjavatraining;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.svi.advancedjavatraining.Main.ProvinceData;
import com.svi.advancedjavatraining.config.*;
import com.svi.advancedjavatraining.constants.*;
import com.svi.advancedjavatraining.object.*;
import com.svi.advancedjavatraining.utils.*;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static CityData[] cityPathDatawithID;

	public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
		
		// TODO WORK AREA		
		List<RefDataStructure> mergedDataList = buildReferenceDataList();			
		// Print to validate elements of arraylist
//		int ctr = 1;
//		for (RefDataStructure data:mergedDataList) {
//			StringBuilder consolemssg = new StringBuilder();
//		    consolemssg.append(data.getRegionIDNum()+ ", " +data.getRegionShortName()+", "+data.getRegionLongName()+ ", " +data.getRegionKey()+ ", ");
//			consolemssg.append(data.getProvinceIDNum()+ ", " +data.getProvinceName()+ ", " +data.getProvinceRegion()+ ", " +data.getProvinceKey()+ ", ");
//			consolemssg.append(data.getCityIDNum()+ ", " +data.getCityName()+ ", " +data.getCityProvince()+ ", "+data.isCityInd());
//			String finalmssg = consolemssg.toString();
//			System.out.println(finalmssg);
//			System.out.println("record num: " +ctr);
//			ctr++;
//		    System.out.println();
//		}			
		 
		List<FinalPopulationData> populationDataFinal = buildDataListFromJSONFiles();
//		System.out.println("populationDataFinal.size(): "+populationDataFinal.size());
		// Print to validate elements of populationDataFinal        
//        for (FinalPopulationData data : populationDataFinal) {
//            System.out.println("Province: " + data.getProvince());
//            System.out.println("City: " + data.getCity());
//            System.out.println("Final Population: " + data.getPopulationInt());
//            System.out.println();
//        }  	
		
		List<RefDataStructure> mergedDataList2 = getPopulationDataByProvince(mergedDataList, populationDataFinal);
		// Print to validate elements of arraylist sample output of mergedDataList2
		//14, Region XI, Davao Region, XI, 66, Davao del Sur, XI, DAS, 1368, Digos, DAS, true, 169393
		//record num: 1368
//		int ctr = 1;
//		for (RefDataStructure data:mergedDataList2) {
//			StringBuilder consolemssg = new StringBuilder();
//		    consolemssg.append(data.getRegionIDNum()+ ", " +data.getRegionShortName()+", "+data.getRegionLongName()+ ", " +data.getRegionKey()+ ", ");
//			consolemssg.append(data.getProvinceIDNum()+ ", " +data.getProvinceName()+ ", " +data.getProvinceRegion()+ ", " +data.getProvinceKey()+ ", ");
//			consolemssg.append(data.getCityIDNum()+ ", " +data.getCityName()+ ", " +data.getCityProvince()+ ", "+data.isCityInd()+ ", ");
//			consolemssg.append(data.getJsonPopulationData());
//			String finalmssg = consolemssg.toString();
//			System.out.println(finalmssg);
//			System.out.println("record num: " +ctr);
//			ctr++;
//		    System.out.println();
//		}	
		
		buildFinalOutputData(mergedDataList2);
	
		// TODO END WORK AREA
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		long millis = TimeUnit.NANOSECONDS.toMillis(totalTime);

		long seconds = TimeUnit.NANOSECONDS.toSeconds(totalTime);

		long minutes = TimeUnit.NANOSECONDS.toMinutes(totalTime);

		long hours = TimeUnit.NANOSECONDS.toHours(totalTime);

		String time = String.format("%d:%02d:%02d.%03d", hours, minutes, seconds, millis);
		System.out.println(time);

	}
		
	
	public static List<RegionData> buildListRegionDataFromURL() {
		WebLoader webLoader = new WebLoader();
		//get Regions path Data from regions URL and put into arraylist using webLoader class
		List<Region> regionsPathData;
		List<RegionData> regionPathDatawithID = new ArrayList<>();
		try {
			regionsPathData = webLoader.getRegions();
			if (!regionsPathData.isEmpty()) {
				int regionIDNum = 1;
				for (Region data:regionsPathData) {
//					System.out.println("Region ID Num: " +regionIDNum);
//					System.out.println("Region Short Name: " + data.getShortName());
//					System.out.println("Region Long Name: " + data.getLongName());
//					System.out.println("Region Key: " + data.getKey());
//					System.out.println();			    
					    
					RegionData regionData = new RegionData(regionIDNum, data.getShortName(), data.getLongName(), data.getKey());
					regionData.setRegionIDNum(regionIDNum);
					regionData.setRegionShortName(data.getShortName());
					regionData.setRegionLongName(data.getLongName());
					regionData.setRegionKey(data.getKey());	
					regionPathDatawithID.add(regionData);
					regionIDNum++;			    
				}
			} else {
				System.out.println("No Data in regionsPathData URL");
			}	
			return regionPathDatawithID;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<ProvinceData> buildListProvinceDataFromURL() {
		WebLoader webLoader = new WebLoader();
		//get Province path Data from province URL and put into arraylist using webLoader class		
		List<Province> provincePathData;
		try {
			provincePathData = webLoader.getProvinces();
			List<ProvinceData> provincePathDatawithID = new ArrayList<>();
			if (!provincePathData.isEmpty()) {
				int provinceIDNum = 1;
				for (Province data:provincePathData) {
//					System.out.println("Province ID Num: " + provinceIDNum);
//					System.out.println("Province Name: " + data.getName());
//					System.out.println("Province Region: " + data.getRegion());
//					System.out.println("Province Key: " + data.getKey());
//					System.out.println();			    
					    
					ProvinceData provinceData = new ProvinceData(provinceIDNum, data.getName(), data.getRegion(), data.getKey());
					provinceData.setProvinceIDNum(provinceIDNum);
					provinceData.setProvinceName(data.getName());
					provinceData.setProvinceRegion(data.getRegion());
					provinceData.setProvinceKey(data.getKey());	
					provincePathDatawithID.add(provinceData);
					provinceIDNum++;			    
				}
			} else {
				System.out.println("No Data in provincePathData URL");
			}	
			return provincePathDatawithID;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;				
	}

	public static List<CityData> buildListCityDataFromURL() {
		WebLoader webLoader = new WebLoader();
		//get Cities Path Data from cities URL and put into arraylist using webLoader class
		List<City> citiesPathData;
		try {
			citiesPathData = webLoader.getCities();
			List<CityData> citiesPathDatawithID = new ArrayList<>();
			if (!citiesPathData.isEmpty()) {
				int cityIDNum = 1;
				for (City data:citiesPathData) {
//					System.out.println("City ID Num: " + cityIDNum);
//					System.out.println("City Name: " + data.getName());
//					System.out.println("City Provice: " + data.getProvince());
//					System.out.println("City Indicator: " + data.isCity());
//					System.out.println();			    
					    
					CityData cityData = new CityData(cityIDNum, data.getName(), data.getProvince(), data.isCity());
					cityData.setCityIDNum(cityIDNum);
					cityData.setCityName(data.getName());
					cityData.setCityProvince(data.getProvince());
					cityData.setCityInd(data.isCity());	
					citiesPathDatawithID.add(cityData);
					cityIDNum++;			    
				}				
			} else {
				System.out.println("No Data in citiesPathData URL");
			}
			return citiesPathDatawithID;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
	}
	
	
	public static class RegionData {
	    private int regionIDNum;
	    private String regionShortName;
	    private String regionLongName;
	    private String regionKey;

	    public RegionData(int regionIDNum, String regionShortName, String regionLongName, String regionKey) {
	        this.regionIDNum = regionIDNum;
	        this.regionShortName = regionShortName;
	        this.regionLongName = regionLongName;
	        this.regionKey = regionKey;
	    }

		public int getRegionIDNum() {
			return regionIDNum;
		}

		public void setRegionIDNum(int regionIDNum) {
	        this.regionIDNum = regionIDNum;
	    }

		public String getRegionShortName() {
			return regionShortName;
		}

		public void setRegionShortName(String regionShortName) {
	        this.regionShortName = regionShortName;
	    }

		public String getRegionLongName() {
			return regionLongName;
		}

		public void setRegionLongName(String regionLongName) {
	        this.regionLongName = regionLongName;
	    }

		public String getRegionKey() {
			return regionKey;
		}

		public void setRegionKey(String regionKey) {
	        this.regionKey = regionKey;
	    }

	    
	}
	
	public static class ProvinceData {
	    private int provinceIDNum;
	    private String provinceName;
	    private String provinceRegion;
	    private String provinceKey;

	    public ProvinceData(int provinceIDNum, String provinceName, String provinceRegion, String provinceKey) {
	        this.provinceIDNum = provinceIDNum;
	        this.provinceName = provinceName;
	        this.provinceRegion = provinceRegion;
	        this.provinceKey = provinceKey;
	    }

		public int getProvinceIDNum() {
			return provinceIDNum;
		}

		public void setProvinceIDNum(int provinceIDNum) {
			this.provinceIDNum = provinceIDNum;
		}

		public String getProvinceName() {
			return provinceName;
		}

		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}

		public String getProvinceRegion() {
			return provinceRegion;
		}

		public void setProvinceRegion(String provinceRegion) {
			this.provinceRegion = provinceRegion;
		}

		public String getProvinceKey() {
			return provinceKey;
		}

		public void setProvinceKey(String provinceKey) {
			this.provinceKey = provinceKey;
		}    
	    
	}
	
	public static class CityData {
	    private int cityIDNum;
	    private String cityName;
	    private String cityProvince;
	    private boolean cityInd;

	    public CityData(int cityIDNum, String cityName,String cityProvince,boolean cityInd) {
	        this.cityIDNum = cityIDNum;
	        this.cityName = cityName;
	        this.cityProvince = cityProvince;
	        this.cityInd = cityInd;	        
	        
	    }

		public int getCityIDNum() {
			return cityIDNum;
		}

		public void setCityIDNum(int cityIDNum) {
			this.cityIDNum = cityIDNum;
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public String getCityProvince() {
			return cityProvince;
		}

		public void setCityProvince(String cityProvince) {
			this.cityProvince = cityProvince;
		}

		public boolean isCityInd() {
			return cityInd;
		}

		public void setCityInd(boolean cityInd) {
			this.cityInd = cityInd;
		}
	    
	}
	
	
	public static List<RefDataStructure> buildReferenceDataList() {
		List<RefDataStructure> mergedDataList = new ArrayList<>();
		List<RegionData> regionPathDatawithID = buildListRegionDataFromURL();		
		List<ProvinceData> provincePathDatawithID = buildListProvinceDataFromURL();	
		List<CityData> cityPathDatawithID = buildListCityDataFromURL();	
		
	    int	refRegionIDNum = 0;
		String refRegionShortName = "";
		String refRegionLongName = "";
		String refRegionKey = "";
		
	    int	refProvinceIDNum = 0;
		String refProvinceName = "";
		String refProvinceRegion = "";
		String refProvinceKey = "";
			
        int	refCityIDNum = 0;
        String	refCityName = "";
    	String	refCityProvince = "";
    	boolean	refisCityInd = false;	
    	
    	int refJsonPopData = 0;
			
//		RefDataStructure mergedData = new RefDataStructure(refRegionIDNum, refRegionShortName, refRegionLongName, refRegionKey
//				, refProvinceIDNum, refProvinceName, refProvinceRegion, refProvinceKey
//				, refCityIDNum, refCityName, refCityProvince, refisCityInd);	
		
		// Iterate over the regionPathDatawithID list
		for (RegionData regionData : regionPathDatawithID) {
		    String regionKey = regionData.getRegionKey();
		    
		    refRegionIDNum = regionData.getRegionIDNum();
			refRegionShortName = regionData.getRegionShortName();
			refRegionLongName = regionData.getRegionLongName();
			refRegionKey = regionData.getRegionKey();

		    // Find the matching provinceData based on the regionKey
		    for (ProvinceData provinceData : provincePathDatawithID) {
		    	String provRegionKey = provinceData.getProvinceRegion();
		    	String provCityKey = provinceData.getProvinceKey();
		    	
		    	refProvinceIDNum = provinceData.getProvinceIDNum();
				refProvinceName = provinceData.getProvinceName();
				refProvinceRegion = provinceData.getProvinceRegion();
				refProvinceKey = provinceData.getProvinceKey();

		        // If the keys match, proceed to find matching cityData
		        if (regionKey.equals(provRegionKey)) {
		            // Iterate over the cityPathDatawithID list
		            for (CityData cityData : cityPathDatawithID) {
		                String cityProvKey = cityData.getCityProvince();
		                
		                refCityIDNum = cityData.getCityIDNum();
		        		refCityName = cityData.getCityName();
		        		refCityProvince = cityData.getCityProvince();
		        		refisCityInd = cityData.isCityInd();		                

		                // If the keys match, create a merged object and add it to the mergedDataList
		                if (provCityKey.equals(cityProvKey)) {
		                	RefDataStructure mergedData = new RefDataStructure(refRegionIDNum, refRegionShortName, refRegionLongName, refRegionKey
		                											, refProvinceIDNum, refProvinceName, refProvinceRegion, refProvinceKey
		                											, refCityIDNum, refCityName, refCityProvince, refisCityInd, refJsonPopData);	                    
		                	
		                	mergedDataList.add(mergedData);		           
		                } //if (provinceKey.equals(cityKey))
		            } //for (CityData cityData 
		        }//if (regionKey.equals(provinceKey))
		    } //for (ProvinceData provinceData
		}//for (RegionData regionData
	
		return mergedDataList;
	}
	
	
	public static class RefDataStructure{
		private int regionIDNum;
		private String regionShortName;
		private String regionLongName;
		private String regionKey;
		private int provinceIDNum;
		private String provinceName;
		private String provinceRegion;
		private String provinceKey;
		private int cityIDNum;
		private String cityName;
		private String cityProvince;
		private boolean cityInd;
		private int jsonPopulationData;
		
		public RefDataStructure(int regionIDNum, String regionShortName, String regionLongName, String regionKey
				,int provinceIDNum, String provinceName, String provinceRegion, String provinceKey
				,int cityIDNum, String cityName, String cityProvince, boolean cityInd,int jsonPopulationData ) {
			this.regionIDNum = regionIDNum;
			this.regionShortName = regionShortName;
			this.regionLongName = regionLongName;
			this.regionKey = regionKey;
			this.provinceIDNum = provinceIDNum;
			this.provinceName = provinceName;
			this.provinceRegion = provinceRegion;
			this.provinceKey = provinceKey;
			this.cityIDNum = cityIDNum;   
			this.cityName = cityName; 
			this.cityProvince = cityProvince; 
			this.cityInd = cityInd;	
			this.jsonPopulationData = jsonPopulationData;
		}

		public int getRegionIDNum() {
			return regionIDNum;
		}

		public void setRegionIDNum(int regionIDNum) {
			this.regionIDNum = regionIDNum;
		}

		public String getRegionShortName() {
			return regionShortName;
		}

		public void setRegionShortName(String regionShortName) {
			this.regionShortName = regionShortName;
		}

		public String getRegionLongName() {
			return regionLongName;
		}

		public void setRegionLongName(String regionLongName) {
			this.regionLongName = regionLongName;
		}

		public String getRegionKey() {
			return regionKey;
		}

		public void setRegionKey(String regionKey) {
			this.regionKey = regionKey;
		}

		public int getProvinceIDNum() {
			return provinceIDNum;
		}

		public void setProvinceIDNum(int provinceIDNum) {
			this.provinceIDNum = provinceIDNum;
		}

		public String getProvinceName() {
			return provinceName;
		}

		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}

		public String getProvinceRegion() {
			return provinceRegion;
		}

		public void setProvinceRegion(String provinceRegion) {
			this.provinceRegion = provinceRegion;
		}

		public String getProvinceKey() {
			return provinceKey;
		}

		public void setProvinceKey(String provinceKey) {
			this.provinceKey = provinceKey;
		}

		public int getCityIDNum() {
			return cityIDNum;
		}

		public void setCityIDNum(int cityIDNum) {
			this.cityIDNum = cityIDNum;
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public String getCityProvince() {
			return cityProvince;
		}

		public void setCityProvince(String cityProvince) {
			this.cityProvince = cityProvince;
		}

		public boolean isCityInd() {
			return cityInd;
		}

		public void setCityInd(boolean cityInd) {
			this.cityInd = cityInd;
		}    
		
		public int getJsonPopulationData() {
			return jsonPopulationData;
		}

		public void setJsonPopulationData(int jsonPopulationData) {
			this.jsonPopulationData = jsonPopulationData;
		}

	}
	
	
	public static List<FinalPopulationData> buildDataListFromJSONFiles() {
		Path inputFolderPath = Paths.get("input");
		// Print the absolute path
//		System.out.println("inputFolderPath: " +inputFolderPath.toAbsolutePath());      
        String inputFolderPathString = inputFolderPath.toString();
        
        List<FinalPopulationData> populationDataFinal = new ArrayList<>();
        
//      List<FolderInfo> arrayListJSON = new ArrayList<>();
//      ArrayList<PopulationData> jsonDataElement = 0;
		List<FolderInfo> listOfFolderJSONFiles = new ArrayList<>();
		File folder = new File(inputFolderPathString);
		int populationJSONData = 0;
//      List<PopulationData> jsonData;
//      ArrayList<PopulationData> jsonDataElement =  new ArrayList<>();
      
		int JSONLENGTH = 5;

		if (folder.exists() && folder.isDirectory()) {
			File[] subfolders = folder.listFiles();

			if (subfolders != null) {
				for (File subfolder : subfolders) {
					if (subfolder.isDirectory()) {
						String folderName = subfolder.getName();
						String[] jsonFiles = subfolder.list((dir, name) -> name.endsWith(".json"));
                      
						String provinceName = folderName;
//                      System.out.println("\nprovinceName :" +provinceName);

						if (jsonFiles != null && jsonFiles.length > 0) {
							FolderInfo folderInfo = new FolderInfo(folderName, jsonFiles);
//							List<FinalPopulationData> populationDataFinal = new ArrayList<>();
							
							listOfFolderJSONFiles.add(folderInfo);
                          
							for (String jsonData : jsonFiles) {
								String cityName = jsonData.substring(0, (jsonData.length() - JSONLENGTH));
//                          	System.out.println("CityName :" +cityName);                          	
								populationJSONData = getDataFromJSONFile(provinceName, cityName);   
								
							FinalPopulationData data = new FinalPopulationData(provinceName,cityName,populationJSONData);
							populationDataFinal.add(data);
							
                          }
                      }
                  }
              }
          }
      }
	
	return populationDataFinal;
		
//        buildInputJSONFolderAndFilesList(inputFolderPathString, populationDataFinal);
//		return populationDataFinal;     

	}	
	
	public static class FolderInfo {
        private String folderName;
        private String[] jsonFileName;
     
        public FolderInfo(String folderName, String[] jsonFileName) {
            this.folderName = folderName;
            this.jsonFileName = jsonFileName;
        }

        public String getFolderName() {
            return folderName;
        }

        public String[] getJsonFileName() {
            return jsonFileName;
        }
    }
	
	
	
	public static class FinalPopulationData {
		private String province;
		private String city;
		private int populationInt;

		public FinalPopulationData (String province, String city, int populationInt) {
			super();
			this.province = province;
			this.city = city;
			this.populationInt = populationInt;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public int getPopulationInt() {
			return populationInt;
		}

		public void setPopulationInt(int populationInt) {
			this.populationInt = populationInt;
		}

		
		
		
	}
	
		
	
	public static int getDataFromJSONFile(String provinceKey, String cityKey) {
		//get data from Input folder into arrayList
//		String provinceKey = "Agusan del Norte";
//		String cityKey ="Cabadbaran";
		CityInfo cityInfoFromInputFolder;
		int populationJSONData = 0;
//		ArrayList<FinalPopulationData> populationDataFinal =  new ArrayList<>();
		JSONFileReader inputDataJSON = new JSONFileReader(provinceKey, cityKey);
		try {
			cityInfoFromInputFolder = inputDataJSON.getData();
//			System.out.println("city: "+cityInfoFromInputFolder.getCity());
//			System.out.println("admin_name: "+cityInfoFromInputFolder.getAdmin_name());
//			System.out.println("population: "+cityInfoFromInputFolder.getPopulation());
//			System.out.println("populationproper: "+cityInfoFromInputFolder.getPopulation_proper());
			
			populationJSONData = (int) Math.round(cityInfoFromInputFolder.getPopulation());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return populationJSONData;	
	}	
	

	public static List<RefDataStructure> getPopulationDataByProvince(List<RefDataStructure> mergedDataList, List<FinalPopulationData> populationDataFinal) {
		int NTHREADS = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS); 
		CompletionService<List<FinalPopulationData>> completionService = new ExecutorCompletionService<>(executorService);
		
		for (RefDataStructure mergeData : mergedDataList) {
			String provinceKey = mergeData.getProvinceName();
			String cityKey = mergeData.getCityName();
			
			for (FinalPopulationData populationData : populationDataFinal) {
		        String populationProvinceName = populationData.getProvince();
		        String populationCityName = populationData.getCity();
		      
		        if(provinceKey.equals(populationProvinceName)&& cityKey.equals(populationCityName)) {
		        	mergeData.setJsonPopulationData(populationData.getPopulationInt());
		        	break;
		        }
			}			
		}
		
		return mergedDataList;		
		
//		for (final City city : citiesPathData) {
//			completionService.submit(
//					new Callable<List<PopulationData>>() {						
//						public List<PopulationData> call() throws Exception {
//							return getDataFromJSONFile(provinceName, cityName, populationData);
//						}
//				}					
//			);
//		}
		
	}
	
	public static void buildFinalOutputData(List<RefDataStructure> mergedDataList2) throws IOException {
		List<PopulationData> finalPopulationData = new ArrayList<>();
		PopulationFileWriter writeFile = new PopulationFileWriter();
		int sumPopulation = 0;
		
		for (RefDataStructure data : mergedDataList2) {
		    String provinceName = data.getProvinceName();
		    String cityName = data.getCityName();
		    int jsonPopData = data.getJsonPopulationData();
		    
		    sumPopulation = jsonPopData + sumPopulation;

		    PopulationData populationData = new PopulationData(provinceName, cityName, jsonPopData);
		    		    
		    finalPopulationData.add(populationData); 
		    writeFile.addPopulationRecord(populationData);	 
		}	
		System.out.println("\nTotal Population: " +sumPopulation);
		
		writeFile.writeToFile();			
		System.out.println("\nOutput File: \"PopulationData.xlsx\" created");
		Path outputFolderPath = Paths.get("output");
		System.out.println("Output FolderPath: " +outputFolderPath.toAbsolutePath());

		
		
//		System.out.println("finalOutputData.size(): " +finalPopulationData.size());
//		int ctr = 1;
//		for (PopulationData data : populationData) {
//			System.out.println("record num: " +ctr +": " +data.getProvince() +", " +data.getCity() +", " +data.getPopulation());
//			System.out.println();
//			ctr++;
//			
//		}
		
	}
	
}
