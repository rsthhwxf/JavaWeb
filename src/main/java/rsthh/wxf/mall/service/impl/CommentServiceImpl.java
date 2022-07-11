package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import rsthh.wxf.mall.dao.CommentDao;
import rsthh.wxf.mall.po.Comment;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Override
    public List<Comment> getItemComments(Integer id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> list = this.baseMapper.selectList(new QueryWrapper<Comment>().eq("item_id", id));
        PageInfo<Comment> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }
}
