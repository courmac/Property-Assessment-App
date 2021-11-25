/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class holds a Neighbourhood object and the appropriate getters and toSting methods
 *
 *
 */
package ca.macewan.milestone_3;

import java.util.Objects;

public class Neighbourhood {
    private int neighbourhoodId;
    private String  neighbourhood, ward;

    public Neighbourhood(String neighbourhoodId, String neighbourhood, String ward){

        if (neighbourhoodId.equals("")) {
            this.neighbourhoodId = 0;
        } else {
            this.neighbourhoodId = Integer.parseInt(neighbourhoodId);
        }

        this.neighbourhood = neighbourhood;
        this.ward = ward;

    }
    public String getNeighbourhood(){
        return neighbourhood;
    }

    public int getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public String getWard() {
        return ward;
    }

    @Override
    public String toString() {
        return neighbourhood +" (" + ward +")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbourhood that = (Neighbourhood) o;
        return neighbourhoodId == that.neighbourhoodId && neighbourhood.equals(that.neighbourhood) && ward.equals(that.ward);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbourhoodId, neighbourhood, ward);
    }
}
