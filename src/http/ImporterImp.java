/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package http;

import com.estg.core.Institution;
import com.estg.core.exceptions.InstitutionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import tp_pp.Classes.InstitutionImp;

/**
 *
 * @author fabio
 */
public class ImporterImp implements com.estg.io.Importer {

    private Institution institution;
    
    public ImporterImp(Institution institution) {
        this.institution = institution;
    }

    @Override
    public void importData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (institution instanceof InstitutionImp) {
            InstitutionImp institution = (InstitutionImp) instn;
            try {
                institution.importData();
            } catch (ParseException ex) {
                throw new InstitutionException();
            }
            
        }
    }
    
    
    public void exportData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if(institution instanceof InstitutionImp) {
            InstitutionImp institution = (InstitutionImp) instn;
            institution.export();
            }
        }
    }
