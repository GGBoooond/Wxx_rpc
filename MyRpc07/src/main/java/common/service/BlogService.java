package common.service;


import common.pojo.Blog;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/10 22:13
 */
//新的接口
public interface BlogService {
    Blog getBlogById(Integer id);
}
