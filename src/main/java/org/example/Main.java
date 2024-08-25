package org.example;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Scanner sc = new Scanner(System.in);

        start(sc);


    }

    public static void start(Scanner sc) throws GeneralSecurityException, IOException {
        System.out.println("Write the values range: ");
        String range = sc.nextLine();

        List<List<Object>> values = SheetsQuickstart.selectValuesRange(range);

        if(values == null || values.isEmpty()){
            System.out.println("Não foram encontrados dados.");
        }else{
            for(List coluna : values){
                System.out.printf("Local: %-15s | Data: %-10s | Nome: %-30s | Convenio: %-15s | Valor: %-10s | Status: %-10s%n",
                        coluna.get(0), coluna.get(1), coluna.get(2), coluna.get(3),
                        //coluna.get(4), coluna.get(5),
                        coluna.get(6), coluna.get(7));

                System.out.println();
            }
        }
    }
}