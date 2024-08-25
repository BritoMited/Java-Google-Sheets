package org.example;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SheetsQuickstart {

    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "Google sheets exemplo";
    private static String SPREADSHEET_ID;

    static {
        try {
            SPREADSHEET_ID = getSpreadsheetId();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSpreadsheetId() throws FileNotFoundException {
        String id;

        try(BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\gusta\\IdeaProjects\\Java-GoogleSheets\\src\\main\\resources\\spreadsheet_id"))){

            id = bf.readLine();

//            while(line != null){
//                String[] fields = line.split(",");
//            } se casou houver mais valores depois


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream in = SheetsQuickstart.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),clientSecrets ,scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()
        ).authorize("user");

        return credential;
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException{
        Credential credential = authorize();

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    public static List<List<Object>> selectValuesRange(String range) throws IOException, GeneralSecurityException{
        sheetsService = getSheetsService();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        return values;

    }

}


















