package com.gsnotes.services;

import java.util.ArrayList;
import java.util.List;

import com.gsnotes.bo.Ligne;
import com.gsnotes.exceptionhandlers.ExcelExceptionHandler;

public interface IExcelDataService {
	
	List<Ligne> getExcelDataAsList() throws ExcelExceptionHandler ;
	int saveExcelData(List<Ligne> lignes);
	//public List<ArrayList<Object>> readFromExcel(String pFilename, int pSheet);
}