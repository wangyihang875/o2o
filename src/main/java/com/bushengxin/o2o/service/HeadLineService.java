package com.bushengxin.o2o.service;

import com.bushengxin.o2o.dto.HeadLineExecution;
import com.bushengxin.o2o.entity.HeadLine;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.IOException;
import java.util.List;

public interface HeadLineService {

	/**
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

	/**
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail);

	/**
	 *
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution modifyHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail);

	/**
	 * 
	 * @param headLineId
	 * @return
	 */
	HeadLineExecution removeHeadLine(long headLineId);

	/**
	 * 
	 * @param headLineIdList
	 * @return
	 */
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);

}
