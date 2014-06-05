package org.bachelor.bpm.dao;

import java.util.List;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.dao.IGenericDao;

/**
 * 流程审核信息数据访问接口
 * 
 * @author 
 *
 */
public interface IBpmTaskReviewDao extends IGenericDao<BpmTaskReview, String> {

	/**
	 * 查询指定节点相关的审核信息。
	 * 此节点必须是审核节点。
	 * 审核信息按照审核时间顺序排列。
	 * 
	 * @param taskId 审核节点ID
	 * @return 节点相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId);
	
	/**
	 * 查询指定业务Key相关的审核信息。
	 * 如果流程中有多个审核节点，那么审核节点的审核信息将全部返回。
	 * 审核信息按照审核时间顺序排列。
	 * 
	 * 
	 * @param bizKey 业务Key
	 * @return 业务Key相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey);
	
	public BaseBpDataEx getBpExData(String piid);

}