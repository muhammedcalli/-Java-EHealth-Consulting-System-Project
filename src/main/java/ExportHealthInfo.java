package main.java;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.ResultSet;

/**
 * The type Export health info.
 */
public class ExportHealthInfo
{

    /**
     * Create pdf file with health info.
     *
     * @param userID the user id
     */
    public void createPDF(int userID)
    {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        //medication, allergies, other, preIllness
        String[] healthInfo = databaseConnector.getHealthInfo(userID);
        ResultSet resultSetUser = databaseConnector.getResultSetUser(userID);

        try {
            resultSetUser.first();
            String firstName = resultSetUser.getString("firstname");
            String lastName = resultSetUser.getString("lastname");

            Document document = new Document();

            String home = System.getProperty("user.home");
            PdfWriter.getInstance(document, new FileOutputStream(home + "/Downloads/" + firstName + "_Health_Information.pdf"));
            document.open();

            //Headline (Weiß noch nicht ganz wie)
            //document.addTitle(user.getFirstName()+ " " +user.getLastName()+ " Health Information");
            //document.addHeader("Test", "test");
            //document.add(new Header("Test", "Blablabla"));

            document.add(new Paragraph("Health Info for " + firstName + " " + lastName + ": "));

            //Chunk.NEWLINE == newline
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            //Adds info to PDF
            document.add(new Paragraph("Medication: " + healthInfo[0]));
            document.add(new Paragraph("Allergies: " + healthInfo[1]));
            document.add(new Paragraph("Other: " + healthInfo[2]));
            document.add(new Paragraph("Existing Illnesses: " + healthInfo[3]));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create txt file with health info.
     *
     * @param userID the user id
     */
    public void createTXT(int userID)
    {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        //medication, allergies, other, preIllness
        String[] healthInfo = databaseConnector.getHealthInfo(userID);
        ResultSet resultSetUser = databaseConnector.getResultSetUser(userID);

        try {
            resultSetUser.first();
            String firstName = resultSetUser.getString("firstname");
            String lastName = resultSetUser.getString("lastname");

            FileWriter writer;

            String home = System.getProperty("user.home");
            File document = new File(home + "/Downloads/" + firstName + "_Health_Information.txt");
            writer = new FileWriter(document, false);

            writer.write("Health Info for " + firstName + " " + lastName + ":\n\n");

            //Adds info to TXT
            writer.write("Medication: " + healthInfo[0] + "\n");
            writer.write("Allergies: " + healthInfo[1] + "\n");
            writer.write("Other: " + healthInfo[2] + "\n");
            writer.write("Existing Illnesses: " + healthInfo[3] + "\n");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
