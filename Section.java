/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcit.eaa;

import java.util.ArrayList;

/**
 *
 * @author Toshiba
 */
public class Section {
    // Data
    private int secID;
    private String secName;
    private int secMaxSize;
    private int secActualSize;
    private Course courseInfo;
    private String slotForTheory;
    private String slotForLab;
    private String secRoomNo;
    private String secLabNo;
    private Instructor secInstructor;
    private Student[] stdList;
    
    //Constructor

    public Section(int secID, String secName, int secSize, Course courseInfo,
            String slotForTheory, String slotForLab, String secRoomNo,
            String secLabNo) {
        this.secID = secID;
        this.secName = secName;
        this.secMaxSize = secSize;
        secActualSize = 0;
        this.courseInfo = courseInfo;
        this.slotForTheory = slotForTheory;
        this.slotForLab = slotForLab;
        this.secRoomNo = secRoomNo;
        this.secLabNo = secLabNo;
        secInstructor = null;
        stdList = new Student[30];
    }

    public int getSecMaxSize() {
        return secMaxSize;
    }

    public void setSecMaxSize(int secMaxSize) {
        this.secMaxSize = secMaxSize;
    }

    public int getSecActualSize() {
        return secActualSize;
    }

    public void setSecActualSize(int secActualSize) {
        this.secActualSize = secActualSize;
    }
    
    //methods
    public int getSecID() {
        return secID;
    }

    public void setSecID(int secID) {
        this.secID = secID;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }


    public Course getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(Course courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getSlotForTheory() {
        return slotForTheory;
    }

    public void setSlotForTheory(String slotForTheory) {
        this.slotForTheory = slotForTheory;
    }

    public String getSlotForLab() {
        return slotForLab;
    }

    public void setSlotForLab(String slotForLab) {
        this.slotForLab = slotForLab;
    }

    public String getSecRoomNo() {
        return secRoomNo;
    }

    public void setSecRoomNo(String secRoomNo) {
        this.secRoomNo = secRoomNo;
    }

    public String getSecLabNo() {
        return secLabNo;
    }

    public void setSecLabNo(String secLabNo) {
        this.secLabNo = secLabNo;
    }

    public Instructor getSecInstructor() {
        return secInstructor;
    }

    public void setSecInstructor(Instructor secInstructor) {
        this.secInstructor = secInstructor;
    }

    public Student[] getStdList() {
        return stdList;
    }

    public void setStdList(Student[] stdList) {
        this.stdList = stdList;
    }
    public boolean slotContradicts(String slotTh, String slotL){
       if (!slotForTheory.equalsIgnoreCase("TBA")) 
          if(!slotForTheory.equalsIgnoreCase("N/A")
             && !slotForLab.equalsIgnoreCase("N/A"))
            if(slotForTheory.equalsIgnoreCase(slotTh) || 
              slotForLab.equalsIgnoreCase(slotL)) 
               {return true;}
            else if (slotForTheory.contains(slotL) || 
                    slotTh.contains(slotForLab)) 
                return true;
        
     return false;
    }
    
     public void student_Dropped(Student student){
     
         for (int i = 0; i < stdList.length; i++) {
             if(stdList[i] != null ){
                 
                 if(student.getMemID() == stdList[i].getMemID() ){
                        secActualSize--;
                        stdList[i] = null;
                 }
              
             }
             
         }
         
        
        
    }
     
     public ArrayList<Student> getAvailableStudents(){
        
         ArrayList<Student>  students = new ArrayList<Student> () ;
          for (int i = 0; i < stdList.length; i++) {
              if(stdList[i] != null)
                  students.add(stdList[i]);
          }
          
         
        return students;
     }
    
}
