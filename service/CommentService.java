package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getItemComments(Integer id);
}
