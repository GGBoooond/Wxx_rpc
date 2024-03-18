package common.service.Impl;


import common.pojo.Blog;
import common.service.BlogService;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/10 22:14
 */
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}
