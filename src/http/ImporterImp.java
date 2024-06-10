/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
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
 * Implementation of an importer for importing and exporting data related to
 * institutions.
 */
public class ImporterImp implements com.estg.io.Importer {

    /**
     * The institution for importing/exporting data.
     */
    private Institution institution;

    /**
     * Constructs a new ImporterImp with the specified institution.
     *
     * @param institution The institution for importing/exporting data.
     */
    public ImporterImp(Institution institution) {
        this.institution = institution;
    }

    /**
     * Imports data for the specified institution.
     *
     * @param instn The institution for importing data.
     * @throws FileNotFoundException if the file to import data from is not
     * found.
     * @throws IOException if an I/O error occurs while importing data.
     * @throws InstitutionException if there is an error related to the
     * institution during import.
     */
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

    /**
     * Exports data for the specified institution.
     *
     * @param instn The institution for exporting data.
     * @throws FileNotFoundException if the file to export data to is not found.
     * @throws IOException if an I/O error occurs while exporting data.
     * @throws InstitutionException if there is an error related to the
     * institution during export.
     */
    public void exportData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (institution instanceof InstitutionImp) {
            InstitutionImp institution = (InstitutionImp) instn;
            institution.export();
        }
    }
}
