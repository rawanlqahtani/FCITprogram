/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcit.eaa;

/**
 *
 * @author Toshiba
 */
public abstract class FCITMember implements Comparable<FCITMember> {
    
    // Class Data 
    protected String deprtName;
    protected String memName;
    protected int memID;
    protected int memStartingYear;
    
    // Constructor
    public FCITMember(String memName, int memID, int memStartingYear) {
        deprtName = "Computer Science";
        this.memName = memName;
        this.memID = memID;
        this.memStartingYear = memStartingYear;
    }
    // methods
    
    public int getStartingYear() {
        return memStartingYear;
    }
    
    public int getMemID() {
        return memID;
    }
    
    /**
     *
     * @return current status of FCIT member 
     * If Instructor give work title lecturer/ assistant prof.
     * or if student give is she 2ndYear, 3rdYear, 4thYear, delayed, graduate.
     */
    
    public abstract String getStatus();
    public abstract int compareTo(FCITMember other);
  
}
