package com.rebs.db.dao;

import java.util.List;
import java.util.Map;

import com.rebs.db.domain.Knowledge;

public interface KnowledgeMapper {

     public List<Knowledge> getKonwledgeList(Knowledge konwledge);
     
     public void callProcedureUSP_KNOWLEDGE_G(Map<Object, Object> map); 
     
}
