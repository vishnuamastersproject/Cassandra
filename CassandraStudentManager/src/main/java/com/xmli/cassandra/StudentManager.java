package com.xmli.cassandra;

import com.datastax.driver.core.Cluster;
import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.ManagerFactoryBuilder;
import info.archinnov.achilles.generated.manager.Student_Manager;

public class StudentManager {

  private Student_Manager achillesStudentManager;
  private State currentState;

  public static void main(String[] args) {
    new StudentManager().run(args);
  }

  private void run(String[] args) {
    if (args.length != 2) {
      System.out.println("Run with this arguments: cassandraHost clusterName");
      return;
    }
    init(args[0], args[1]);
    while (true) {
      try {
        switch (currentState) {
          case MAIN:
            mainMenu();
            break;
          case INSERT:
            insertMenu();
            break;
          case UPDATE:
            updateMenu();
            break;
          case DELETE:
            deleteMenu();
            break;
          case GET:
            getMenu();
            break;
          default:
            System.out.println("State is illegal. going to main state");
            currentState = State.MAIN;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void getMenu() {
    System.out.println("Insert student id to get student info or b to go back.");
    String input = System.console().readLine();
    if ("b".equalsIgnoreCase(input)) {
      currentState = State.MAIN;
    } else {
      Student student = achillesStudentManager.crud().findById(input).get();
      if (student == null) {
        System.out.println("Can't find student with id: " + input);
      } else {
        System.out.println("Student info is: " + student);
      }
    }
  }

  private void deleteMenu() {
    System.out.println("Enter student id to delete or b to go back.");
    String input = System.console().readLine();
    if ("b".equalsIgnoreCase(input)) {
      currentState = State.MAIN;
    } else {
      achillesStudentManager.crud().deleteById(input).execute();
      System.out.println("Student deleted successfully");
    }
  }

  private void insertMenu() {
    System.out.println("Write student info like this to insert: id,firstName,lastName,address,age,percentage");
    System.out.println("Or press b to go back.");

    String input = System.console().readLine();

    if ("b".equalsIgnoreCase(input)) {
      currentState = State.MAIN;
    } else {
      String[] studentArray = input.split(",");
      if (studentArray.length != 6) {
        System.out.println("Please write student like example.");
      } else {
        Student student = new Student();
        student.setId(studentArray[0]);
        student.setFirstName(studentArray[1]);
        student.setLastName(studentArray[2]);
        student.setAddress(studentArray[3]);
        student.setAge(studentArray[4]);
        student.setPercentage(studentArray[5]);

        achillesStudentManager.crud().insert(student).execute();
        System.out.println("Student inserted successfully");
      }
    }
  }

  private void updateMenu() {
    System.out.println("Enter student id to update or b to go back.");
    String input = System.console().readLine();
    if ("b".equalsIgnoreCase(input)) {
      currentState = State.MAIN;
    } else  {
      Student studentToUpdate = achillesStudentManager.crud().findById(input).get();
      if (studentToUpdate == null) {
        System.out.println("Can't find student with id: " + input);
      } else {
        System.out.println("Student is: " + studentToUpdate.toString());
        System.out.println("Write student info like this to update: firstName,lastName,address,age,percentage");
        System.out.println("Or press b to go back.");

        input = System.console().readLine();
        if ("b".equalsIgnoreCase(input)) {
          currentState = State.MAIN;
        } else {
          String[] studentArray = input.split(",");
          if (studentArray.length != 5) {
            System.out.println("Please write student like example.");
          } else {
            Student student = new Student();
            student.setId(studentToUpdate.getId());
            student.setFirstName(studentArray[0]);
            student.setLastName(studentArray[1]);
            student.setAddress(studentArray[2]);
            student.setAge(studentArray[3]);
            student.setPercentage(studentArray[4]);

            achillesStudentManager.crud().insert(student).execute();
            System.out.println("Student updated successfully");
          }
        }
      }
    }
  }

  private void mainMenu() {
    System.out.println("Enter i to insert new student.");
    System.out.println("Enter u to update a student.");
    System.out.println("Enter d to delete a student.");
    System.out.println("Enter g to get a student.");
    System.out.println("Enter e to exit.");

    String input = System.console().readLine();

    if ("i".equalsIgnoreCase(input)) {
      currentState = State.INSERT;
    } else if ("u".equalsIgnoreCase(input)) {
      currentState = State.UPDATE;
    } else if ("d".equalsIgnoreCase(input)) {
      currentState = State.DELETE;
    } else if ("g".equalsIgnoreCase(input)) {
      currentState = State.GET;
    } else if ("e".equalsIgnoreCase(input)) {
      System.exit(0);
    } else {
      System.out.println("You entered wrong character");
    }
  }

  private void init(String host, String clusterName) {
    Cluster cluster = Cluster.builder().addContactPoint(host).withClusterName(clusterName).build();
    ManagerFactory managerFactory = ManagerFactoryBuilder.builder(cluster).doForceSchemaCreation(true).build();
    achillesStudentManager = managerFactory.forStudent();

    currentState = State.MAIN;
  }
}