package org.example;

import org.example.jaxws.server_topdown.*;

import java.lang.ref.Cleaner;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ESClient {

    static PersonService_Service pService = new PersonService_Service();
    static org.example.jaxws.server_topdown.PersonService pServiceProxy = pService.getPersonServiceImplPort();

    public static void addPersonMenu() {
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Podaj id");
            int id = sc.nextInt();
            System.out.println("Podaj imie");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.println("Podaj wiek");
            int wiek = sc.nextInt();
            pServiceProxy.addPerson(id, name, wiek);
            System.out.println("Dodano osobę o id " + id);
        } catch (InputMismatchException e) {
            System.out.println("Błędny typ.");
            addPersonMenu();
        } catch (PersonExistsEx_Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void updatePersonMenu(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Podaj id:");
            int id = sc.nextInt();
            System.out.println("Podaj imie:");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.println("Podaj wiek:");
            int wiek = sc.nextInt();
            pServiceProxy.updatePerson(id, name, wiek);
            System.out.println("Zaktualizowano dane osoby o id " + id);
        } catch (InputMismatchException e) {
            System.out.println("Błędny typ.");
            updatePersonMenu();
        } catch (PersonNotFoundEx_Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void deletePersonMenu(){
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Podaj id:");
            int id = sc.nextInt();
            pServiceProxy.deletePerson(id);
            System.out.println("Usunięto osobę o id " + id);
        } catch (InputMismatchException e) {
            System.out.println("Błędny typ.");
            updatePersonMenu();
        } catch (PersonNotFoundEx_Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void getPersonMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj id:");
        int id = sc.nextInt();
        try {
            org.example.jaxws.server_topdown.Person person = pServiceProxy.getPerson(id);
            System.out.println("Osoba " + person.getFirstName() + ", wiek = " + person.getAge());
        } catch (PersonNotFoundEx_Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void getPeopleMenu(){
        List<org.example.jaxws.server_topdown.Person> list = pServiceProxy.getAllPersons();
        System.out.println("Lista osób: ");
        for (org.example.jaxws.server_topdown.Person value : list) {
            System.out.println(value.getId() +" - "+ value.getFirstName() + " l." + value.getAge());
        }
    }

    public static void getCount(){
        int num = -1;
        num = pServiceProxy.countPersons();
        System.out.println("Liczba osób: " + num);
    }

    public static void menu(){

        while (true){

            System.out.println("Wybierz co chcesz zrobić:");
            System.out.println("1. Dodaj osobę");
            System.out.println("2. Zmień dane osoby");
            System.out.println("3. Usuń osobę");
            System.out.println("4. Pobierz dane osoby");
            System.out.println("5. Wyświetl wszystkie osoby");
            System.out.println("6. Zlicz osoby");
            System.out.println("7. Wyjdź.");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input){
                case ("1") ->
                        addPersonMenu();
                case ("2") ->
                        updatePersonMenu();
                case ("3") ->
                        deletePersonMenu();
                case ("4") ->
                        getPersonMenu();
                case ("5") ->
                        getPeopleMenu();
                case ("6") -> {
                        getCount();
                }
                case ("7") -> {
                    return;
                }
                default ->{
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }

            }
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        URL addr = new URL("http://localhost:8081/personservice?wsdl");
        menu();

    }
}
