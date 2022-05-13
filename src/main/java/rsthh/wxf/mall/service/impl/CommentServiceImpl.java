package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import rsthh.wxf.mall.dao.CommentDao;
import rsthh.wxf.mall.po.Comment;
import rsthh.wxf.mall.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Override
    public List<Comment> getItemComments(Integer id) {
        List<Comment> comments = this.baseMapper.selectList(new QueryWrapper<Comment>().eq("item_id", id));
        return comments;
    }
}
