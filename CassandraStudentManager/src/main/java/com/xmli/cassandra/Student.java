package com.xmli.cassandra;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

@Table(keyspace = "default")
public class Student {

  @PartitionKey
  private String id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String address;

  @Column
  private String age;

  @Column
  private String percentage;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getPercentage() {
    return percentage;
  }

  public void setPercentage(String percentage) {
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return id + "," + firstName + "," + lastName + "," + address + "," + age + "," + percentage;
  }
}
