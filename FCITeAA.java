/*
 * CPCS 203 Programming2 Course
 * Assignment4 [Inheratance - Polymorphism - Abstract - Interfaces]
 */
package fcit.eaa;

import java.io.*;
import java.util.*;

public class FCITeAA {

    // Class Data
    
    private static Course[] deprtCourses;
    
    
   
    
     public static void outputSortedStudents(ArrayList<Section> deprtSchedule, Scanner in, PrintWriter out) {
        
        int itemID = -1;
        
        
       //stop looping and print when reach -1
       do {

           itemID = in.nextInt();
           if( itemID == -1)
               break;
           
             Section itemSec = findSectionByID(deprtSchedule, itemID);

            ArrayList<Student> availableSt = itemSec.getAvailableStudents();
            
            //sort the list of students
            Collections.sort(availableSt);
                

            //print results to the output
            
            String outPut = "Course: " + itemSec.getCourseInfo().getcName() + " " + itemSec.getCourseInfo().getcNum() + "\tSection: " + itemSec.getSecName();
            
            out.println(outPut);
            
            out.println("Day and Time: " + itemSec.getSlotForTheory() + " " + itemSec.getSlotForLab());

            out.printf("%n\t%-10s%-10s", "StdId", "StdName");
     
            out.println("\r\n\t--------------------------------");

            //Print Student List
            for (int x = 0; x < availableSt.size(); x++) 
                    out.printf("\t%-10s%-10s%n", availableSt.get(x).getMemID(), availableSt.get(x).getMemName());
                   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println("\r\n-----------------------------------------------------------------------------------");
        } while (itemID != -1);
        
    } 

     public static Section findSectionByID(ArrayList<Section> deprtSchedule, int secId) {
       
        Section result = null;
        for(int i=0; i <deprtSchedule.size(); i++){
        
            if (deprtSchedule.get(i).getSecID() == secId) 
                result = deprtSchedule.get(i);
                
            
        }
        return result;
    }
    
    public static void tryDropSection(ArrayList<Section> deprtSchedule, ArrayList<FCITMember> deprtMember, Scanner in, PrintWriter out) {
        
        int itemID1 = 0;
        int itemID2 = 0;
        Student itemStudent = null;
        Section itemSection = null;

        
        
        //in data from file
        itemID1 = in.nextInt();
        itemID2 = in.nextInt();
       
       
        //search for student itemect in the array
        for (int x = 0; x < deprtMember.size(); x++) {
             if (deprtMember.get(x).getMemID() == itemID1) 
                if(deprtMember.get(x) instanceof Student){
                    itemStudent = (Student) deprtMember.get(x);
                
                    break;
            }
        }
        
            //search for section itemect in the array
            itemSection = findSectionByID(deprtSchedule, itemID2);

            
            // handle excpetion caused by not finding section student schedule list
            try {
                
                //this method through an excpetion
                itemStudent.section_dropped(itemID2);
                
                
                itemSection.student_Dropped(itemStudent);
                
                //if no exception occured 
                //print data to the output file
                ArrayList<Section>  availableSch = itemStudent.getAvailableSections();
                
               String registeredCre = String.valueOf(itemStudent.getCurrentRegCredit());
               
                out.println("\r\nStudent: " + itemStudent.getMemName() + "\tstdID: " + itemStudent.getMemID() + "\tTotal Credit Hours: " + registeredCre);

                out.printf("\r\n\t%-10s%-10s%-15s%-30s%-10s", "SecId", "SecName", "Course", "Time", "Location");
                out.println("");
                
                
                
                for (int x = 0; x < availableSch.size(); x++) {
                    
                    Section schduleSec = availableSch.get(x);
                    
                    String sectionCou = schduleSec.getCourseInfo().getcCode() + " "  + schduleSec.getCourseInfo().getcNum();
                   
                    out.printf("\t%-10s%-10s%-15s%-30s%-10s%n", schduleSec.getSecID(), schduleSec.getSecName(),
                            sectionCou , schduleSec.getSlotForTheory() + " " + schduleSec.getSlotForLab(),
                            schduleSec.getSecRoomNo() + " " + schduleSec.getSecLabNo());
                    
                }
                
                
                out.println("-----------------------------------------------------------------------------------");
                out.println("Drop Course : " + itemSection.getCourseInfo().getcName()  + " " + 
                        itemSection.getCourseInfo().getcNum()+ "\t\tSection: " + itemSection.getSecName());
                
                

            } catch (CourseNotRegisteredException itemEx) {
                
                //process the exception by printing this data ot the output file
                    out.println(itemEx + ":");

                    out.println("\tInput was invalid");
                    out.println("\tStudent " + itemStudent.getMemName() + " is not registered in " + 
                            itemSection.getCourseInfo().getcName() + " " + itemSection.getCourseInfo().getcNum()+ " course ");
            }

    }
    

