package javasabr.leetcode.schedule;

import java.util.Arrays;

public class RoundRobinScheduling {

  public static void main(String[] args) {
    // process id's
    int[] processes = {1, 2, 3};
    int[] burstTime = {10, 5, 8};
    int quantum = 2;

    executeAndPrintTime(processes, burstTime, quantum);
  }

  public static void executeAndPrintTime(int[] processes, int[] burstTime, int quantum) {

    int[] waitTime = new int[burstTime.length];
    int[] turnAroundTime = new int[processes.length];

    execute(burstTime, waitTime, quantum);

    for (int i = 0; i < processes.length; i++) {
      turnAroundTime[i] = waitTime[i] + burstTime[i];
    }

    int totalWaitTime = 0, totalTat = 0;

    System.out.println("PN      " + "BT   " + "WT      " + "TAT");

    for (int i = 0; i < processes.length; i++) {
      totalWaitTime += waitTime[i];
      totalTat += turnAroundTime[i];
      System.out.println(" " + (i + 1) + "\t\t" + burstTime[i] + "\t " + waitTime[i] + "\t\t " + turnAroundTime[i]);
    }

    System.out.println("Average waiting time = " + (float) totalWaitTime / (float) processes.length);
    System.out.println("Average turn around time = " + (float) totalTat / (float) processes.length);
  }

  private static void execute(int[] burstTime, int[] waitTime, int quantum) {

    int[] currentBurstTime = Arrays.copyOf(burstTime, burstTime.length);
    int totalTime = 0;

    while (true) {

      boolean done = true;

      for (int i = 0; i < currentBurstTime.length; i++) {

        int remainTime = currentBurstTime[i];
        if (remainTime < 1) {
          continue;
        }

        if (remainTime > quantum) {
          currentBurstTime[i] -= quantum;
          totalTime += quantum;
          done = false;
        } else {
          totalTime += remainTime;
          waitTime[i] = totalTime - burstTime[i];
          currentBurstTime[i] = 0;
          done = false;
        }
      }

      if (done) {
        break;
      }
    }
  }
}
