package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.KnowledgeMapper;
import com.rebs.db.domain.Knowledge;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    
    public List<Knowledge> getKonwledgeList(Knowledge konwledge) {
        return knowledgeMapper.getKonwledgeList(konwledge);
    }
    
    /**
     * 根据knowledgeId获取知识
     * @param knowledgeId
     * @return
     */
    public ArrayList<Knowledge> callProcedureUSP_KNOWLEDGE_G(Integer knowledgeId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_Knowledge_id", knowledgeId);
        map.put("cur_out", new ArrayList<Knowledge>());
        knowledgeMapper.callProcedureUSP_KNOWLEDGE_G(map);
        @SuppressWarnings("unchecked")
        ArrayList<Knowledge> cur_out = (ArrayList<Knowledge>)map.get("cur_out");
        return cur_out;
    }
}
