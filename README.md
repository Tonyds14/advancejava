java program that will perform combination of loading data from WebService (JSON) and File (JSON).
	- Source Input data from "Input" folder and from website.

had used the ff; List, JSON, ExecutorService, CompletionService

Pre-requisites:
1. Project File is given - "FileAccessMultiThreading.zip"

Goal:
2. insert line of codes in "Main" class section marked between "// TODO WORK AREA" and "// TODO END WORK AREA" to perform the ff;
-	Using the WebLoader (com.svi.advancedjavatraining.utils.WebLoader), load all the provincial and city data
-	Match Cities to their corresponding Province Data (key)
-	Match Population Data using the Province Name as folder, City as filename (using JSONFileReader, com.svi.advancedjavatraining.utils.JSONFileReader)
-	Add the sum of all loaded population data and display in the end
-	write all Loaded data to Excel File (com.svi.advancedjavatraining.utils.PopulationFileWriter)

Sample Input:

![Structure_of_Data_from_website](https://github.com/user-attachments/assets/7db731af-4a49-49c7-9cdb-2fd5818ec68f)
![Structure_of_Data_from_Input_Folder](https://github.com/user-attachments/assets/1575b79b-b578-4e30-add0-ac4de475d063)

Sample Output:

![Sample_Console_output](https://github.com/user-attachments/assets/234ea7ec-13e0-486c-a5d5-5029d2ec60d1)
![Sample_Data_of_Output_Excel](https://github.com/user-attachments/assets/7ce66989-535b-4753-8698-c6d0942d8be8)
