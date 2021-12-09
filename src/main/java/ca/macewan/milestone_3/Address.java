/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #3
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class holds an address object and the appropriate getters and toSting methods
 *
 *
 */
package ca.macewan.milestone_3;

import java.util.Objects;

public class Address{
    private String suite, streetName;
    private int houseNumber;


    public Address(String suite, String houseNumber, String streetName){


        this.suite = suite;
        if (houseNumber.equals("")) {
            this.houseNumber = 0;
        } else {
            this.houseNumber = Integer.parseInt(houseNumber);
        }

        this.streetName = streetName;
    }

    public String getSuite(){
        return suite;
    }

    public int getHouseNumber(){
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    @Override
    public String toString() {
        return suite + " " + houseNumber + " " + streetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return houseNumber == address.houseNumber && suite.equals(address.suite) && streetName.equals(address.streetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suite, streetName, houseNumber);
    }
}
