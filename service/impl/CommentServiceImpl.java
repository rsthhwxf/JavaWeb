package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.CommentDao;
import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.service.CommentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Override
    public List<Comment> getItemComments(Integer id) {
        List<Comment> comments = this.baseMapper.selectList(new QueryWrapper<Comment>().eq("item_id", id));
        return comments;
    }
}
