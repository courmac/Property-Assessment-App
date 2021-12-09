/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #3
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class holds a location object and the appropriate getters and toSting methods
 *
 *
 */
package ca.macewan.milestone_3;

import java.util.Objects;

public class Location {
    private String  pointLocation;
    private double latitude, longitude;

    public Location(String latitude, String longitude, String pointLocation){

        if (latitude.equals("")) {
            this.latitude = 0;
        } else {
            try {
                this.latitude = Double.parseDouble(latitude);
            } catch (Exception e){
                this.latitude = 0;

            }

        }

        if (longitude.equals("")) {
            this.longitude = 0;
        } else {
            try {
                this.longitude = Double.parseDouble(longitude);
            } catch (Exception e){
                this.longitude = 0;

            }
        }

        this.pointLocation = pointLocation;
    }

    public String getPointLocation(){
        return pointLocation;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {

        return "(" + latitude + ", " + longitude + ")";
    }

    /**
     *  this equal method only compares the property assessment account numbers as each account number is unique
     *
     */
    public boolean equals(Object o){
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Location location = (Location) o;
        return this.pointLocation.equals(location.pointLocation);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.pointLocation);
    }
}
