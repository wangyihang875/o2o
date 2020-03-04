package com.bushengxin.o2o.service.impl;

import com.bushengxin.o2o.dao.HeadLineDao;
import com.bushengxin.o2o.dto.HeadLineExecution;
import com.bushengxin.o2o.entity.HeadLine;
import com.bushengxin.o2o.service.HeadLineService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    //	@Autowired
//	private JedisUtil.Strings jedisStrings;
//	@Autowired
//	private JedisUtil.Keys jedisKeys;
    @Autowired
    private HeadLineDao headLineDao;
    private static String HLLISTKEY = "headlinelist";


    /**
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
    }

    /**
     * @param headLine
     * @param thumbnail
     * @return
     */
    @Override
    public HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail) {
        return null;
    }

    /**
     * @param headLine
     * @param thumbnail
     * @return
     */
    @Override
    public HeadLineExecution modifyHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail) {
        return null;
    }

    /**
     * @param headLineId
     * @return
     */
    @Override
    public HeadLineExecution removeHeadLine(long headLineId) {
        return null;
    }

    /**
     * @param headLineIdList
     * @return
     */
    @Override
    public HeadLineExecution removeHeadLineList(List<Long> headLineIdList) {
        return null;
    }
}
