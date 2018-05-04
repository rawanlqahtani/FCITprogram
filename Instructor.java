/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcit.eaa;

/**
 *
 * @author Toshiba
 */
public class Instructor extends FCITMember
        implements Comparable<FCITMember> {

    // Data
    private String instQualification;
    private Section[] instSchedule;

    public void setInstSchedule(Section[] instSchedule) {
        this.instSchedule = instSchedule;
    }

    // Constructor
    public Instructor(String InstQualification, Section[] InstSchedule,
            String memName, int memID, int memStartingYear) {
        super(memName, memID, memStartingYear);
        this.instQualification = InstQualification;
        this.instSchedule = InstSchedule;
    }

    public Instructor(String InstQualification, String memName, int memID,
            int memStartingYear) {
        super(memName, memID, memStartingYear);
        this.instQualification = InstQualification;
        instSchedule = null;
    }

    // Method
    public void setStatus(String qualification) {
        instQualification = qualification;
    }

    public String getStatus() {

        return instQualification;
    }

    public Section[] getInstSchedule() {
        return instSchedule;
    }

    public String getDeprtName() {
        return deprtName;
    }

    public String getInstructorName() {
        return memName;
    }

    public int getInstructorID() {
        return memID;
    }

    public int compareTo(FCITMember other) {

        if (memStartingYear > other.getStartingYear()) {
            return 1;
        } else if (memStartingYear < other.getStartingYear()) {
            return -1;
        }
        return 0;
    }

}
