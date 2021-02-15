package cn.org.chtf.card.manage.MakeEvidence.service.impl;

import cn.org.chtf.card.manage.MakeEvidence.model.CmWrongDocument;
import cn.org.chtf.card.manage.MakeEvidence.service.CmWrongDocumentService;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmWrongDocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author ggwudivs
 */
@Service
public class CmWrongDocumentServiceImpl implements CmWrongDocumentService {

    @Autowired
    private CmWrongDocumentMapper cmWrongDocumentDao;

    /**
     * 查询列表
     */
    @Override
    public List<CmWrongDocument> list(Map<String, Object> map) {
        return cmWrongDocumentDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return cmWrongDocumentDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public CmWrongDocument findById(Integer id) {
        return cmWrongDocumentDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<CmWrongDocument> findByMap(Map<String, Object> map) {
        return cmWrongDocumentDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(CmWrongDocument cmWrongDocument) {
        return cmWrongDocumentDao.save(cmWrongDocument);
    }

    /**
     * 修改
     */
    @Override
    public int update(CmWrongDocument cmWrongDocument) {
        return cmWrongDocumentDao.update(cmWrongDocument);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return cmWrongDocumentDao.deleteById(id);
    }

}
