package com.gsnotes.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.Ligne;
import com.gsnotes.bo.Niveau;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.dao.InscriptionDao;
import com.gsnotes.dao.LigneDao;
import com.gsnotes.exceptionhandlers.ExcelExceptionHandler;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.services.IExcelDataService;
import com.gsnotes.services.IPersonService;

@Service
public class ExcelDataServiceImpl implements IExcelDataService {

	@Value("${app.upload.file:${user.home}}")
	public String EXCEL_FILE_PATH;

	@Autowired
	LigneDao repo;
	
	@Autowired
	IEtudiantService etudiantService = new EtudiantServiceImpl();
	
	@Autowired
	IEtudiantDao etudiantDao ;
	
	@Autowired
	INiveauDao niveauDao ;
	
	@Autowired
	InscriptionDao inscDao ;
	

	Workbook workbook ;
		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();
        
		public List<Ligne> getExcelDataAsList() throws ExcelExceptionHandler{
			

		// Create the Workbook
		try {
			workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		List<Object> list = new ArrayList<Object>();
		List<Ligne> invList ;


		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");
		
		//verifier que nbre de colonnes == 6
		if(noOfColumns == 6) {

		// Using for-each loop to iterate over the rows and columns
		for (Row row : sheet) {
			for (Cell cell : row) {
				if(cell.getCellType()== CellType.STRING) {
				list.add(cell.getStringCellValue());
				}
				else if(cell.getCellType()== CellType.NUMERIC) {
					int cellValue = (int) cell.getNumericCellValue();
					list.add(cellValue);
				}
			}
		}
		System.out.println(list);
		if(verify(list)) {
			}
			else throw new ExcelExceptionHandler("les types des données sont incorrectes");
		System.out.println(list);
		// filling excel data and creating list as List<Invoice>
		 invList = createList(list, noOfColumns);

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		
		else throw new ExcelExceptionHandler("Format non acceptable : nombre de colonnes ");
		System.out.println(invList);
		if(!verifyType(invList)) {
			throw new ExcelExceptionHandler("type d'inscription incompatible avec la base de données");
		}
		if(!verifyNiveau(invList)) {
			throw new ExcelExceptionHandler("niveau ou type d'inscription incorrecte");
		}
		return invList;
		
	}
		
		
		
    
	private List<Ligne> createList(List<Object> excelData, int noOfColumns) {

		ArrayList<Ligne> ligneList = new ArrayList<Ligne>();

		int i = noOfColumns;
		do {
			Ligne ligne = new Ligne();
try {
	int id = (int) excelData.get(i);
			ligne.setId(id);
			ligne.setCne(excelData.get(i + 1).toString());
			ligne.setnom(excelData.get(i + 2).toString());
			ligne.setPrenom(excelData.get(i + 3).toString());
			int niveau = (int) excelData.get(i+4);
			ligne.setNiveau(niveau);
			ligne.setType(excelData.get(i+5).toString());
}catch(Exception e) {
	e.printStackTrace();
}
			ligneList.add(ligne);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return ligneList;
	}
	
	public boolean verify(List<Object> list2) {
		Iterator<Object> it = list2.iterator();
		ArrayList<String> row = new ArrayList();
		for(int i=0;i<6;i++) {
		 row.add((String) it.next());
		 System.out.println(row);
		}
		List<String> list = new ArrayList();
		String arr[] = {"ID ETUDIANT","CNE","NOM","PRENOM","ID NIVEAU ACTUEL","TYPE"};
		Collections.addAll(list, arr);
		if(!row.equals(list))
			return false ;
		return true ;
	}
	
	
	
	public boolean verifyType(List<Ligne> data) {
		Iterator<Ligne> it = data.iterator();
	
		
		
		while(it.hasNext()) {
			Ligne row = it.next();
			String typeInsc = row.getType();
			int idInsc =  row.getId();
			Etudiant etu = new Etudiant();
			try {
			etu =  etudiantDao.getById((long) idInsc);
			System.out.println(etu);
			if(!etu.equals(null) && typeInsc.equals("REINSCRIPTION")) {
				System.out.println("reinsc");
				continue ;
			
			}
			}catch(EntityNotFoundException ex) {
				System.out.println(typeInsc);
			if(typeInsc.equals("INSCRIPTION")) {
				System.out.println("insc");
				continue ;
			}
			else return false ;	
		}
		}

		return true;
	}
	
	
	
	public boolean verifyNiveau(List<Ligne> data) {
		Iterator<Ligne> it = data.iterator();
		while(it.hasNext()) {
			Ligne row = it.next();
			int idNiv = row.getNiveau();
			//verifier si le niveau existe dans la bd
			Niveau niv = niveauDao.getById((long) idNiv);
			if(niv.equals(null)) {
				return false ;
			}
			
			//trouver l'id correspond a l'etudiant dans la bd
			if(row.getType().equals("INSCRIPTION")) {
				continue ;
			}
			else {	
				System.out.println(row.getType());
			int idEtu = row.getId()	;
			InscriptionAnnuelle ins = inscDao.getInscriptionAnnuelleByEtudiant(etudiantDao.getById((long) idEtu));
			Long idfound = ins.getNiveau().getIdNiveau() ;
			if(ins.getValidation().equals("NV")) {
				
				if(idfound!=idNiv) {
					System.out.println(idfound);
					System.out.println(idNiv);
					System.out.println("nv");
					return false ;
				}
			}
			else if(idfound==1 || idfound==3 || idfound==4 || idfound==6 || idfound==7 || idfound==13 || idfound==14
					|| idfound==21 || idfound==23 || idfound==24 || idfound==26 || idfound==27) {
				if(idNiv!=idfound+1) {
					System.out.println("**");
					return false ;
				}
				
			}
			else if((idfound==2 && idNiv!=12) || (idfound==16 && idNiv!=21) || (idfound==29 && idNiv!=25)) {
				return false ;
			}
	}
		}
		return true ;
	}
	
	
	@Override
	public int saveExcelData(List<Ligne> lignes) {
		lignes = repo.saveAll(lignes);
		return lignes.size();
	}

}