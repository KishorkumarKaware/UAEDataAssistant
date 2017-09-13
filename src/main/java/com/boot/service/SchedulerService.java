package com.boot.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.boot.model.EmailStructure;
import com.boot.sendmail.SendMail;
import com.boot.sendmail.queue.EmailQueue;

@Service
public class SchedulerService {

	public void SendEmailAndRemoveEntry(){
		EmailStructure es;
		Iterator iterator = EmailQueue.emailq.iterator();
		while(iterator.hasNext()){
			es=(EmailStructure) iterator.next();
			SendMail sm=new SendMail();
			sm.sendMail(es.getEmail(), es.getInvestment(), es.getActivity(), es.getYear());
			EmailQueue.emailq.remove(es);

		}
	}

}
