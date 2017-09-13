package com.boot.sendmail.queue;

import java.util.LinkedList;
import java.util.Queue;

import com.boot.model.EmailStructure;

public class EmailQueue {
     public static Queue<EmailStructure> emailq = new LinkedList<EmailStructure>();
}
