//Gio Lapid
//CMPS12B
//PA 4: Simulation.java
//08/04/13

import java.io.*;
import java.util.Scanner;

public class Simulation{
  
  public static Job getJob(Scanner in) {
    String[] s = in.nextLine().split(" ");
    int a = Integer.parseInt(s[0]);
    int d = Integer.parseInt(s[1]);
    return new Job(a, d);
  }
  
  public static void main(String[] args) throws IOException{
    if (args[0] != null) {
      File in_file = new File(args[0]);
      Scanner scan = new Scanner(in_file);
      PrintWriter report = new PrintWriter(new FileWriter(args[0]+".rpt"));
      PrintWriter trace = new PrintWriter(new PrintWriter(args[0]+".trc"));
      
      String str = scan.nextLine();
      int m = Integer.parseInt(str);
      
      Queue storage = new Queue();
      for(int i = 0; i < m; ++i) 
		storage.enqueue((Job)getJob(scan));
      Queue backup = backup(storage, m);
      
      scan.close();
      
      Queue[] processor = new Queue[m+1];
      for (int j = 1; j <= m; ++j) 
        processor[j] = new Queue();
      
      
      report.println("Report file: "+args[0]+".rpt");
      report.println(m+" Jobs:");
      report.println(storage);
      report.println();
      for (int j = 0; j < 60; ++j) report.print("*");
      report.println();
      
      
      trace.println("Trace file: "+args[0]+".trc");
      trace.println(m+" Jobs:");
      trace.println(storage);
      
      for(int i = 1; i < m; ++i) {
        
        if(i == 1) 
			report.print(i+" processor: ");
        else 
			report.print(i+" processors: ");
        
        
        trace.println();
        for (int j = 0; j < 30; ++j) trace.print("*");
        trace.println();
        if(i == 1) trace.println(i+" processor:");
        else trace.println(i+" processors:");
        for (int j = 0; j < 30; ++j) trace.print("*");
        trace.println();
        
        
        trace.println("time=0");
        trace.println("0: "+storage);
        for(int j = 1; j <= i; ++j) trace.println(j+": "+processor[j]);
        
        int TIME = 0;
        int remaining = m;
        
        while(remaining > 0) {
          boolean print_now = false;
          while(newProcess(storage,processor,TIME,i)) 
			print_now = true;
          while(finishProcess(storage,processor,TIME,i)){
			print_now = true; 
			remaining--;
		  }
          
          
          if (print_now) {
            trace.println("time="+TIME);
            trace.println("0: "+storage);
            for(int j = 1; j <= i; ++j) trace.println(j+": "+processor[j]);
          }
          
          TIME++;
        }
        
        int TOTAL_WAIT_TIME = ComputeTotalWait(storage, m);
        int MAX_WAIT_TIME = ComputeMaxWait(storage, m);
        reset(backup, storage, m);
        
        report.printf("totalWait=%d, maxWait=%d, averageWait=%.2f %n",
                          TOTAL_WAIT_TIME, MAX_WAIT_TIME ,(float)TOTAL_WAIT_TIME/m);
        
      }
      
      report.close();
      trace.close();
    }
  }
  
  static boolean finishProcess(Queue storage, Queue[] process, int time, int num) {
    for (int i = 1; i <= num; i++) {
      if (process[i].isEmpty()) continue;
      else{
        if (((Job)process[i].peek()).getFinish() == time) {
          storage.enqueue(process[i].dequeue());
          if(!process[i].isEmpty()) ((Job)process[i].peek()).computeFinishTime(time);
          return true;
        }
      }
    }
    return false;
  }
  
  static boolean newProcess(Queue storage, Queue[] process, int time, int num) {
    if (!storage.isEmpty()) {
      if (((Job)storage.peek()).getArrival() == time) {
        Job tmp = ((Job)storage.dequeue());
        int value = nextProcessor(process, num);
        process[value].enqueue(tmp);
        if (((Job)process[value].peek()).getFinish() == Job.UNDEF) ((Job)process[value].peek()).computeFinishTime(time);
        return true;
      }
    }
    return false;
  }
  
  static int nextProcessor(Queue[] processor, int num) {
    int next = 1;
    for(int i = 1; i <= num; i++) {
      if(processor[i].length() < processor[next].length()) next = i;
    }
    return next;
  }
  
  static int ComputeTotalWait(Queue storage, int m) {
    int WAIT_TIME = 0;
    for (int i = 0; i < m; i++) {
      Job curr = (Job)storage.dequeue();
      WAIT_TIME += curr.getWaitTime();
      storage.enqueue(curr);
    }
    return WAIT_TIME;
  }
  
  static int ComputeMaxWait(Queue storage, int m) {
    int WAIT_TIME = 0;
    for (int i = 0; i < m; i++) {
      Job curr = (Job)storage.dequeue();
      if (curr.getWaitTime() > WAIT_TIME) WAIT_TIME = curr.getWaitTime();
      storage.enqueue(curr);
    }
    return WAIT_TIME;
  }
  
  static void reset(Queue backup, Queue storage, int m) {
    if (!storage.isEmpty()) storage.dequeueAll();
    for (int i = 0; i < m; i++) {
      Job curr = (Job)backup.dequeue();
      curr.resetFinishTime();
      storage.enqueue(curr);
      backup.enqueue(curr);
    }
  }
  
  static Queue backup(Queue storage, int m) {
    Queue orig = new Queue();
    for (int i = 0; i < m; i++) {
      Job curr = (Job)storage.dequeue();
      orig.enqueue(curr);
      storage.enqueue(curr);
    }
    return orig;
  }
}

