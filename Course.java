/*
 * CPCS 203 Programming2 Course
 * Assignment4 [Inheratance - Polymorphism - Abstract - Interfaces]
 */
package fcit.eaa;

/**
 *
 * @author Dr. E. A. Fadel
 */
public class Course {
    // Course Data
    private String cName;
    private String cCode;
    private int cNum;
    private int cLevel;
    private int cCredit;
    private boolean hasTheory;
    private boolean hasLab;
    
    
    // Course Constructors
    public Course(String cCode, int cNum, String cName, int cLevel, int cCredit, boolean hasTheory, boolean hasLab) {
        this.cName = cName;
        this.cCode = cCode;
        this.cNum = cNum;
        this.cLevel = cLevel;
        this.cCredit = cCredit;
        this.hasTheory = hasTheory;
        this.hasLab = hasLab;
    }

    // Course Methods
    // Getters & Setters
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public int getcLevel() {
        return cLevel;
    }

    public void setcLevel(int cLevel) {
        this.cLevel = cLevel;
    }

    public int getcCredit() {
        return cCredit;
    }

    public void setcCredit(int cCredit) {
        this.cCredit = cCredit;
    }

    public boolean isTheory() {
        return hasTheory;
    }

    public void setTheory(boolean hasTheory) {
        this.hasTheory = hasTheory;
    }

    public boolean isLab() {
        return hasLab;
    }

    public void setLab(boolean hasLab) {
        this.hasLab = hasLab;
    }
    
    
}