    public static void main(String[] args) throws Exception {

        // Application DataEntry 
        int numOfCourses = 0;

        ArrayList<FCITMember> deprtMember = new ArrayList();
        ArrayList<Section> deprtSchedule = new ArrayList();
        
        //Prepare Input/Output Files
        File fcitData = new File("infciteaa_v2.txt");
        File fcitReportData = new File("utfciteaa_v2.txt");
        
        Scanner in = new Scanner(fcitData);
        PrintWriter out = new PrintWriter(fcitReportData);
        
        out.print("Programming Assignment 5 _______");
        out.println(new Date().toString());
        //======================================================================
        String command;
        while (in.hasNext()) {
            command = in.next(); //Read Command from file

            //Get Course Data
            if (command.equalsIgnoreCase("InputDepartmentPlan")) {
                numOfCourses = in.nextInt();
                deprtCourses = new Course[numOfCourses];
                inputDepartmentPlanCommand(in, out);

            } // Get instructor data
            else if (command.equalsIgnoreCase("InputInstructorData")) {
                inputInstructorDataCommand(in, out, deprtMember);

            } // Get student data 
            else if (command.equalsIgnoreCase("InputStudentData")) {
                inputStudentDataCommand(in, out, deprtMember);

            } // Get section data
            else if (command.equalsIgnoreCase("InputSectionData")) {
                inputSectionDataCommand(in, out, deprtSchedule);
            } /* 
             The following commands are the commands that process the entered data
             */ else if (command.equalsIgnoreCase("RegisterCourse")) {
                reRegisterCourseCommand(in, out, deprtMember, deprtSchedule);

            } // 
            else if (command.equalsIgnoreCase("InstructorLoadRequest")) {
                instructorLoadRequestCommand(in, out, deprtMember, deprtSchedule);

            } // print All instructors
            else if (command.equalsIgnoreCase("PrintAllInstructorsLoads")) {
                printAllInstructorsLoadsCommand(in, out, deprtMember);

            } // print Section Student List
            else if (command.equalsIgnoreCase("PrintSectionStudentList")) {
                printSectionStudentListCommand(in, out, deprtSchedule);
            } 
            
            
            /////////////////////////////////////////////////////////////////////////////////
            //new methods 
            
            else if (command.equalsIgnoreCase("DropCourse")) {
                
                out.println("");
                out.println("Drop Report: ");
                out.println("-------------");
                

                tryDropSection(deprtSchedule, deprtMember, in, out);
                
                
                
                out.println("===================================================================================");
            }
            
            else if (command.equalsIgnoreCase("PrintSortedSectionStudentList")) {
                out.println("");
                out.println("Sorted Section Student List");
                out.println("---------------------");
                
                out.println("");

                outputSortedStudents(deprtSchedule, in, out);
                
                
                
                out.println("===================================================================================");
            }
            

        }//End of reading inputfile.        
        // Closing Data Files
        in.close();
        out.close();
    }// End of Main
//=====================================================================================================

