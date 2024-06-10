/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package http;

import com.estg.core.Institution;
import com.estg.core.exceptions.InstitutionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import tp_pp.Classes.InstitutionImp;

/**
 *
 * @author fabio
 */
public class ImporterImp implements com.estg.io.Importer {

    private Institution institution;
    private String filePath;
    
    public ImporterImp(Institution institution, String filePath) {
        this.institution = institution;
        this.filePath = filePath;
    }

    @Override
    public void importData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (institution instanceof InstitutionImp) {
            InstitutionImp institution = (InstitutionImp) instn;
            if (institution.importData("src/Files/vehicles.json") && institution.importData("src/Files/aidboxArray.json")) {
                System.out.println("Success importing program vehicles");
            }
        }
    }

}
