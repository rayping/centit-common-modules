package com.centit.support.serialno.dao.jdbcimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.framework.jdbc.dao.DatabaseOptUtils;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.serialno.dao.OptFlowNoPoolDao;
import com.centit.support.serialno.po.OptFlowNoPool;
import com.centit.support.serialno.po.OptFlowNoPoolId;
import com.centit.support.algorithm.NumberBaseOpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("optFlowNoPoolDao")
public class OptFlowNoPoolDaoImpl extends BaseDaoImpl<OptFlowNoPool, OptFlowNoPoolId> implements OptFlowNoPoolDao {


    public static final Logger logger = LoggerFactory.getLogger(OptFlowNoPoolDaoImpl.class);

    public Map<String, String> getFilterField() {
        if (filterField == null) {
            filterField = new HashMap<>();
            filterField.put("ownerCode", "ownerCode = :ownerCode");
            filterField.put("(date)codeDate", "codeDate = :codeDate");
            filterField.put("codeCode", "codeCode = :codeCode");
            filterField.put("curNo", "ownerCode = :curNo");
        }
        return filterField;
    }

    @Override
    public OptFlowNoPool getObjectById(OptFlowNoPoolId cid) {
        return super.getObjectById(cid);
    }

    @Override
    public void deleteObjectById(OptFlowNoPoolId cid) {
        super.deleteObjectById(cid);
    }


    @Transactional
    public long fetchFirstLsh(String ownerCode, String codeCode,
                              Date codeBaseDate) {

        Long lsh = NumberBaseOpt.castObjectToLong(DatabaseOptUtils.getScalarObjectQuery(this,
                "select min(CURNO) as MinNo from F_OPTFLOWNOPOOL" +
                " where OWNERCODE = ? and CODECODE = ? and CODEDATE = ?",
                new Object[]{ownerCode,ownerCode,codeBaseDate }));
        return lsh == null ? 0L: lsh;
    }

    @Override
    public void saveNewOptFlowNoPool(OptFlowNoPool optFlowNoPool){
        super.saveNewObject(optFlowNoPool);
    }

    /*@Override
    public void updateOptFlowNoPool(OptFlowNoPool optFlowNoPool){
        super.updateObject(optFlowNoPool);
    }*/
    @Override
    public List<OptFlowNoPool> listLshInPool(Map<String, Object> filterMap, PageDesc pageDesc){
        JSONArray jsonArray = listObjectsAsJson(filterMap,pageDesc);
        List<OptFlowNoPool> list = JSONObject.parseArray(jsonArray.toJSONString(),OptFlowNoPool.class);
        if(list!=null && list.size()>0){
            return  list;
        }
        return null;
    }
}
