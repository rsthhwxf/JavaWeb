package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getItemComments(Integer id);
}