    public static String generateReport(Student s, Section[] ss) {
        StringBuilder report = new StringBuilder();
        String stat = s.getStatus();
        int[] minLevels = {0, 0};
        int coursesRegistered = 0;

        for (int x = 0; x < ss.length; x++) {
            if (ss[x] != null) {
                coursesRegistered += 1;
            } else {
                break;
            }
        }

        // Check for under 12 hours
        int registeredCredit = 0;
        int count = 0;
        boolean found = false;
        while (ss[count] != null) {
            registeredCredit += ss[count].getCourseInfo().getcCredit();
            count++;
        }

        if (registeredCredit < 12) {
            report.append("Student Registered Credit is less than 12 Credits Must ADD courses \r\n");
        }

        //Check for CPCS 498
        if (stat.equals("Graduate")) {
            for (int x = 0; x < coursesRegistered; x++) {
                if (ss[x].getCourseInfo().getcCode().equals("CPCS") && ss[x].getCourseInfo().getcNum() == 498) {
                    found = true;
                }
            }

            if (!found) {
                report.append("Student is a Graduate and did not Register CPCS 498 \r\n");
            }
        }

        //Check for cross-level
        
for (int y = 0; y < coursesRegistered; y++){
        for (int x = 1; x < coursesRegistered; x++) {
        
            if ( y!=x && ss[y].getCourseInfo().getcLevel() != ss[x].getCourseInfo().getcLevel()) {
                 report.append("Student has registered course from two different levels \r\n");
                break;
            }
            
        
         break;
        }
         break;
}
        
        report.append("The Student is a " + s.getStatus());

        return report.toString();
    }// End of generate report
    //==================================================================================================
    public static void inputDepartmentPlanCommand(Scanner in, PrintWriter out) {

        String beginingOfLine, stringItem1, stringItem2;
        boolean theory, lab;
        int intItem1, intItem2;

        int numOfCourses = deprtCourses.length;
        int i = 0, intDataItem = 1;

        while (i < numOfCourses) {     // Loop to in all courses in plan.
            beginingOfLine = in.next();
            if (beginingOfLine.equalsIgnoreCase("Level")) {
                intDataItem = in.nextInt();

                stringItem1 = in.next();
            } else {
                stringItem1 = beginingOfLine;
            }
            intItem1 = in.nextInt();
            stringItem2 = in.next().replace('?', ' ');
            intItem2 = in.nextInt();
            theory = in.next().equalsIgnoreCase("theory");
            lab = in.next().equalsIgnoreCase("lab");
            deprtCourses[i] = new Course(stringItem1, intItem1, stringItem2, intDataItem, intItem2, theory, lab);

            i++; // Increase Course counter.    
        }// End of while courses
        out.println("Department Plan(Courses) Data has been Inserted");
        out.println("===================================================================================");

    }//  End of reading course data
    //===================================================================================================
    public static void inputInstructorDataCommand(Scanner in, PrintWriter out, ArrayList<FCITMember> deprtMember) {

        int i = 0, intItem1, intItem2;
        String stringItem2, stringItem1;

        int intDataItem = in.nextInt();
        i = 0;
        while (i < intDataItem) { //loop to in all instructor data.
            stringItem2 = in.next().replace('?', ' ');
            intItem1 = in.nextInt();
            intItem2 = in.nextInt(); // Read starting year.
            stringItem1 = in.next().replace('?', ' '); // in qualification.
            deprtMember.add(new Instructor(stringItem1, stringItem2, intItem1,
                    intItem2));
            i++;
        }

        out.println("Department Instructor's Data has been Inserted");
        out.println("===================================================================================");

    }// End of get instructor data
    //==================================================================================================
    public static void inputStudentDataCommand(Scanner in, PrintWriter out, ArrayList<FCITMember> deprtMember) {

        int intDataItem = in.nextInt();

        double gpa;
        int i = 0, intItem1, intItem2, thours;
        String stringItem2;
        while (i < intDataItem) { //loop to in all student data.
            stringItem2 = in.next().replace('?', ' ');// Read name
            intItem1 = in.nextInt();// Read ID
            intItem2 = in.nextInt(); // Read starting year.
            gpa = in.nextDouble();
            thours = in.nextInt();
            String[][] plan = null;
            if (in.next().equalsIgnoreCase("N/A")) {
                plan = null;
            }
            deprtMember.add(new Student(gpa, thours, plan, stringItem2, intItem1, intItem2));
            i++; // Next Student
        }// end of while student    
        Student t = null;

        out.println("Department Students Data has been Inserted");
        out.println("===================================================================================");

    } // End of  get student data 
    //=================================================================================================
    public static void inputSectionDataCommand(Scanner in, PrintWriter out, ArrayList<Section> deprtSchedule) {

        int i = 0, s, intItem1, intItem2;
        String stringItem1, stringItem2, stringItem3, stringItem4, stringItem5, stringItem6;

        int intDataItem = in.nextInt();

        while (i < intDataItem) { //loop to in all section data.
            intItem1 = in.nextInt();// Read Section ID
            stringItem1 = in.next(); // section name.
            stringItem2 = in.next(); // section code.
            intItem2 = in.nextInt();
            s = in.nextInt(); // in size.
            stringItem3 = in.next().replace('?', ' '); //in slot
            stringItem4 = in.next().replace('?', ' '); // in lab slot
            stringItem5 = in.next(); // in room number
            stringItem6 = in.next(); // in lab number
            Course c = null;
            for (int j = 0; j < deprtCourses.length; j++) {
                if (deprtCourses[j].getcCode().equals(stringItem2) && deprtCourses[j].getcNum() == intItem2) {
                    c = deprtCourses[j];
                    break;
                } else {
                    c = null;
                }
            }
            deprtSchedule.add(new Section(intItem1, stringItem1, s, c, stringItem3,
                    stringItem4, stringItem5, stringItem6));
            i++;
        }// End of in all section data    

        out.println("Department Schedule (Sections) has been Inserted");
        out.println("===================================================================================");

    }// End of inputSectionDataCommand method
    //================================================================================================
    public static void reRegisterCourseCommand(Scanner in, PrintWriter out, ArrayList<FCITMember> deprtMember, ArrayList<Section> deprtSchedule) {

        out.println("");
        out.println("Registration Validity Report: ");
        out.println("------------------------------");

        Student temp = null;
        int tRegCourses = 0;
        int i = 0;
        int tRegHours = 0;
        int aSize;
        Student[] stdList;
        String regReport="";
        // Read the total number of registration requests
        int intItem1 = in.nextInt();
        int intItem2;
        // Read and Process First Request.
        while (i < intItem1) {
            // Get Student info
            intItem2 = in.nextInt();
            for (int t = 0; t < deprtMember.size(); t++) {
                if (deprtMember.get(t) instanceof Student) {
                    temp = (Student) deprtMember.get(t);
                    if (temp.getMemID() == intItem2) {
                        break;
                    }
                }
            }
            if (temp != null) { // if a Student was found
                // Read All Allowed Section Registeration Requests
                regReport="";
                intItem2 = in.nextInt();
                int j = 0;
                Section[] schedule = new Section[7];
                while (intItem2 != -1) {
                    for (int t = 0; t < deprtSchedule.size(); t++) {
                        if (j < 7) {
                            if (intItem2 == deprtSchedule.get(t).getSecID()) { // Get Section Information
                                schedule[j] = deprtSchedule.get(t); // Add section to Student Schedule
                                tRegCourses = j + 1; //upate total number of courses.
                                tRegHours += deprtSchedule.get(t).getCourseInfo().getcCredit();

                                // Add student to Section Student List
                                stdList = deprtSchedule.get(t).getStdList();
                                aSize = deprtSchedule.get(t).getSecActualSize();
                                stdList[aSize] = temp;
                                deprtSchedule.get(t).setStdList(stdList);
                                aSize += 1;
                                deprtSchedule.get(t).setSecActualSize(aSize);
                                j++;
                            }
                        } else {
                            out.println(deprtSchedule.get(t).getCourseInfo().getcName()
                                    + "The Requested Course has exceeded the Maximum allowed");
                        }
                    }
                    // Get Next Course
                    intItem2 = in.nextInt();
                }
                // add registered table

                

                // Check for Contradiction
                int courseForDelete = 0;
                boolean delete = false;
                String c1 = null, c2 = null;
                for (int x = 0; x < tRegCourses; x++) {
                    for (int y = 0; y < tRegCourses; y++) {
                        if (x != y) {
                            if (schedule[x].slotContradicts(schedule[y].getSlotForTheory(),
                                    schedule[y].getSlotForLab())) {
                                c1 = schedule[x].getSecName();
                                c2 = schedule[y].getSecName();
                                courseForDelete = x;
                                delete = true;
                                break;
                            }
                        }
                    }
                }
                if (delete) {
                    int indexOfSt=-1;
                    for(int x=0;x<deprtSchedule.size();x++)
                    {
                        if(c1 == deprtSchedule.get(x).getSecName()){
                        // delete student from Section Student List
                                stdList = deprtSchedule.get(x).getStdList();
                                aSize = deprtSchedule.get(x).getSecActualSize();
                                for(int y=0;y<aSize;y++)
                                {
                                    if(0==temp.compareTo(stdList[y]))
                                       indexOfSt=y; 
                                }
                                stdList[indexOfSt] = null;
                                for(int y=indexOfSt;y<aSize-1;y++)
                                {
                                   stdList[y] = stdList[y+1]; 
                                }
                                deprtSchedule.get(x).setStdList(stdList);
                                aSize -= 1;
                                deprtSchedule.get(x).setSecActualSize(aSize);}}
                    regReport +="The following two Sections Contradict"
                            + " in Slots " + c1 + "  " + c2+"\r\n";

                    tRegHours -= schedule[courseForDelete].getCourseInfo().getcCredit();
                    schedule[courseForDelete] = null;
                    tRegCourses--;
                }

                //Record Number of courses regestered
                temp.setTotalRegCourses(tRegCourses);
              temp.setCurrentRegCredit(tRegHours);
 
                // Print Report to file
                out.println("\r\nStudent: " + temp.getMemName() + "\tstdID: "
                        + temp.getMemID() + "\tTotal Credit Hours: " + tRegHours);
                //     out.println("");
                out.printf("\r\n\t%-10s%-10s%-15s%-30s%-10s", "SecId",
                        "SecName", "Course", "Time", "Location");
                out.println("");
                for (int z = 0; z < tRegCourses; z++) {
                    out.printf("\t%-10s%-10s%-15s%-30s%-10s", schedule[z].getSecID(),
                            schedule[z].getSecName(),
                            schedule[z].getCourseInfo().getcCode() + " "
                            + schedule[z].getCourseInfo().getcNum(),
                            schedule[z].getSlotForTheory() + " " + schedule[z].getSlotForLab(),
                            schedule[z].getSecRoomNo() + " " + schedule[z].getSecLabNo());
                    out.println("");
                }
                out.println("-----------------------------------------------------------------------------------");
                //           out.println("Registration Validity Report:");
                temp.setsSchedule(schedule);
regReport += generateReport(temp, schedule);
                out.println(regReport);
                
            } else {
                out.println("The Member with ID: " + deprtMember.get(intItem2).memID
                        + " is not a student. Therefore Registration can not be completed.");
            }
            i++;
            tRegHours = 0;
            out.println("___________________________________________________________________________________");
        }//End of while register requests exist
        out.println("===================================================================================");
    }// End of Register Course
    //====================================================================================
    public static void instructorLoadRequestCommand(Scanner in, PrintWriter out, ArrayList<FCITMember> deprtMember, ArrayList<Section> deprtSchedule) {

        out.println("");
        out.println("Instructor Load Requests:");
        out.println("-------------------------");
        Section[] instLoad, instActualLoad;
        Instructor tempInst;
        int intItem1 = in.nextInt();
        int i = 0;
        int intItem2, intItem3;
        int loadInCourses;
        instLoad = new Section[10]; //temporary array to in load in.
        while (i < intItem1) {
            intItem2 = in.nextInt();
            for (int k = 0; k < deprtMember.size(); k++) {
                //Find Instructor
                if (deprtMember.get(k) instanceof Instructor) {
                    tempInst = (Instructor) deprtMember.get(k);
                    if (tempInst.getInstructorID() == intItem2) {
                        intItem3 = in.nextInt();
                        loadInCourses = 0;
                        //Find Course and Check for more Courses
                        while (intItem3 != -1) {
                            for (int k1 = 0; k1 < deprtSchedule.size(); k1++) {
                                if (deprtSchedule.get(k1).getSecID() == intItem3) {
                                    deprtSchedule.get(k1).setSecInstructor(tempInst);
                                    if (deprtSchedule.get(k1) instanceof Section) {
                                        instLoad[loadInCourses] = (Section) deprtSchedule.get(k1);
                                    }
                                    loadInCourses++;
                                    break;
                                }
                            }
                            // Get Next Section
                            intItem3 = in.nextInt();
                        }
                        instActualLoad = new Section[loadInCourses];
                        for (int k2 = 0; k2 < loadInCourses; k2++) {
                            instActualLoad[k2] = instLoad[k2];
                        }
                        tempInst.setInstSchedule(instActualLoad);
                        // Print to file instructor assignement
                        out.println("");
                        out.println("Instructor: " + tempInst.getInstructorName()
                                + "\tInstructor ID: " + tempInst.getInstructorID()
                                + "\tTotal Number of Courses: " + loadInCourses);
                        out.printf("\r\n\t%-10s%-10s%-15s%-30s%-10s", "SecId",
                                "SecName", "Course", "Time", "Location");
                        out.println("");
                        Section[] tempSchd;
                        tempSchd = tempInst.getInstSchedule();
                        for (int z1 = 0; z1 < instActualLoad.length; z1++) {
                            out.printf("\t%-10s%-10s%-15s%-30s%-10s", tempSchd[z1].getSecID(),
                                    tempSchd[z1].getSecName(),
                                    tempSchd[z1].getCourseInfo().getcCode() + " "
                                    + tempSchd[z1].getCourseInfo().getcNum(),
                                    tempSchd[z1].getSlotForTheory() + " " + tempSchd[z1].getSlotForLab(),
                                    tempSchd[z1].getSecRoomNo() + " " + tempSchd[z1].getSecLabNo());
                            out.println("");
                        }
                        out.println("-----------------------------------------------------------------------------------");
                    }// end of found instructor                
                }// check if element is Instructor
            }
            //Next Request             
            i++;
        }
        out.println("===================================================================================");

    }// End of instructorLoadRequestCommand method
    //=============================================================================================
    public static void printAllInstructorsLoadsCommand(Scanner in, PrintWriter out, ArrayList<FCITMember> deprtMember) {
        out.println("");
        out.println("All Instructors Sorted Load List ");
        out.println("---------------------------------");
        out.println("");

        Instructor inst;
        Collections.sort(deprtMember);
        for (int o = 0; o < deprtMember.size(); o++) {
            if (deprtMember.get(o) instanceof Instructor) {
                inst = (Instructor) deprtMember.get(o);
                out.println("Instructor: " + inst.getInstructorName()
                        + "\tInstructor ID: " + inst.getInstructorID());
                out.printf("\r\n\t%-10s%-10s%-15s%-30s%-10s", "SecId",
                        "SecName", "Course", "Time", "Location");
                out.println("");
                Section[] tempSchd;
                tempSchd = inst.getInstSchedule();
                if (tempSchd != null) {
                    for (int z1 = 0; z1 < tempSchd.length; z1++) {
                        out.printf("\t%-10s%-10s%-15s%-30s%-10s", tempSchd[z1].getSecID(),
                                tempSchd[z1].getSecName(),
                                tempSchd[z1].getCourseInfo().getcCode() + " "
                                + tempSchd[z1].getCourseInfo().getcNum(),
                                tempSchd[z1].getSlotForTheory() + " " + tempSchd[z1].getSlotForLab(),
                                tempSchd[z1].getSecRoomNo() + "  " + tempSchd[z1].getSecLabNo());
                        out.println("");
                    }
                    out.println("");
                }
                out.println("-----------------------------------------------------------------------------------");
            } //end of print to file one instructor
        }

        out.println("===================================================================================");

    } // End of printAllInstructorsLoadsCommand method
    //========================================================================================
    public static void printSectionStudentListCommand(Scanner in, PrintWriter out, ArrayList<Section> deprtSchedule) {

        out.println("");
        out.println("Section Student List");
        out.println("---------------------");
        out.println("");
        Section tempSec = null;
        Student[] tempStd = null;

        int totalStdNum = 0;

        int intItem1 = in.nextInt();

        while (intItem1 != -1) {

            //Get Section info.
            for (int n = 0; n < deprtSchedule.size(); n++) {
                if (intItem1 == deprtSchedule.get(n).getSecID()) {
                    tempSec = deprtSchedule.get(n);
                    break;
                }//End of if
            }// End of find section information.  

            tempStd = tempSec.getStdList();

            if (tempStd != null) {

                //Print Student List

                out.print("Course: " + tempSec.getCourseInfo().getcName()
                        + " " + tempSec.getCourseInfo().getcNum());

                out.println("\tSection: " + tempSec.getSecName());
                out.println("Day and Time: " + tempSec.getSlotForTheory()
                        + " " + tempSec.getSlotForLab());

                //           out.println("____________________________________________________");
                out.printf("\r\n\t%-10s%-10s", "StdId", "StdName");
                out.println("\r\n\t--------------------------------");
                totalStdNum = tempSec.getSecActualSize();
                for (int n = 0; n < totalStdNum; n++) {
                    out.printf("\t%-10s%-10s\r\n", tempStd[n].getMemID(), tempStd[n].getMemName());
                }
                
                

                out.println("");
                out.println("-----------------------------------------------------------------------------------");

            }//End of print data
            else {
                out.println("No Students Found");
            }

            intItem1 = in.nextInt();
        }//End of While != -1
        out.println("===================================================================================");
    } // End of  printSectionStudentListCommand  method

    
       
    
     
    
    
    
}
