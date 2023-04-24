package org.example;

import org.example.jaxws.server_topdown.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ESClient {
    public static void main(String[] args) throws MalformedURLException,
            PersonNotFoundEx_Exception, PersonExistsEx_Exception {
        int num = -1;
        URL addr = new URL("http://localhost:8081/personservice?wsdl");
        PersonService_Service pService = new PersonService_Service();
        org.example.jaxws.server_topdown.PersonService pServiceProxy = pService.getPersonServiceImplPort();
        num = pServiceProxy.countPersons();
        System.out.println("Num of Persons = " + num);

        org.example.jaxws.server_topdown.Person person = pServiceProxy.getPerson(2);
//        pServiceProxy.addPerson(10, "Maria", 22);
        System.out.println("Person " + person.getFirstName() + ", id = " + person.getId());
        List<org.example.jaxws.server_topdown.Person> list = pServiceProxy.getAllPersons();
        System.out.println("Person list ");
        for (org.example.jaxws.server_topdown.Person value : list) {
            System.out.println(value.getFirstName());
        }
        pServiceProxy.deletePerson(1);
        List<org.example.jaxws.server_topdown.Person> list2 = pServiceProxy.getAllPersons();
        System.out.println("---------------");
        for (org.example.jaxws.server_topdown.Person value : list2) {
            System.out.println(value.getFirstName());
        }
    }
}
