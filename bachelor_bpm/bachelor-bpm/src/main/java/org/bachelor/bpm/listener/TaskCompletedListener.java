package org.bachelor.bpm.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskCompletedEvent;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.context.service.IVLService;

@Service
public class TaskCompletedListener implements ApplicationListener<TaskCompletedEvent>{
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	@Autowired
	private IBpmRuntimeTaskService bpmRuntimeTaskService;
	
	@Override
	public void onApplicationEvent(TaskCompletedEvent event) {
		BaseBpDataEx  bpDataEx=(BaseBpDataEx) event.getSource();
		BaseBpDataEx newBpDateEx = bpmRuntimeService.getBpDataEx(bpDataEx.getPiId());
		if(newBpDateEx!=null && newBpDateEx.getPiId()!=null){
			TaskEx taskEx = bpmRuntimeTaskService.getActiveTask(newBpDateEx.getPiId());
			newBpDateEx.setTaskEx(taskEx);
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, newBpDateEx);
		}else{
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, null);
		}
	
	}
	
}